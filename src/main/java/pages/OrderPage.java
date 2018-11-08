package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderPage extends BasePage {
    @FindBy(xpath = "//div[@data-test-id='filter-block-brand']//../span[@class='show-text']")
    WebElement showAllButtonForBrands;

    @FindBy(xpath = "//button[@data-test-id='range-filter-show']")
    WebElement showFilteredByPriceButton;

    @FindBy(xpath = "//input[@data-test-id='range-filter-from-input']")
    WebElement priceRangeTextBoxFrom;

    @FindBy(xpath = "//input[@data-test-id='range-filter-to-input']")
    WebElement priceRangeTextBoxTo;

    @FindBy(xpath = "//div[@data-v-346efd2e]")
    WebElement foundItems;

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public void chooseOrderCategory(String text) {
        click(findByXpath("//li[@class='category-item']/a[contains(@data-test-category-name,'" + text + "')]"));
    }

    public void chooseBrand(String text) {
        if (showAllButtonForBrands.isDisplayed())
            click(showAllButtonForBrands);

        click("//span[@class='checkmark']/following::span[contains(text(),'" + text + "')]");
    }

    public void inputPriceRange(String priceFrom, String priceTo) {
        fillElement(priceRangeTextBoxFrom, priceFrom);
        fillElement(priceRangeTextBoxTo, priceTo);
        click(showFilteredByPriceButton);
    }

    public void inputPriceRange(String priceFrom) {
        fillElement(priceRangeTextBoxFrom, priceFrom);
        click(showFilteredByPriceButton);
        click(showFilteredByPriceButton);
    }

    public void addItemsToCart() {
        int count = 2;
        if (foundItems.isDisplayed()) {
            for (int i = 0; i <= count - 1; i++) {
                if (findByXpath
                        ("//div[@data-index='" + String.valueOf(i) + "']/div[@class='tile-actions']/div[@class='availability']")
                        .getText().contains("В наличии")) {
                    click("//div[@data-index='" + String.valueOf(i) + "']/div[@class='tile-actions']/div[@class='sale-block']//span[contains(text(),'корзину')]");
                    getLocker().saveItemPrice
                            (findByXpath("//div[@data-index='" + String.valueOf(i) + "']/div[@class='rollover-container']//a[@class='text-link name-link']"),
                                    findByXpath("//div[@data-index='" + String.valueOf(i) + "']/div[@class='rollover-container']//span[@class='price-number']/span")
                            );
                }
            }

        }
    }
}
