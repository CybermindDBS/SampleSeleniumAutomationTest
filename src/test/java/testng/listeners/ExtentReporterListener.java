package testng.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.HashMap;

public class ExtentReporterListener implements ITestListener {
    private static final HashMap<Long, ExtentTest> tests = new HashMap<>();
    private ExtentReports report;

    public static void attachScreenShot(String path) {
        tests.get(Thread.currentThread().getId()).addScreenCaptureFromPath(path);
    }

    @Override
    public void onTestStart(ITestResult result) {
        tests.put(Thread.currentThread().getId(), report.createTest(result.getMethod().getMethodName() + " - " + Thread.currentThread().getId()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        tests.get(Thread.currentThread().getId()).pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        tests.get(Thread.currentThread().getId()).fail(result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        tests.get(Thread.currentThread().getId()).skip(result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("src/test/resources/extent-report.html");
        report = new ExtentReports();
        report.attachReporter(extentSparkReporter);

    }

    @Override
    public void onFinish(ITestContext context) {
        report.flush();
    }
}
