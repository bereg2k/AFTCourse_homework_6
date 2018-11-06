package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

    @FindBy(xpath = "//input[@data-test-id='header-search-input']")
    WebElement mainSearchBox;

    @FindBy(xpath = "//button[@data-test-id='header-search-go']")
    WebElement mainSearchGoButton;

    @FindBy(xpath = "//span[@class='eMyOzon_Item_Bottom' and text()='Корзина']")
    WebElement openCartButton;

    public void openCart(){
        click(openCartButton);
    }
}
