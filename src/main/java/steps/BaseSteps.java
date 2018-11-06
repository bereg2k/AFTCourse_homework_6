package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public abstract class BaseSteps {

    private static WebDriver driver;

    Map<String, String> userOrderList = new HashMap<>();

    public Map<String, String> getUserOrderList() {
        return userOrderList;
    }

    public void click(WebElement element) {
        scroll(element);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void scroll(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    void waitForVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    void selectByText(WebElement element, String text) {
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

    void checkElementText(WebElement element, String expectedText) {
        assertEquals("Значения текста не соотвествует ожидаемому",
                expectedText, element.getText());
    }

    void fillElement(By locator, String text) {
        WebElement element = findByLocator(locator);
        if (element.getTagName().equalsIgnoreCase("select")) {
            selectByText(element, text);
        } else {
            element.sendKeys(text);
        }
    }

    void fillElement(String xpath, String text) {
        fillElement(By.xpath(xpath), text);
    }

    boolean isElementPresent(String xpath) {
        return driver.findElements(By.xpath(xpath)).size() != 0;
    }

    void saveItemPrice(WebElement name, WebElement price) {
        userOrderList.put(name.getText(), price.getText().substring(0, 3));
    }

    void openWebPage(String url) {
        driver.get(url);
    }
}
