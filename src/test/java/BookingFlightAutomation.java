import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookingFlightAutomation {
    public static void main(String[] args) {
    	WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(500));
		try {
			
			 driver.get("https://www.booking.com");
			 driver.manage().window().maximize();
			 driver.findElement(By.xpath("//a[@id='flights']")).click();
//			 WebElement from= driver.findElement(By.xpath("(//button[@class='ShellButton-module_btn__tCJzz'])[1]"));
//			 from.click();
//			 driver.findElement(By.xpath("//span[@class='Icon-module_root_lkZX8 Tags-moduleicon_XQsOM Icon-moduleroot--size-small__8pl9w']")).click();
//			 By fromLocation = By.xpath("//input[@class='AutoComplete-module_textInput__tZuOx ']");
//			 WebElement fromLocationelement = wait.until(ExpectedConditions.visibilityOfElementLocated(fromLocation));
//			 fromLocationelement.sendKeys("Houston");
			 By toSelector = By.xpath("(//span[@class='InputCheckbox-module_field__9MB75'])[2]");
			 WebElement toSelectorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(toSelector));
			 toSelectorElement.click();
			 WebElement to = driver.findElement(By.xpath("//button[@class='ShellButton-module_btn__tCJzz' and @data-ui-name='input_location_to_segment_0']"));
			 to.click();
			 WebElement toLocation = driver.findElement(By.xpath("//input[@class='AutoComplete-module_textInput__tZuOx ']"));
			 toLocation.click();
			 toLocation.sendKeys("newyork");
			 By selector = By.xpath("(//span[@class='InputCheckbox-module_field__9MB75'])[2]");
			 WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
			 element.click();
			 WebElement dateSelector = driver.findElement(By.xpath("//button[@data-ui-name='button_date_segment_0']"));
			 dateSelector.click();
			 WebElement fromDate = driver.findElement(By.xpath("//span[contains(@aria-label, '15 October 2024')]"));
			 fromDate.click();
			 WebElement toDate = driver.findElement(By.xpath("//span[contains(@aria-label, '22 October 2024')]"));
			 toDate.click();
			 By dateSelectionDone = By.xpath("//span[contains(text(),'Done')]");
			 WebElement dateSelectionDoneElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dateSelectionDone));
			 dateSelectionDoneElement.click();
			 By search = By.xpath("//span[contains(text(),'Search')]");
			 WebElement searchElement = wait.until(ExpectedConditions.visibilityOfElementLocated(search));
			 searchElement.click();
			 
			 
			 
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Filters')]")));

	            // Find all flight elements
	            List<WebElement> flights = driver.findElements(By.xpath("//div[@class='FlightCardPrice-module_priceContainer__nXXv2']"));

	            WebElement cheapestFlight = null;
	            WebElement longestDurationFlight = null;
	            double cheapestPrice = Double.MAX_VALUE;
	            int longestDuration = 0;

	            for (WebElement flight : flights) {
	                // Extract flight price
	                WebElement priceElement = flight.findElement(By.xpath("//div[@class='FlightCardPrice-module_priceContainer__nXXv2']"));
	                String priceText = priceElement.getText().replaceAll("[^\\d.]", "");
	                double price = Double.parseDouble(priceText);

	                // Extract flight duration
	                By flightDuration = By.xpath("//div[@data-testid='flight_card_segment_duration_0']");
	                
	                WebElement flightDurationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(flightDuration));
	                String durationText = flightDurationElement.getText();
	                int duration = parseDuration(durationText); // Implement this method to parse duration

	                // Determine the cheapest flight
	                if (price > cheapestPrice) {
	                    cheapestPrice = price;
	                    cheapestFlight = flight;
	                }

	                // Determine the longest duration flight
	                if (duration > longestDuration) {
	                    longestDuration = duration;
	                    longestDurationFlight = flight;
	                }
	            }

	            if (cheapestFlight != null) {
	                System.out.println("Cheapest Flight: " + cheapestPrice);
	            } else {
	                System.out.println("No flight found for the cheapest search.");
	            }

	            if (longestDurationFlight != null) {
	                System.out.println("Longest Duration Flight: " + longestDuration);
	            } else {
	                System.out.println("No flight found for the longest duration search.");
	            }
			 
			 
			 
			 
			 
			
		} catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
		 
		 
	}
	private static int parseDuration(String durationText) {
        // Example implementation, adapt to actual format
        String[] parts = durationText.split(" ");
        int hours = 0, minutes = 0;

        for (String part : parts) {
            if (part.endsWith("h")) {
                hours = Integer.parseInt(part.replace("h", ""));
            } else if (part.endsWith("m")) {
                minutes = Integer.parseInt(part.replace("m", ""));
            }
        }

        return hours * 60 + minutes;
    }
}