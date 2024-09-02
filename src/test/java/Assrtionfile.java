import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Assrtionfile {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.booking.com/index.en-gb.html");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
    }

    @Test
    public void testFlightSearch() {
        WebElement flightsButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("flights")));
        flightsButton.click();

        WebElement fromInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ShellButton-module__inner___dHha\\+ .Text-module__root--color-neutral_alt___nUheL")));
        fromInput.click();

        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='ShellButton-module__contentInner___kuPR3']")));
        select.click();

        WebElement cancell = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='Text-module__root--variant-body_2___XeO4S Tags-module__item___sPVsA']")));
        cancell.click();

        WebElement enter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='AutoComplete-module__textInput___tZuOx ']")));
        enter.sendKeys("DTW");

        WebElement Accept = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//li[@class='List-module__location___Yigjj'])[1]")));
        Accept.click();

        WebElement todestianton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-ui-name='input_location_to_segment_0']")));
        todestianton.click();

        WebElement textInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".AutoComplete-module__textInput___tZuOx")));
        textInput.sendKeys("phl");

        WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".List-module__content___dN\\+zP:nth-child(2) > span")));
        secondOption.click();

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".Button-module__text___TdDtz")));
        searchButton.click();

        // Add assertions
        List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='FlightCardPrice-module__priceContainer___nXXv2']")));
        Assert.assertFalse(priceElements.isEmpty(), "No price elements found.");

        List<WebElement> flightElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='Frame-module__justify-content_flex-start___EWEFz Frame-module__flex-direction_column___ms2of']")));
        Assert.assertFalse(flightElements.isEmpty(), "No flight elements found.");

        // Example assertion for price
        String priceText = priceElements.get(0).getText().replaceAll("[^\\d.]", "");
        Assert.assertFalse(priceText.isEmpty(), "Price text is empty.");
        double price = Double.parseDouble(priceText);
        Assert.assertTrue(price > 0, "Price is not greater than zero.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
