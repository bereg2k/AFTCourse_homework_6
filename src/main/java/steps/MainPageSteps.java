package steps;

import io.qameta.allure.Step;
import pages.MainPage;

public class MainPageSteps extends BaseSteps {

    MainPage mainPage = new MainPage();

    @Step("Открыта Корзина")
    public void stepOpenCart(){
        mainPage.openCart();
    }
}
