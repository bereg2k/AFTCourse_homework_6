package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderPage extends BasePage {
    @FindBy(xpath = "//div[@data-test-id='filter-block-brand']//../span[@class='show-text']")
    WebElement showAllButtonForBrands;

    @FindBy(xpath = "//div[@data-test-id='filter-block-brand']//input")
    WebElement searchBrandTextBox;

    @FindBy(xpath = "//button[@data-test-id='range-filter-show']")
    WebElement showFilteredByPriceButton;

    @FindBy(xpath = "//input[@data-test-id='range-filter-from-input']")
    WebElement priceRangeTextBoxFrom;

    @FindBy(xpath = "//input[@data-test-id='range-filter-to-input']")
    WebElement priceRangeTextBoxTo;

    @FindBy(xpath = "//div[@data-index='0']")
    WebElement firstFoundItem;

    @FindBy(xpath = "//div[@class='five-dots preloader']")
    WebElement preloader;

    private static String availabilityLabelFormat = "//div[@data-index='%d']/div[@class='tile-actions']/div[@class='availability']";
    private static String addToCartButtonFormat = "//div[@data-index='%d']/div[@class='tile-actions']/div[@class='sale-block']//span[contains(text(),'корзину')]";
    private static String itemNameLabelFormat = "//div[@data-index='%d']/div[@class='rollover-container']//a[@class='text-link name-link']";
    private static String itemPriceLabelFormat = "//div[@data-index='%d']/div[@class='rollover-container']//span[@class='price-number']/span";

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public void chooseOrderCategory(String text) {
        click(findByXpath("//li[@class='category-item']/a[contains(@data-test-category-name,'" + text + "')]"));
    }

    public void chooseBrand(String text) {
        if (showAllButtonForBrands.isDisplayed())
            click(showAllButtonForBrands);
        fillElement(searchBrandTextBox, text);
        click("//span[@class='checkmark']/following::span[contains(text(),'" + text + "')]");
        waitForVisible(preloader);
    }

    public void inputPriceRange(String priceFrom, String priceTo) {
        fillElement(priceRangeTextBoxFrom, priceFrom);
        fillElement(priceRangeTextBoxTo, priceTo);
        click(showFilteredByPriceButton);
    }

    public void inputPriceRange(String priceFrom) {
        waitForVisible(priceRangeTextBoxFrom);
        scroll(priceRangeTextBoxFrom);
        clearPriceRangeInputBox(priceRangeTextBoxFrom);
        fillElement(priceRangeTextBoxFrom, priceFrom);
        click(priceRangeTextBoxTo);
        click(showFilteredByPriceButton);
        waitForVisible(preloader);
    }

    public void addItemsToCart(int count) {
        waitForVisible(firstFoundItem);
        for (int i = 0; i <= count - 1; i++) {
            if (findByXpath
                    (String.format(availabilityLabelFormat, i)).getText().contains("В наличии")) {
                click(String.format(addToCartButtonFormat, i));
                getLocker().saveItemPrice(findByXpath(String.format(itemNameLabelFormat, i)), findByXpath(String.format(itemPriceLabelFormat, i)));
            } else {
                i--;
            }
        }
    }
}
