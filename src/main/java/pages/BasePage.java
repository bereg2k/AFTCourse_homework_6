package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Базовый абстрактный класс для описания свойств и методов любой страницы сайта Озон.
 */
abstract class BasePage {

    private WebDriver driver;

    private static int timeout = 5; // общая переменная для установки таймаутов
    private static final boolean isScreenshotsAllowed = true; // флаг включения снятия промежуточных скриншотов

    BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); //инициализация класса страницы через PageFactory
    }

    /**
     * Метод прокрутки страницы для подхода к нужным элементам.
     * Реализован через JavascriptExecutor.
     *
     * @param element элемент, к которому делается прокрутка.
     */
    void scroll(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * Ожидание видимости элемента на странице.
     * Обработка исключения по таймауту нужна в виду особенностей динамического обновления страниц Озона.
     *
     * @param locator локатор элемента, который ожидаем
     */
    private void waitForVisible(By locator) {
        try {
            new WebDriverWait(driver, timeout)
                    .until((ExpectedConditions.visibilityOfElementLocated(locator)));
        } catch (TimeoutException ignored) {
        }
    }

    /**
     * Ожидание видимости элемента на странице.
     * Обработка исключения по таймауту нужна в виду особенностей динамического обновления страниц Озона.
     *
     * @param element элемент, который ожидаем
     */
    void waitForVisible(WebElement element) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException ignored) {
        }
    }

    /**
     * Ожидание доступности нажатия по элементу на странице.
     * Обработка исключения по таймауту нужна в виду особенностей динамического обновления страниц Озона.
     *
     * @param element элемент, который ожидаем
     */
    private void waitForClickable(WebElement element) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException ignored) {
        }
    }

    /**
     * Ожидание невидимости элемента на странице.
     * Необходима в виду наличия дополнительных элементов на страницах при загрузке, которые блокируют действия.
     * Обработка исключения по таймауту нужна в виду особенностей динамического обновления страниц Озона.
     *
     * @param locator локатор элемента, который ожидаем
     */
    void waitForInVisible(By locator) {
        try {
            new WebDriverWait(driver, timeout)
                    .until((ExpectedConditions.invisibilityOfElementLocated(locator)));
        } catch (TimeoutException ignored) {
        }
    }

    /**
     * Ожидание невидимости элемента на странице.
     * Необходима в виду наличия дополнительных элементов на страницах при загрузке, которые блокируют действия.
     * Обработка исключения по таймауту нужна в виду особенностей динамического обновления страниц Озона.
     *
     * @param element элемент, который ожидаем
     */
    void waitForInVisible(WebElement element) {
        try {
            new WebDriverWait(driver, timeout)
                    .until((ExpectedConditions.invisibilityOf(element)));
        } catch (TimeoutException e) {
            System.out.println("TOE caught! Locator = " + element.toString());
        }
    }

    /**
     * Вспомогательный метод поиск элемента по локатору.
     *
     * @param locator локатор искомого элемента
     * @return сам элемент в виде WebElement
     */
    private WebElement findByLocator(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Поиск элемента по xpath.
     *
     * @param xpath строка с xpath искомого элемента
     * @return сам элемент в виде WebElement
     */
    WebElement findByXpath(String xpath) {
        return findByLocator(By.xpath(xpath));
    }

    /**
     * Нажатие по элементу по локатору.
     * Метод ожидает появления элемента и возможности на него нажать.
     *
     * @param locator локатор элемента, на который кликаем
     */
    void click(By locator) {
        waitForVisible(locator);
        waitForClickable(findByLocator(locator));
        findByLocator(locator).click();
    }

    /**
     * Нажатие по элементу по xpath.
     * Метод ожидает появления элемента и возможности на него нажать.
     *
     * @param xpath строка с xpath элемента, на который кликаем
     */
    void click(String xpath) {
        waitForVisible(By.xpath(xpath));
        waitForClickable(findByXpath(xpath));
        findByXpath(xpath).click();
    }

    /**
     * Нажатие по элементу по его объекту.
     * Метод ожидает появления элемента и возможности на него нажать.
     *
     * @param element элемент, на который кликаем
     */
    void click(WebElement element) {
        waitForVisible(element);
        waitForClickable(element);
        element.click();
    }

    /**
     * Заполнение текстовых полей.
     *
     * @param element элемент текстового поля для заполнения
     * @param text    текст для заполнения
     */
    void fillElement(WebElement element, String text) {
        waitForVisible(element);
        waitForClickable(element);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Вспомогательный метод очистки текстовых полей для указания возможной цены для товара (фильтры).
     * Нужен в виду особенностей реализации поля для ввода с использованием авто-заполнения.
     *
     * @param element элемент поля для указания цены
     */
    void clearPriceRangeInputBox(WebElement element) {
        while (!element.getAttribute("value").equals("")) {
            click(element);
            element.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        }
    }

    /**
     * Проверка текстовых значений элементов на странице.
     *
     * @param element      проверяемый текстовый элемент
     * @param expectedText ожидаемое значение текста
     */
    void checkElementText(WebElement element, String expectedText) {
        waitForVisible(element);
        assertEquals("Значения текста не соотвествует ожидаемому",
                expectedText, element.getText());
    }

    /**
     * Метод добавления в шаг отчёта дополнительной текстовой информации.
     */
    @Attachment(value = "см. доп. информацию по шагу")
    String additionalInformation(String text) {
        return text;
    }

    /**
     * Метод добавления в шаг отчёта скриншотов.
     */
    @Attachment(value = "см. скриншот")
    byte[] takeScreenshot() {
        if (isScreenshotsAllowed) {
            try {
                BufferedImage screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(500)).takeScreenshot(driver).getImage();
                ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
                ImageIO.write(screenshot, "png", imageStream);
                imageStream.flush();
                byte[] imageInByte = imageStream.toByteArray();
                imageStream.close();
                return imageInByte;
            } catch (IOException ignored) {
                return null;
            }
        } else
            return "Screenshots are turned off! Refer to global variable 'isScreenshotAllowed' in BasePage.class".getBytes();
    }
}
