import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features =
        {"src/test/resources"},
        glue = {"stepsDefs"},
        tags = {"@phones"},
        plugin = {"ru.yandex.qatools.allure.cucumberjvm.AllureReporter"})

/**
 * Created by student on 05.11.2018.
 */
public class CucumberRunner {

}
