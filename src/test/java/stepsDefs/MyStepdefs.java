package stepsDefs;

import cucumber.api.PendingException;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.MainPage;
import pages.OrderPage;

public class MyStepdefs {
    private WebDriver driver = Hooks.driver;
    private MainPage mainPage = new MainPage(driver);
    private OrderPage orderPage = new OrderPage(driver);
    private CartPage cartPage = new CartPage(driver);

    @Когда("^выбран пункт главного меню \"([^\"]*)\"$")
    public void chooseMainMenuLink(String text) {
        mainPage.chooseMainMenuLink(text);
    }

    @И("^выбрана категория \"([^\"]*)\"$")
    public void chooseOrderCategory(String orderCategory) {
        orderPage.chooseOrderCategory(orderCategory);
    }

    @И("^выбран производитель \"([^\"]*)\"$")
    public void chooseBrand(String brandName) {
        orderPage.chooseBrand(brandName);
    }

    @И("^заполнена цена ОТ \"([^\"]*)\"$")
    public void inputPriceRangeFrom(String priceFrom) {
        orderPage.inputPriceRange(priceFrom);
    }

    @И("^добавлен товар в Корзину$")
    public void addItemsToCart() {
        orderPage.addItemsToCart();
    }

    @И("^открыта Корзина$")
    public void openCart() {
        mainPage.openCart();
    }

    @Тогда("^проверить товары в Корзине$")
    public void checkItemsInCart() {
        cartPage.checkItemsInCart();
    }

    @И("^проверить итоговую сумму в Корзине$")
    public void checkCartTotalSum() {
        cartPage.checkCartTotalSum();
    }

    @Когда("^очищена Корзина$")
    public void clearCart() {
        cartPage.clearCart();
    }

    @Тогда("^проверить что корзина пуста$")
    public void проверитьЧтоКорзинаПуста() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
