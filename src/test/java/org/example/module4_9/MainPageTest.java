package org.example.module4_9;

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
        By linkSite = By.xpath("//div[@class='tpmeta']/div/cite"); //заменил локатор, т.к. изначально задание выполнялось при включенном впн и bing видимо настроился под страну их впн
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.xpath("//input[@id='sb_form_q']"));
        searchField.sendKeys(input);
        searchField.submit();
        wait.until(ExpectedConditions.elementToBeClickable(linkSite));
        List<WebElement> results = driver.findElements(linkSite);
        clickSearch(results, 0);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//h2[contains(text(),'Install Selenium and run your first script')]"))); // ввёл специально, иначе на странице селениума почему-то получает url 1-ой вкладки
        Assertions.assertEquals("https://www.selenium.dev/", driver.getCurrentUrl(), "URL не совпадает");
    }

    public void clickSearch(List<WebElement> results, int num) {
        results.get(num).click();
        System.out.println(results.get(num).toString());
    }
}
