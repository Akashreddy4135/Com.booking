import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class xmlcode {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1936, 1048));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testFlightSearch() {
        driver.get("https://www.booking.com/index.en-gb.html");

        WebElement flightsButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("flights")));
        flightsButton.click();

        WebElement fromInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".ShellButton-module__inner___dHha\\+ .Text-module__root--color-neutral_alt___nUheL")));
        fromInput.click();

        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='ShellButton-module__contentInner___kuPR3']")));
        select.click();

        WebElement cancell = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@class='Text-module__root--variant-body_2___XeO4S Tags-module__item___sPVsA']")));
        cancell.click();

        WebElement enter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='AutoComplete-module__textInput___tZuOx ']")));
        enter.sendKeys("DTW");

        WebElement accept = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//li[@class='List-module__location___Yigjj'])[1]")));
        accept.click();

        WebElement toDestinationOn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-ui-name='input_location_to_segment_0']")));
        toDestinationOn.click();

        WebElement textInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".AutoComplete-module__textInput___tZuOx")));
        textInput.sendKeys("phl");

        WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".List-module__content___dN\\+zP:nth-child(2) > span")));
        secondOption.click();

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".Button-module__text___TdDtz")));
        searchButton.click();

        
        
        // Process prices
        List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='FlightCardPrice-module__priceContainer___nXXv2']")));
        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText().replaceAll("[^\\d.]", ""); // Remove non-numeric characters
            if (!priceText.isEmpty()) {
                try {
                    prices.add(Double.parseDouble(priceText)); // Parse string to double
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing price: " + priceText);
                }
            }
        }

        // Find and print the cheapest price
        if (!prices.isEmpty()) {
            double cheapestPrice = Collections.min(prices);
            System.out.println("The cheapest price is: $" + cheapestPrice);
        } else {
            System.out.println("No prices found.");
        }

        // Process durations
        List<WebElement> flightElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class='Frame-module__justify-content_flex-start___EWEFz Frame-module__flex-direction_column___ms2of']")));

        int longestDurationMinutes = 0;
        String longestDepartureName = "";
        String longestDestinationName = "";
        String longestDepartureTime = "";
        String longestArrivalTime = "";

        for (WebElement flight : flightElements) {
            try {
                String outboundDurationText = flight.findElement(By.xpath(".//div[@data-testid='flight_card_segment_duration_0']")).getText();
                String returnDurationText = flight.findElement(By.xpath(".//div[@data-testid='flight_card_segment_duration_1']")).getText();

                String departureName = flight.findElement(By.xpath(".//div[@data-testid='flight_card_segment_departure_airport_0']")).getText();
                String destinationName = flight.findElement(By.xpath(".//div[@data-testid='flight_card_segment_destination_airport_0']")).getText();
                String departureTime = flight.findElement(By.xpath(".//div[@data-testid='flight_card_segment_departure_time_0']")).getText();
                String arrivalTime = flight.findElement(By.xpath(".//div[@data-testid='flight_card_segment_destination_time_0']")).getText();

                int outboundDurationMinutes = convertDurationToMinutes(outboundDurationText);
                int returnDurationMinutes = convertDurationToMinutes(returnDurationText);

                int totalDurationMinutes = outboundDurationMinutes + returnDurationMinutes;

                if (totalDurationMinutes > longestDurationMinutes) {
                    longestDurationMinutes = totalDurationMinutes;
                    longestDepartureName = departureName;
                    longestDestinationName = destinationName;
                    longestDepartureTime = departureTime;
                    longestArrivalTime = arrivalTime;
                }

            } catch (Exception e) {
                System.out.println("Error extracting or parsing flight details: " + e.getMessage());
            }
        }

        if (longestDurationMinutes > 0) {
            System.out.println("Departure: " + longestDepartureName);
            System.out.println("Destination: " + longestDestinationName);
            System.out.println("Departure Time: " + longestDepartureTime);
            System.out.println("Arrival Time: " + longestArrivalTime);
            System.out.println("Total Duration: " + formatDuration(longestDurationMinutes));
        } else {
            System.out.println("No flights found.");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static int convertDurationToMinutes(String duration) {
        int minutes = 0;
        Pattern pattern = Pattern.compile("(\\d+)h(?:\\s*(\\d+)m)?");
        Matcher matcher = pattern.matcher(duration);
        if (matcher.find()) {
            minutes += Integer.parseInt(matcher.group(1)) * 60;
            if (matcher.group(2) != null) {
                minutes += Integer.parseInt(matcher.group(2));
            }
        }
        return minutes;
    }

    private static String formatDuration(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return hours + "h " + (minutes > 0 ? minutes + "m" : "");
    }
}
