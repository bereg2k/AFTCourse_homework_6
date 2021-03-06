package stepsDefs;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.MainPage;
import pages.OrderPage;

/**
 * Собрание аннотированных методов для связи между .feature файлом для Cucumber и методами классов PageObject
 */
public class MyStepdefs {
    private WebDriver driver = Hooks.getDriver();
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

    @И("^выбрана подкатегория \"([^\"]*)\"$")
    public void chooseOrderSubCategory(String orderSubCategory) {
        orderPage.chooseOrderCategory(orderSubCategory);
    }

    @И("^выбран производитель \"([^\"]*)\"$")
    public void chooseBrand(String brandName) {
        orderPage.chooseBrand(brandName);
    }

    @И("^заполнена цена ОТ \"([^\"]*)\"$")
    public void inputPriceRangeFrom(String priceFrom) {
        orderPage.inputPriceRange(priceFrom);
    }

    @И("^заполнена цена ОТ \"([^\"]*)\" ДО \"([^\"]*)\"$")
    public void inputPriceRangeFromTo(String priceFrom, String priceTo) {
        orderPage.inputPriceRange(priceFrom, priceTo);
    }

    @И("^добавлены товары в Корзину - первые (\\d+) шт.$")
    public void addItemsToCart(int i) {
        orderPage.addItemsToCart(i);
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
    public void isCartEmpty() {
        cartPage.isCartEmpty();
    }
}
