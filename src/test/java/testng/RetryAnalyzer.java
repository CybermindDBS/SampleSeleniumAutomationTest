package testng;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retestCount = 0;
    private final int maxCount = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (retestCount++ < maxCount)
        {
            System.out.println("Retrying " + result.getMethod().getMethodName()+ "...");
            return true;
        }
        else return false;
    }
}
