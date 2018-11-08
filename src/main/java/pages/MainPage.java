package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

    @FindBy(xpath = "//input[@data-test-id='header-search-input']")
    WebElement mainSearchBox;

    @FindBy(xpath = "//button[@data-test-id='header-search-go']")
    WebElement mainSearchGoButton;

    @FindBy(xpath = "//span[@class='eMyOzon_Item_Bottom' and contains(text(),'Корзина')]")
    WebElement openCartButton;

    @FindBy(xpath = "//div[@class='eCityCompleteSelector_exactButton jsCloseExact']")
    WebElement locationDeliveryButton;

    public MainPage(WebDriver driver) {
        super(driver);

        if (locationDeliveryButton.isDisplayed())
            click(locationDeliveryButton);
    }

    @Step("выбран пункт главного меню \"{0}\"")
    public void chooseMainMenuLink(String text) {
        click(By.xpath("//div[@class='links']/a[contains(text(),'" + text + "')]"));
    }

    @Step("Открыта Корзина")
    public void openCart() {
        click(openCartButton);
    }
}
