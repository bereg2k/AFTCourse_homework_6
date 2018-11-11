package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

/**
 * Класс для описания свойств и действия для Главной страницы сайта Озона.
 */
public class MainPage extends BasePage {

    // ссылка на значок Корзины вверху страницы
    @FindBy(xpath = "//span[@class='eMyOzon_Item_Bottom' and contains(text(),'Корзина')]")
    WebElement openCartButton;

    // ссылка на кнопку закрытия всплываюшего окна определения региона пользователя
    @FindBy(xpath = "//div[@class='eCityCompleteSelector_exactButton jsCloseExact']")
    WebElement locationDeliveryButton;

    public MainPage(WebDriver driver) {
        super(driver);

        // обработка ситуации, когда появляется всплываюший элемент определения региона пользователя.
        // может мешать выполнению действий на странице.
        // ловим исключение для случаев, когда данный элемент отсуствует на странице.
        try {
            if (locationDeliveryButton.isDisplayed())
                click(locationDeliveryButton);
        } catch (TimeoutException | NoSuchElementException ignored) {
            System.out.println("По умолчанию город доставки - МОСКВА.");
        }
    }

    /**
     * Выбор пункта главного меню с главной страницы сайта.
     * Пункты главного меню есть в шапке сайта и по факту доступны на любой странице.
     *
     * @param text название пункта меню
     */
    public void chooseMainMenuLink(String text) {
        click(By.xpath("//div[contains(@class, 'bHeaderCategoryLinks')]/a[contains(text(),'" + text + "')]"));
    }

    /**
     * Открытие Корзины с главной страницы.
     */
    public void openCart() {
        click(openCartButton);
    }
}
