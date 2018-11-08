package pages;


import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CartPage extends BasePage {

    private int totalSum = 0;

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
        assertEquals((findByXpath("//div[contains(@class,'eCartTotal_summPrice')]").getText().replaceAll("\\s+", "")), String.valueOf(totalSum));
    }

    @Step("очищаем Корзину")
    public void clearCart() {
        click("//div[contains(@class,'RemoveAll')]");
    }

    @Step("проверяем пуста ли Корзина")
    public void isCartEmpty() {
        checkElementText(findByXpath("//span[contains(@class,'jsInnerContentpage_title')]"), "Корзина пуста");
    }
}