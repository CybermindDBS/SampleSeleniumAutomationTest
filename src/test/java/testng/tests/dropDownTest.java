package testng.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import testng.RetryAnalyzer;
import testng.dataProviders.TestDataProvider;
import testng.listeners.ExtentReporterListener;
import utils.ExcelUtil;
import utils.PropertiesReader;

import java.nio.file.Path;

public class dropDownTest extends BaseTest {

    @Test(groups = {"group2"}, priority = 1, retryAnalyzer = RetryAnalyzer.class)
    public void loadPage() {
        dropdownsPage.loadPage();
    }

    @Test(dataProvider = "testDataProvider", dataProviderClass = TestDataProvider.class, dependsOnMethods = "loadPage", groups = {"group2"}, priority = 2)
    public void selectFromDropdown(String testData) {
        boolean status;
        if (testData.charAt(0) >= '0' && testData.charAt(0) <= '9')
            status = dropdownsPage.choose(null, Integer.parseInt(testData));
        else status = dropdownsPage.choose(testData);
        ExtentReporterListener.attachScreenShot(elementUtil.takeScreenshot(Thread.currentThread().getName() + "-" + testData));
        Assert.assertTrue(status);
    }

    @Test(dependsOnMethods = "loadPage", groups = {"group2"}, priority = 3, retryAnalyzer = RetryAnalyzer.class)
    public void getTextsFromDropdown() {
        String[] data = dropdownsPage.getTextsFromDropdown();
        String[][] cellData = new String[data.length][1];
        int index = 0;
        for (String dat : data)
            cellData[index++][0] = dat;
        ExcelUtil excelUtil = new ExcelUtil();
        excelUtil.create(Path.of(PropertiesReader.readString("out.excel"))).write(cellData);
    }

    @Test(groups = {"group1"})
    public void method() {
        System.out.println("---___ Completed ___---");
    }
}
