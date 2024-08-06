package selenium.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ElementUtil {
    private WebDriver driver;
    private JavascriptExecutor jsExecutor;
    private TakesScreenshot takesScreenshot;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        jsExecutor = (JavascriptExecutor) driver;
        takesScreenshot = (TakesScreenshot) driver;
    }

    public boolean verifyVisibility(WebElement element, int... timeLimit) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(timeLimit.length != 0 ? timeLimit[0] : 5000, ChronoUnit.MILLIS));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyVisibility(By locator, int... timeLimit) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(timeLimit.length != 0 ? timeLimit[0] : 5000, ChronoUnit.MILLIS));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyElementClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(5, ChronoUnit.SECONDS));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyElementClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(5, ChronoUnit.SECONDS));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyAndScrollToElement(WebElement element, WebElement scrollableElement) {
        jsExecutor.executeScript("arguments[0].scrollTop = 0;", scrollableElement);
        Double currentScrollPosition = 0d, nextScrollPosition = -1d;
        while (!verifyVisibility(element, 150) && !currentScrollPosition.equals(nextScrollPosition)) {
            currentScrollPosition = ((Number) jsExecutor.executeScript("return arguments[0].scrollTop", scrollableElement)).doubleValue();
            jsExecutor.executeScript("arguments[0].scrollTop += 100;", scrollableElement);
            nextScrollPosition = ((Number) jsExecutor.executeScript("return arguments[0].scrollTop", scrollableElement)).doubleValue();
        }
        return verifyVisibility(element, 150);
    }

    public String takeScreenshot(String fileName) {
        File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
        try {
            String newPath = screenshot.toPath().getParent().toString() + File.separator + fileName + ".png";
            Files.move(screenshot.toPath(), Path.of(newPath), StandardCopyOption.REPLACE_EXISTING);
            return newPath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
