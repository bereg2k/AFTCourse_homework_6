package other;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Вспомогательный класс для работы с файлом свойств "application.properties" (есть в корневом каталоге).
 * В файле хранятся различные глобальные константы, типа используемого браузера или адреса главного ресурса.
 * Сделан по шаблону singleton.
 */
public class TestProperties {
    private final Properties properties = new Properties(); // экземпляр класса Properties
    private static TestProperties INSTANCE = null;

    /**
     * Конструктор TestProperties, в рамках которого происходит загрузка
     * в экземляр класса Properties нужных свойств из файла "application.properties".
     */
    private TestProperties() {
        try {
            properties.load(new FileInputStream(new File(
                    System.getProperty("environment", "application") + ".properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter для инициализации класса TestProperties, либо вызова его уже готового экземпляра.
     * @return экземпляр класса TestProperties
     */
    public static TestProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestProperties();
        }
        return INSTANCE;
    }

    /**
     * Getter для доступа к экземпляру класса Properties. Для итоговой работы с файлом свойств нам нужен именно он.
     * @return экземпляр класса Properties
     */
    public Properties getProperties() {
        return properties;
    }
}
