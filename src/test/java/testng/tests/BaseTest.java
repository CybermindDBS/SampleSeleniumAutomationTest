package testng.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import pages.DropdownsPage;
import selenium.utils.DriverFactory;
import selenium.utils.ElementUtil;
import utils.PropertiesReader;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    protected WebDriver driver;
    protected DropdownsPage dropdownsPage;
    protected ElementUtil elementUtil;

    @BeforeTest()
    @Parameters("browser")
    public void setup(String browser) {
        driver = DriverFactory.getInstance(browser);
        elementUtil = new ElementUtil(driver);
        dropdownsPage = new DropdownsPage(driver, elementUtil);
    }

    @AfterTest()
    public void tearDown() {
        driver.quit();
    }
}
