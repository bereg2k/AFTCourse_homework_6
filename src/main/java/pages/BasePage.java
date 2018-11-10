package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import other.Debugger;
import other.Locker;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 03.11.2018.
 */
abstract class BasePage {

    private WebDriver driver;
    private static Locker locker = Locker.getInstance();
    private static int timeout = 5;

    private static Debugger debugger = Debugger.getInstance();

    public Debugger getDebugger() {
        return debugger;
    }

    Locker getLocker() {
        return locker;
    }

    BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    void scroll(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    void waitForVisible(By locator) {
        try {
            new WebDriverWait(driver, timeout)
                    .until((ExpectedConditions.visibilityOfElementLocated(locator)));
        } catch (TimeoutException e) {
            System.out.println("TOE caught! Locator = " + locator.toString());
        }
    }

    void waitForVisible(WebElement element) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException ignored) {
            System.out.println("TOE caught! Locator = " + element.toString());
        }
    }

    void waitForClickable(WebElement element) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException ignored) {
            System.out.println("TOE caught! Locator = " + element.toString());
        }
    }

    void waitForInVisible(By locator) {
        try {
            new WebDriverWait(driver, timeout)
                    .until((ExpectedConditions.invisibilityOfElementLocated(locator)));
        } catch (TimeoutException e) {
            System.out.println("TOE caught! Locator = " + locator.toString());
        }
    }

    WebElement findByXpath(String xpath) {
        return findByLocator(By.xpath(xpath));
    }

    WebElement findByLocator(By locator) {
        return driver.findElement(locator);
    }

    void click(By locator) {
        waitForVisible(locator);
        waitForClickable(findByLocator(locator));
        debugger.addSteps(driver.getCurrentUrl());
        findByLocator(locator).click();
    }

    void click(String xpath) {
        waitForVisible(By.xpath(xpath));
        waitForClickable(findByXpath(xpath));
        debugger.addSteps(driver.getCurrentUrl());
        findByXpath(xpath).click();
    }

    void click(WebElement element) {
        waitForVisible(element);
        waitForClickable(element);
        debugger.addSteps(driver.getCurrentUrl());
        element.click();
    }

    void fillElement(WebElement element, String text) {
        waitForVisible(element);
        waitForClickable(element);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    void clearPriceRangeInputBox(WebElement element) {
        while (!element.getAttribute("value").equals("")) {
            click(element);
            element.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        }
    }

    void checkElementText(WebElement element, String expectedText) {
        waitForVisible(element);
        assertEquals("Значения текста не соотвествует ожидаемому",
                expectedText, element.getText());
    }
}
