package Stepdefination;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FlightSteps {
	 public WebDriver driver;
	    public WebDriverWait wait;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("I am on the booking website")
    public void i_am_on_the_booking_website() {
        driver.get("https://www.booking.com/index.en-gb.html");
    }

    @When("I search for flights from {string} to {string}")
    public void i_search_for_flights_from_to(String fromLocation, String toLocation) {
        WebElement flightsButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("flights")));
        flightsButton.click();

        WebElement fromInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ShellButton-module__inner___dHha\\+ .Text-module__root--color-neutral_alt___nUheL")));
        fromInput.click();

		WebElement select = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//span[@class=\'ShellButton-module__contentInner___kuPR3\']")));
		select.click();

		WebElement cancell = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//span[@class='Text-module__root--variant-body_2___XeO4S Tags-module__item___sPVsA']")));
		cancell.click();
		System.out.println("enter correct destination");

		WebElement enter = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@class='AutoComplete-module__textInput___tZuOx ']")));
		enter.sendKeys(fromLocation);
		System.out.println("passing location");

		WebElement Accept = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("(//li[@class='List-module__location___Yigjj'])[1]")));
		Accept.click();

		WebElement todestianton = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@data-ui-name='input_location_to_segment_0']")));
		todestianton.click();

		WebElement textInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector(".AutoComplete-module__textInput___tZuOx")));
		textInput.click();
		textInput.sendKeys(toLocation);

		WebElement secondOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector(".List-module__content___dN\\+zP:nth-child(2) > span")));
		secondOption.click();

		WebElement searchButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".Button-module__text___TdDtz")));
		searchButton.click();
    }

    @Then("I should see the flight with the longest duration")
    public void i_should_see_the_flight_with_the_longest_duration() {
        List<WebElement> flightElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='Frame-module__justify-content_flex-start___EWEFz Frame-module__flex-direction_column___ms2of']")));
        int longestDurationMinutes = 0;
        String longestDepartureName = "";
        String longestDestinationName = "";
        String longestDepartureTime = "";
        String longestArrivalTime = "";

        for (WebElement flight : flightElements) {
            try {
                String outboundDurationText = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_duration_0']")).getText();
                String returnDurationText = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_duration_1']")).getText();

                String departureName = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_departure_airport_0']")).getText();
                String destinationName = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_destination_airport_0']")).getText();
                String departureTime = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_departure_time_0']")).getText();
                String arrivalTime = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_destination_time_0']")).getText();

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
            System.out.println("Longest Flight Details:");
            System.out.println("Departure: " + longestDepartureName);
            System.out.println("Destination: " + longestDestinationName);
            System.out.println("Departure Time: " + longestDepartureTime);
            System.out.println("Arrival Time: " + longestArrivalTime);
            System.out.println("Total Duration: " + formatDuration(longestDurationMinutes));
        } else {
            System.out.println("No flights found.");
        }
    }

    @Then("I should see the flight with the cheapest price")
    public void i_should_see_the_flight_with_the_cheapest_price() {
        List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='FlightCardPrice-module__priceContainer___nXXv2']")));
        List<WebElement> flightElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='Frame-module__justify-content_flex-start___EWEFz Frame-module__flex-direction_column___ms2of']")));

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
            System.out.println("Cheapest Price: $" + cheapestPrice);

            // Find the corresponding flight details
            for (WebElement flight : flightElements) {
                try {
                	 WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='FlightCardPrice-module__priceContainer___nXXv2']")));
                   // WebElement priceElement = flight.findElement(By.xpath(".//div[@class='FlightCardPrice-module__priceContainer___nXXv2']"));
                    String priceText = priceElement.getText().replaceAll("[^\\d.]", "");
                    double flightPrice = Double.parseDouble(priceText);

                    if (flightPrice == cheapestPrice) {
                        String departureName = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_departure_airport_0']")).getText();
                        String destinationName = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_destination_airport_0']")).getText();
                        String departureTime = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_departure_time_0']")).getText();
                        String arrivalTime = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_destination_time_0']")).getText();

                        System.out.println("Cheapest Flight Details:");
                        System.out.println("Departure: " + departureName);
                        System.out.println("Destination: " + destinationName);
                        System.out.println("Departure Time: " + departureTime);
                        System.out.println("Arrival Time: " + arrivalTime);
                        System.out.println("Price: $" + flightPrice);
                        break;
                    }

                } catch (Exception e) {
                    System.out.println("Error extracting or parsing flight details: " + e.getMessage());
                }
            }
        } else {
            System.out.println("No prices found.");
        }
    }

    private int convertDurationToMinutes(String duration) {
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

    private String formatDuration(int minutes) {
        int hours = minutes / 60;
        minutes = minutes % 60;
        return String.format("%dh %dm", hours, minutes);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
           
        }
        if (driver != null) {
            driver.quit();
        }
    }
}