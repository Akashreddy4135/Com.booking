
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Optional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class flight {
	private static final WebElement[] UI = null;

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.booking.com/index.en-gb.html");
		driver.manage().window().setSize(new Dimension(1936, 1048));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement flightsButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("flights")));
		flightsButton.click();

		WebElement fromInput = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector(".ShellButton-module__inner___dHha\\+ .Text-module__root--color-neutral_alt___nUheL")));
		fromInput.click();

		WebElement select = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//span[@class=\'ShellButton-module__contentInner___kuPR3\']")));
		select.click();

		WebElement cancell = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//span[@class='Text-module__root--variant-body_2___XeO4S Tags-module__item___sPVsA']")));
		cancell.click();
		System.out.println("you gave input ");

		WebElement enter = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@class='AutoComplete-module__textInput___tZuOx ']")));
		enter.sendKeys("DTW");
		System.out.println(" From location Entered");

		WebElement Accept = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("(//li[@class='List-module__location___Yigjj'])[1]")));
		Accept.click();

		WebElement todestianton = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@data-ui-name='input_location_to_segment_0']")));
		todestianton.click();

		WebElement textInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector(".AutoComplete-module__textInput___tZuOx")));
		textInput.click();
		textInput.sendKeys("phl");

		WebElement secondOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector(".List-module__content___dN\\+zP:nth-child(2) > span")));
		secondOption.click();

		WebElement searchButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".Button-module__text___TdDtz")));
		searchButton.click();

//    WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
//    wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.resultWrapper")));

		List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
				By.xpath("//div[@class='FlightCardPrice-module__priceContainer___nXXv2']")));
		List<WebElement> flightElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
				"//div[@class='Frame-module__justify-content_flex-start___EWEFz Frame-module__flex-direction_column___ms2of']")));

		// Process prices
		List<Double> prices = new ArrayList<>();
		for (WebElement priceElement : priceElements) {
			String priceText = priceElement.getText().replaceAll("[^\\d.]", ""); // Remove non-numeric characters
			if (!priceText.isEmpty()) {
				try {
					prices.add(Double.parseDouble(priceText)); // Parse string to integer
				} catch (NumberFormatException e) {
					// Handle cases where parsing fails
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
//    List<Integer> totalDurations = new ArrayList<>();
//    for (WebElement flight : flightElements) {
//        try {
//            // Extract durations for "to" and "fro" flights
//            String outboundDurationText = flight.findElement(By.xpath(".//div[@data-testid='flight_card_segment_duration_0']")).getText();
//            String returnDurationText = flight.findElement(By.xpath(".//div[@data-testid='flight_card_segment_duration_1']")).getText();
//
//            // Convert duration to minutes
//            int outboundDurationMinutes = convertDurationToMinutes(outboundDurationText);
//            int returnDurationMinutes = convertDurationToMinutes(returnDurationText);
//
//            int totalDurationMinutes = outboundDurationMinutes + returnDurationMinutes;
//            totalDurations.add(totalDurationMinutes);
//
//        } catch (Exception e) {
//            System.out.println("Error extracting or parsing duration: " + e.getMessage());
//        }
//    }
//
//    // Find and print the longest total duration
//    if (!totalDurations.isEmpty()) {
//        int longestDuration = Collections.max(totalDurations);
//        System.out.println("The longest flight duration is: " + formatDuration(longestDuration));
//    } else {
//        System.out.println("No durations found.");
//    }
//}
//
//           private static int convertDurationToMinutes(String duration) {
//      int minutes = 0;
//          Pattern pattern = Pattern.compile("(\\d+)h(?:\\s*(\\d+)m)?");
//              Matcher matcher = pattern.matcher(duration);
//                   if (matcher.find()) {
//           minutes += Integer.parseInt(matcher.group(1)) * 60;
//    if (matcher.group(2) != null) {
//        minutes += Integer.parseInt(matcher.group(2));
//    }
//  }
//                return minutes;
// }
//
//            private static String formatDuration(int totalMinutes) {
//        int hours = totalMinutes / 60;
//      int minutes = totalMinutes % 60;
//            return hours + "h " + (minutes > 0 ? minutes + "m" : "");
//
//	

//    List<Integer> totalDurations = new ArrayList<>();
//    for (WebElement flight : flightElements) {
//        try {
//            // Extract durations for "to" and "fro" flights
//            String outboundDurationText = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_duration_0']")).getText();
//            String returnDurationText = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_duration_1']")).getText();
//            String depatureName = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_departure_airport_0']")).getText();
//            String destinationName = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_destination_airport_0']")).getText();
//            String departureTime = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_departure_time_0']")).getText();
//            String arrivalTime = flight.findElement(By.xpath("//div[@data-testid='flight_card_segment_destination_time_0']")).getText();
//
//            // Convert duration to minutes
//            int outboundDurationMinutes = convertDurationToMinutes(outboundDurationText);
//            int returnDurationMinutes = convertDurationToMinutes(returnDurationText);
//
//            int totalDurationMinutes = outboundDurationMinutes + returnDurationMinutes;
//            totalDurations.add(totalDurationMinutes);
//
//            // Print the flight details
//            System.out.println("Depature: " + depatureName);
//            System.out.println("Destination: " + destinationName);
//            System.out.println("Departure: " + departureTime);
//            System.out.println("Arrival: " + arrivalTime);
//            System.out.println("Total Duration: " + formatDuration(totalDurationMinutes));
//            System.out.println("-----------------------------------");
//
//        } catch (Exception e) {
//            System.out.println("Error extracting or parsing flight details: " + e.getMessage());
//        }
//    }
//
//    // Find and print the longest total duration
//    if (!totalDurations.isEmpty()) {
//        int longestDuration = Collections.max(totalDurations);
//        System.out.println("The longest flight duration is: " + formatDuration(longestDuration));
//    } else {
//        System.out.println("No durations found.");
//    }
//    }
//
//    private static int convertDurationToMinutes(String duration) {
//        int minutes = 0;
//        Pattern pattern = Pattern.compile("(\\d+)h(?:\\s*(\\d+)m)?");
//        Matcher matcher = pattern.matcher(duration);
//        if (matcher.find()) {
//            minutes += Integer.parseInt(matcher.group(1)) * 60;
//            if (matcher.group(2) != null) {
//                minutes += Integer.parseInt(matcher.group(2));
//            }
//        }
//        return minutes;
//    }
//
//    private static String formatDuration(int totalMinutes) {
//        int hours = totalMinutes / 60;
//        int minutes = totalMinutes % 60;
//        return hours + "h " + (minutes > 0 ? minutes + "m" : "");

		List<WebElement> flightElements1 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
				"//div[@class='Frame-module__justify-content_flex-start___EWEFz Frame-module__flex-direction_column___ms2of']")));

		int longestDurationMinutes = 0;
		String longestDepatureName = "";
		String longestDestinationName = "";
		String longestDepartureTime = "";
		String longestArrivalTime = "";

		for (WebElement flight : flightElements1) {
			try {
				// Extract durations for "to" and "fro" flights
				String outboundDurationText = flight
						.findElement(By.xpath("//div[@data-testid='flight_card_segment_duration_0']")).getText();
				String returnDurationText = flight
						.findElement(By.xpath("//div[@data-testid='flight_card_segment_duration_1']")).getText();

				// Extract destination name, departure, and arrival times
				String depatureName = flight
						.findElement(By.xpath("//div[@data-testid='flight_card_segment_departure_airport_0']"))
						.getText();
				String destinationName = flight
						.findElement(By.xpath("//div[@data-testid='flight_card_segment_destination_airport_0']"))
						.getText();
				String departureTime = flight
						.findElement(By.xpath("//div[@data-testid='flight_card_segment_departure_time_0']")).getText();
				String arrivalTime = flight
						.findElement(By.xpath("//div[@data-testid='flight_card_segment_destination_time_0']"))
						.getText();

				// Convert duration to minutes
				int outboundDurationMinutes = convertDurationToMinutes(outboundDurationText);
				int returnDurationMinutes = convertDurationToMinutes(returnDurationText);

				int totalDurationMinutes = outboundDurationMinutes + returnDurationMinutes;

				if (totalDurationMinutes > longestDurationMinutes) {
					longestDurationMinutes = totalDurationMinutes;
					longestDepatureName = depatureName;
					longestDestinationName = destinationName;
					longestDepartureTime = departureTime;
					longestArrivalTime = arrivalTime;
				}

			} catch (Exception e) {
				System.out.println("Error extracting or parsing flight details: " + e.getMessage());
			}
		}

		// Print the details of the flight with the longest duration
		if (longestDurationMinutes > 0) {
			System.out.println("Departure: " + longestDepatureName);
			System.out.println("Destination: " + longestDestinationName);
			System.out.println("Departure Time: " + longestDepartureTime);
			System.out.println("Arrival Time: " + longestArrivalTime);
			System.out.println("Total Duration: " + formatDuration(longestDurationMinutes));
		} else {
			System.out.println("No flights found.");
		}

		driver.quit();
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
