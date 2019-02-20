package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import other.Locker;

import static org.junit.Assert.fail;

/**
 * Класс для описания свойств и действия для страницы заказа товаров сайта Озона.
 */
public class OrderPage extends BasePage {

    // кнопка "Показать все" для фильтра раздела "Бренды"
    @FindBy(xpath = "//div[@data-test-id='filter-block-brand']//span[@class='show-text' and contains(text(),'Посмотреть все')]")
    WebElement showAllButtonForBrands;

    // поле для ввода имени бренда для фильтра раздела "Бренды"
    @FindBy(xpath = "//div[@data-test-id='filter-block-brand']//input[@class='input']")
    WebElement searchBrandTextBox;

    // кнопка "Показать" для фильтра раздела "Цена"
    @FindBy(xpath = "//button[@data-test-id='range-filter-show']")
    WebElement showFilteredByPriceButton;

    // поле ввода для ввода цены "ОТ" для фильтра раздела "Цена"
    @FindBy(xpath = "//input[@data-test-id='range-filter-from-input']")
    WebElement priceRangeTextBoxFrom;

    // поле ввода для ввода цены "ДО" для фильтра раздела "Цена"
    @FindBy(xpath = "//input[@data-test-id='range-filter-to-input']")
    WebElement priceRangeTextBoxTo;

    // первый найденный элемент в списке результов поиска после применения фильтров
    @FindBy(xpath = "//div[@data-index='0']")
    WebElement firstFoundItem;

    // стандартный промежуточный элемент в виде 5 кружков, обозначающих загрузку элементов в списке
    @FindBy(xpath = "//div[@class='five-dots preloader']")
    WebElement preloader;

    //общий формат xpath для нахождения лейбла "В НАЛИЧИИ" у найденного товара (например, %d = 0 - первый товар в списке, 1 - второй...)
    private static String availabilityLabelFormat = "//div[@data-index='%d']/div[@class='tile-actions']/div[@class='availability']";

    //общий формат xpath для нахождения кнопки "В КОРЗИНУ" у найденного товара (например, %d = 0 - первый товар в списке, 1 - второй...)
    private static String addToCartButtonFormat = "//div[@data-index='%d']/div[@class='tile-actions']/div[@class='sale-block']//span[contains(text(),'корзину')]";

    //общий формат xpath для нахождения наименования товара в результатах поиска (например, %d = 0 - первый товар в списке, 1 - второй...)
    private static String itemNameLabelFormat = "//div[@data-index='%d']/div[@class='rollover-container']//a[@class='text-link name-link']";

    //общий формат xpath для нахождения цены товара в результатах поиска (например, %d = 0 - первый товар в списке, 1 - второй...)
    private static String itemPriceLabelFormat = "//div[@data-index='%d']/div[@class='rollover-container']//span[@class='price-number']/span";

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Выбор основной категории товара для заказа.
     * Этот же метод используется для выбора подкатегории товара, т.к. использует те же локаторы для своих элементов.
     *
     * @param text название категории/подкатегории
     */
    public void chooseOrderCategory(String text) {
        click(findByXpath("//li[@class='category-item']/a[contains(@data-test-category-name,'" + text + "')]"));
    }

    /**
     * Выбор бренда заказываемого товара.
     * Когда список брендов в текущем представлении превышает 5 штук, то доступна кнопка "ПОКАЗАТЬ ВСЕ".
     * Эта кнопка раскрывает полный список доступных брендов, отсортированных по алфавиту,
     * а также отображает текстовое поле для быстрого поиска.
     * После этого происходит ввод имени бренда и нажатие по появившемуся в списке варианту (чек-бокс).
     * <p>
     * В случаях, когда брендов 5 или менее, мы пропускаем нажатие на эту кнопку (её просто не будет, поэтому ловим исключение).
     * В этом случае мы выбираем нужные бренду по чек-боксам сразу.
     * <p>
     * После всего ожидаем загрузки получившегося списка товара после фильтрации (waitForInVisible(preloader)).
     *
     * @param text название бренда
     */
    public void chooseBrand(String text) {
        try {
            if (showAllButtonForBrands.isDisplayed()) {
                click(showAllButtonForBrands);
                fillElement(searchBrandTextBox, text);
            }
        } catch (NoSuchElementException | StaleElementReferenceException ignored) {
        }
        click("//div[@data-test-id='filter-block-brand']//span[@class='checkmark']/following-sibling::span[contains(text(),'" + text + "')]");
        waitForInVisible(preloader);
    }

    /**
     * Ввод диапазона для возможной цены у товара.
     * Последовательность действий выбрана именно такая, в виду особенностей реализации текстовых полей для ввода цены
     * через автозаполнение (а точнее, автоПЕРЕзаполнение).
     *
     * @param priceFrom левая граница цены (цена ОТ)
     */
    public void inputPriceRange(String priceFrom) {
        waitForVisible(priceRangeTextBoxFrom);
        scroll(priceRangeTextBoxFrom);
        clearPriceRangeInputBox(priceRangeTextBoxFrom);
        fillElement(priceRangeTextBoxFrom, priceFrom);
        click(priceRangeTextBoxTo);
        //click(showFilteredByPriceButton);
        waitForInVisible(preloader);
    }

    /**
     * Ввод диапазона для возможной цены у товара (расширенная версия).
     * Последовательность действий выбрана именно такая, в виду особенностей реализации текстовых полей для ввода цены
     * через автозаполнение (а точнее, автоПЕРЕзаполнение).
     *
     * @param priceFrom левая граница цены (цена ОТ)
     * @param priceTo   правая граница цены (цена ДО)
     */
    public void inputPriceRange(String priceFrom, String priceTo) {
        waitForVisible(priceRangeTextBoxFrom);
        scroll(priceRangeTextBoxFrom);
        clearPriceRangeInputBox(priceRangeTextBoxFrom);
        fillElement(priceRangeTextBoxFrom, priceFrom);
        click("//label[contains(text(),'от')]");
        waitForInVisible(preloader);
        clearPriceRangeInputBox(priceRangeTextBoxTo);
        fillElement(priceRangeTextBoxTo, priceTo);
        click("//label[contains(text(),'до')]");
        //click(showFilteredByPriceButton);
        waitForInVisible(preloader);
    }

    /**
     * Добавление товара в Корзину по нажатию на соответствующую кнопку.
     * Добавляются несколько первых товаров (либо один).
     *
     * @param count количество товаров для добавления, начиная с самого первого.
     */
    public void addItemsToCart(int count) {
        waitForVisible(firstFoundItem);

        // обработка исключения для случаев, когда найдено меньше доступных товаров, чем нужно добавить по шагу теста.
        // такая обработка нужна исключительно для упрощения вывода итоговой ошибки в отчёт (и большей наглядности).
        // данная проверка покрывает 2 случая:
        // 1. нужно добавить 4 товара, но найдено только 3, хоть и все "В НАЛИЧИИ".
        // 2. нужно добавить 4 товара, найдено 5, но 2 из них с пометкой "НЕТ В НАЛИЧИИ".
        int countAvailable = 0, countUnavailable = 0, j = 0;
        try {
            while (countAvailable != count) {
                if (findByXpath(String.format(availabilityLabelFormat, j)).getText().contains("В наличии")) {
                    countAvailable++; //считаем количество доступных товаров, начиная с первого
                } else {
                    countUnavailable++; //считаем количество НЕдоступных товаров, начиная с первого
                }
                j++;
            }
        } catch (NoSuchElementException ignored) {
            fail("Найдено менее " + count + " доступных товаров для добавления!");
        }

        // если найденных доступных товаров хватает, то проходимся по порядку и добавляем все доступные.
        // в DOM индексация найденных элементов начинается с 0, а не 1.
        // добавляем ещё количество товаров, которые не в наличии, чтобы пройтись от начала до нужного количества доступных.
        for (int i = 0; i <= (count - 1 + countUnavailable); i++) {
            // если очередной товар в наличии, то добавляем его в Корзину по соответствующей кнопке
            if (findByXpath(String.format(availabilityLabelFormat, i)).getText().contains("В наличии")) {
                click(String.format(addToCartButtonFormat, i));

                //открываем хранилище и записываем туда название+цену каждого добавляемого в Корзину товара
                Locker.getInstance().saveItemPrice
                        (findByXpath(String.format(itemNameLabelFormat, i)),
                                findByXpath(String.format(itemPriceLabelFormat, i)));

                // добавляем информацию о добавленном товаре в шаг для отчёта
                additionalInformation("Добавлен товар: \"" +
                        findByXpath(String.format(itemNameLabelFormat, i)).getText() + "\"\n" +
                        "по цене = " + findByXpath(String.format(itemPriceLabelFormat, i)).getText() + " руб.");
            }
        }
    }
}
