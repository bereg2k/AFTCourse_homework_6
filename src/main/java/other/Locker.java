package other;

import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class Locker {
    private static Locker INSTANCE = null;
    private Map<String, String> userOrderList = new HashMap<>();

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

    public void saveItemPrice(WebElement name, WebElement price) {
        userOrderList.put(name.getText(), price.getText().replaceAll("\\s+",""));
    }
}
