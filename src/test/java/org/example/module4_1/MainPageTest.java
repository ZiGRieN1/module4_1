package org.example.module4_1;

import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.xpath("//textarea[@id = 'sb_form_q']"));
        searchField.sendKeys(input);
        searchField.submit();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='b_attribution']/cite")));
        List<WebElement> results = driver.findElements(By.xpath("//div[@class='b_attribution']/cite"));
        clickSearch(results, 0);
        ArrayList tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) driver.switchTo().window(tabs.get(1).toString());
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[contains(text(),'Install Selenium and run your first script')]"))); // ввёл специально, иначе на странице селениума почему-то получает url 1-ой вкладки
        Assertions.assertEquals(driver.getCurrentUrl(), "https://www.selenium.dev/", "URL не совпадает");
    }

    public void clickSearch(List<WebElement> results, int num) {
        results.get(num).click();
    }
}
