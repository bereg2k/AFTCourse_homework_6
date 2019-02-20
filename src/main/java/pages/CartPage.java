package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.Locker;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Класс для описания свойств и действия для страницы Корзины сайта Озона.
 */
public class CartPage extends BasePage {

    private int totalSum = 0; // доп. переменная для подсчёта общей суммы товаров в Корзине

    // кнопка закрытия всплывающего списка только что удаленных товаров
    @FindBy(xpath = "//div[contains(@class,'eRemovedCartItems_removeAll jsRemoveAll')]")
    WebElement closeRemovedItemListButton;

    // кнопка "Удалить все"
    @FindBy(xpath = "//div[@class='cart-list']//button[contains(text(),'Удалить')]")
    WebElement clearAllCartButton;

    // общая сумма товаров в Корзине
    // Примечание: в XPATH вместо 'Итого' можно подставить 'Скидка' или 'Товары'
    @FindBy(xpath = "//div[@data-test-id='cart-total']//div[contains(text(), 'Итого')]/../..//span[contains(@class,'price-number')]")
    WebElement totalSumLabel;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Проверка добавленных ранее товаров на наличие их в Корзине.
     * Используем хранилище Locker для сверки значений наименований товара и их цены.
     */
    public void checkItemsInCart() {
        waitForInVisible(By.xpath("//div[contains(@class, 'bCartPage BlockActions')]"));

        for (Object o : Locker.getInstance().getUserOrderList().entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            assertEquals(pair.getKey().toString(), findByXpath("//div[@class='cart']//div[@data-test-id='cart-split-item']//div[@class='desc']/a[contains(text(),'" + pair.getKey() + "')]").getText());
        }

        // снимаем скриншот результата для отчёта
        takeScreenshot();
    }

    /**
     * Проверка отображаемой общей суммы в Корзине с общей суммой добавленных ранее товаров.
     */
    public void checkCartTotalSum() {
        for (Object o : Locker.getInstance().getUserOrderList().entrySet()) { // суммируем цену всех ранее добавленных товаров
            Map.Entry pair = (Map.Entry) o;
            totalSum += Integer.valueOf(pair.getValue().toString());
        }

        // отображение итоговой суммы в Корзине идёт вместе с валютой. Отрезаем все лишнее с помощью replaceAll.
        assertEquals("Общая сумма товаров в Корзине не соответствует сумме добавленных в неё товаров!",
                (totalSumLabel.getText().replaceAll("\\s+|[а-яА-я]|\\.", "")), String.valueOf(totalSum));
    }

    /**
     * Очищение Корзины от всех добавленных туда товаров.
     * Наличие цикла while необходимо для случаев, когда с одного нажатия на "Удалить всё" все товары не пропадают.
     * Это происходит в случае их группировки по какому-то внутреннему принципу (возможно, сроки доставки).
     */
    public void clearCart() {
        try {
            while (clearAllCartButton.isDisplayed()) {
                waitForInVisible(By.xpath("//div[contains(@class, 'bCartPage BlockActions')]"));
                click(clearAllCartButton);
                waitForInVisible(By.xpath("//div[contains(@class, 'bCartPage BlockActions')]"));
                click(closeRemovedItemListButton);
            }
        } catch (NoSuchElementException ignored) {
        }
        Locker.getInstance().getUserOrderList().clear(); // очищаем хранилище
    }

    /**
     * Проверка Корзины на пустоту. Ищем надпись "Корзина пуста".
     */
    public void isCartEmpty() {
        checkElementText(findByXpath("//div[@class='cart']//h1[@class='cart-title']"), "Корзина пуста");
        takeScreenshot(); // снимаем скриншот результата для отчёта
    }
}