package EU10.bookit;

import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.Test;
import utilities.ConfigReader;

@SerenityTest
public class BookitEnvTest {

    @Test
    public void test1(){

        System.out.println(ConfigReader.getProperty("base.url"));
        System.out.println(ConfigReader.getProperty("dbUsername"));
        System.out.println(ConfigReader.getProperty("dbPassword"));
    }
}
