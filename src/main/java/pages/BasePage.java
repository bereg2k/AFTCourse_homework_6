package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import other.Locker;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 03.11.2018.
 */
abstract class BasePage {

    private WebDriver driver;
    private static Locker locker = Locker.getInstance();

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
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    void waitForVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    void waitForClickable(WebElement element){
        Wait<WebDriver> wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void selectByText(WebElement element, String text) {
        new Select(element).selectByVisibleText(text);
    }

    WebElement findByXpath(String xpath) {
        return findByLocator(By.xpath(xpath));
    }

    WebElement findByLocator(By locator) {
        return driver.findElement(locator);
    }

    void click(By locator) {
        waitForVisible(locator);
        findByLocator(locator).click();
    }

    void click(String xpath) {
        waitForVisible(By.xpath(xpath));
        findByXpath(xpath).click();
    }

    void click(WebElement element) {
        scroll(element);
        waitForClickable(element);
        element.click();
    }

    void fillElement(WebElement element, String text) {
        click(element);
        element.clear();
        if (element.getTagName().equalsIgnoreCase("select")) {
            selectByText(element, text);
        } else {
            element.sendKeys(text);
        }
    }

    void fillElement(String xpath, String text) {
        fillElement(findByXpath(xpath), text);
    }

    void checkElementText(WebElement element, String expectedText) {
        waitForVisible(element);
        assertEquals("Значения текста не соотвествует ожидаемому",
                expectedText, element.getText());
    }

    boolean isElementPresent(String xpath) {
        return driver.findElements(By.xpath(xpath)).size() != 0;
    }

}
