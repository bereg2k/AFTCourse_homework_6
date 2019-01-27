package stepsDefs;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import other.Locker;
import other.TestProperties;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Вспомогательный класс для дополнительных методов, запускающихся до и после тестов.
 */
public class Hooks {

    private static WebDriver driver; //статичный класс для общего webdriver
    private static Locker locker = Locker.getInstance(); // получение статичного экземпляра класса для хранилища
    private static Properties properties = TestProperties.getInstance().getProperties(); //инициализация/вызов класса свойств

    static WebDriver getDriver() {
        return driver;
    }

    /**
     * Метод для инициализации первичных свойств и действий для выполнения каждого сценария.
     * Запускается перед каждым сценарием.
     */
    @Before
    public static void setUp() {
        // убираем возможные надоедливые нотификации в Chrome
        // (например, "Разрешить всплывающие окна" или "Разрешить определение местоположения")
        // все они могут мешать выполнению тестов
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");

        switch (properties.getProperty("browser")){ //запрос значения параметра browser из файла свойств application.properties
            case "firefox":
                System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver"));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver(chromeOptions);
                break;
            default:
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver(chromeOptions);
        }

        String baseUrl = properties.getProperty("url"); // запрос значения параметра url из файла свойств application.properties
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //установка обшего явного ожидания для всех объектов
        driver.manage().window().maximize(); // открываем окно на весь экран
        driver.get(baseUrl); // переходим по основной ссылке из файла свойств
    }

    /**
     * Закрывающий метод, выполняющийся после каждого сценария. Расширен сохранением скриншотов в случае ошибок.
     * @param scenario сценарий, после которого выполняется закрывающий метод
     */
    @After
    public static void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        locker.getUserOrderList().clear(); // очищаем хранилище
        driver.quit(); // закрываем браузер
    }
}
