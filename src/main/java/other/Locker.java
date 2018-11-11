package other;

import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

/**
 * Вспомогательный класс-хранилище. Необходим для хранения различных промежуточных данных для тестов.
 * Сделан по шаблону singleton.
 */
public class Locker {
    private static Locker INSTANCE = null;
    private Map<String, String> userOrderList = new HashMap<>(); // список комбинаций "товар-цена" для проверки добавления товаров

    /**
     * Getter для инициализации класса Locker, либо вызова его уже готового экземпляра.
     * @return экземпляр класса Locker
     */
    public static Locker getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Locker();
        }
        return INSTANCE;
    }

    private Locker() {
    }

    public Map<String, String> getUserOrderList() {
        return userOrderList;
    }

    /**
     * Сохранение связи "наименование товара + цена товара" в Map.
     * Значение цены в DOM имеет лишние пробелы, так что они убираются через метод replaceAll.
     * @param name наименование добавляемого товара
     * @param price цена добавляемого товара
     */
    public void saveItemPrice(WebElement name, WebElement price) {
        userOrderList.put(name.getText(), price.getText().replaceAll("\\s+",""));
    }
}
