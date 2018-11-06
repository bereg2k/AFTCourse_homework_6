import org.junit.Test;
import steps.MainPageSteps;

/**
 * Реализовать следующий тестовый сценарий с применением подхода PageObject/PageFactory:
 * 1.   Перейдите на сервис http://www.ozon.ru/
 * 2.	Выполните поиск по «iPhone 7 Plus/8 Plus Black»
 * 3.	Из результатов поиска добавьте в корзину 5 товаров.
 * 4.	Выполнить поиск по "Galaxy Tab".
 * 5.	Из результатов поиска добавьте в корзину 2 товара.
 * 6.	Перейдите в корзину, убедитесь, что все добавленные ранее товары находятся в корзине
 * 7.	Проверить, что общая сумма покупки равна сумме всех добавленных ранее товаров со страницы поиска.
 * 8.	Удалите все товары из корзины
 * 9.	Проверьте, что корзина не содержит никаких товаров.
 */
public class OzonTest extends BaseTest{

    @Test
    public void ozonTestMain(){
        MainPageSteps mainPageSteps = new MainPageSteps();

        mainPageSteps.stepOpenCart();

    }
}