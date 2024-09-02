
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomListener implements ITestListener {

    private PrintWriter writer;

    @Override
    public void onStart(ITestContext context) {
        // Initialization logic if needed
        try {
            writer = new PrintWriter(new FileWriter("test-report.html", true));
            writer.println("<html><head><title>Test Report</title></head><body>");
            writer.println("<h1>Test Report</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // Finalization logic
        if (writer != null) {
            writer.println("</body></html>");
            writer.close();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Log test start
        System.out.println("Test started: " + result.getMethod().getMethodName());
        if (writer != null) {
            writer.println("<p>Test started: " + result.getMethod().getMethodName() + "</p>");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log test success
        System.out.println("Test passed: " + result.getMethod().getMethodName());
        if (writer != null) {
            writer.println("<p style='color:green;'>Test passed: " + result.getMethod().getMethodName() + "</p>");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log test failure
        System.out.println("Test failed: " + result.getMethod().getMethodName());
        if (writer != null) {
            writer.println("<p style='color:red;'>Test failed: " + result.getMethod().getMethodName() + "</p>");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log test skipped
        System.out.println("Test skipped: " + result.getMethod().getMethodName());
        if (writer != null) {
            writer.println("<p style='color:orange;'>Test skipped: " + result.getMethod().getMethodName() + "</p>");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Optionally handle this scenario if needed
    }
}
