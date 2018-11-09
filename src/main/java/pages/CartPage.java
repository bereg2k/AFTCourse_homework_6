package pages;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CartPage extends BasePage {

    private int totalSum = 0;

    @FindBy(xpath = "//div[contains(@class,'eRemovedCartItems_removeAll jsRemoveAll')]")
    WebElement closeRemovedItemListButton;

    @FindBy(xpath = "//div[contains(@class,'bIconButton mRemove mGray jsRemoveAll')]")
    WebElement clearAllCartButton;

    @FindBy(xpath = "//div[contains(@class, 'bCartPage BlockActions')]")
    WebElement actionBlocker;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("проверяем наличие заказанных товаров в Корзине")
    public void checkItemsInCart() {
        for (Object o : getLocker().getUserOrderList().entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            assertEquals(pair.getKey().toString(), findByXpath("//span[contains(text(),'" + pair.getKey() + "')]").getText());
            totalSum += Integer.valueOf(pair.getValue().toString());
        }
    }

    @Step("проверяем итоговую сумму заказа в Корзине")
    public void checkCartTotalSum() {
        assertEquals((findByXpath("//div[contains(@class,'eCartTotal_summPrice')]").getText().replaceAll("\\s+|[а-яА-я]|\\.", "")), String.valueOf(totalSum));
    }

    @Step("очищаем Корзину")
    public void clearCart() {
        try {
            while (clearAllCartButton.isDisplayed()) {
                click(clearAllCartButton);
                waitForInVisible(By.xpath("//div[contains(@class, 'bCartPage BlockActions')]"));
                click(closeRemovedItemListButton);
            }
        } catch (NoSuchElementException ignored) {
        }
    }

    @Step("проверяем пуста ли Корзина")
    public void isCartEmpty() {
        checkElementText(findByXpath("//span[contains(@class,'jsInnerContentpage_title')]"), "Корзина пуста");
    }
}