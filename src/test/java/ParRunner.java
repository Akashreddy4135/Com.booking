

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParRunner {

    @DataProvider(name = "ParRunner")
    public static Object[][] flightData() {
        return new Object[][] {
                {"DFW", "PHL"},
                {"NYC", "LAX"},
                {"JFK", "LAX"},
                {"ORD", "SFO"}
        };
    }
    @Test(groups = "smoke", dataProvider = "ParRunner")
    public void testGetCheapestPrice(String startLocation, String destinationLocation) {
        // Your test code here
    }

    @Test(groups = "regression", dataProvider = "ParRunner")
    public void testGetLongestDuration(String startLocation, String destinationLocation) {
        // Your test code here
    }
}




























