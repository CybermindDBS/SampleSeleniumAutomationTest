package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import selenium.utils.ElementUtil;
import utils.PropertiesReader;

import java.awt.*;

public class DropdownsPage {
    @FindBy(xpath = "//select[@class='form-select' and @aria-label='Default select example']")
    private WebElement dropDown;

    @FindBy(tagName = "html")
    private WebElement scrollableElement;

    private WebDriver driver;
    private ElementUtil elementUtil;

    public DropdownsPage(WebDriver driver, ElementUtil elementUtil) {
        this.driver = driver;
        this.elementUtil = elementUtil;
        PageFactory.initElements(driver, this);
    }

    public boolean loadPage() {
        driver.get(PropertiesReader.readString("test.target"));
        return true;
    }

    public boolean choose(String text, int... index) {
        boolean status = elementUtil.verifyAndScrollToElement(dropDown, scrollableElement);
        Select select = new Select(dropDown);
        if (status)
            if (index.length != 0)
                select.selectByIndex(index[0]);
            else
                select.selectByVisibleText(text);
        return status;
    }

    public String[] getTextsFromDropdown() {
        Select select = new Select(dropDown);
        return select.getOptions().stream().map((x) -> x.getText()).toList().toArray(new String[0]);
    }
}
