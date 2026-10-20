package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.example.OrangeHRM_Automation_Framework.utils.ExtentReportManager;
import org.example.OrangeHRM_Automation_Framework.utils.ScreenshotUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context){
        ExtentReportManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result){
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        ExtentReportManager.createTest(testName, description);
        ExtentReportManager.getTest().log(Status.INFO, "Test started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result){
        ExtentReportManager.getTest().log(Status.PASS,"Test Passed: " + result.getMethod().getMethodName());

    }

    @Override
    public void onTestFailure(ITestResult result){
        String testName =  result.getMethod().getMethodName();

        // 1. Log the exact exception/assertion error in the report
        ExtentReportManager.getTest().log(Status.FAIL,"Test failed: " + result.getThrowable());

        // 2. Save Physical .png file to reports/screenshots/
        String savedPath = ScreenshotUtils.captureScreensgotFile(testName);

        // 3. Embed Base64 screenshot directly into the ExtentReport HTML
        String base64Screenshot =  ScreenshotUtils.captureBase64Screenshot();
        if(base64Screenshot != null) {
            ExtentReportManager.getTest().fail(
                    "Failure Screenshot (saved at: " + savedPath + ")",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot, testName).build()
            );
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP,"Test skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReports();
    }
}
