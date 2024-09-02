package RunnerClass;



import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;




@CucumberOptions(
		    features = "C:\\Users\\provi\\OneDrive\\Desktop\\Exercises\\GoogleFlights\\src\\test\\resources\\flight_search.feature", 
    glue = "Stepdefination", 
    plugin = {"pretty", "html:target/cucumber-reports.html"} 
)
public class TestRunner extends AbstractTestNGCucumberTests {

//    @BeforeClass
//    public void setUp() {
//        // Set up your WebDriver or other initial setup if needed
//    }
//
//    @AfterClass
//    public void tearDown() {
//        // Clean up after tests if needed
//    }
}
