package selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.PropertiesReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

//Singleton design pattern
public class DriverFactory {
    private static final HashMap<String, WebDriver> drivers = new HashMap<>();

    private DriverFactory() {
    }

    public static WebDriver getInstance(String browserName) {
        WebDriver driver = null;
        if (drivers.containsKey(browserName))
            return drivers.get(browserName);
        else {
            switch (browserName) {
                case "chrome" -> {
                    if (PropertiesReader.readString("test.launch_mode").equalsIgnoreCase("local"))
                        driver = new ChromeDriver();
                    else {
                        try {
                            driver = new RemoteWebDriver(new URL(PropertiesReader.readString("selenium.grid.hub_url")), new ChromeOptions());
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case "edge" -> {
                    if (PropertiesReader.readString("test.launch_mode").equalsIgnoreCase("local"))
                        driver = new EdgeDriver();
                    else {
                        try {
                            driver = new RemoteWebDriver(new URL(PropertiesReader.readString("selenium.grid.hub_url")), new EdgeOptions());
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                default -> {
                    System.out.println("Un-defined browser error.");
                    System.exit(1);
                }
            }
            drivers.put(browserName, driver);
        }
        return driver;
    }
}
