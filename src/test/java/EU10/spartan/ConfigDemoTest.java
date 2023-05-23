package EU10.spartan;

import org.junit.jupiter.api.Test;
import utilities.ConfigReader;

public class ConfigDemoTest {

    @Test
    public void test1() {
        System.out.println("Project name = " + ConfigReader.getProperty("serenity.project.name"));
        System.out.println("Username = " + ConfigReader.getProperty("spartan.editor.username"));
        System.out.println("Password = " + ConfigReader.getProperty("spartan.editor.password"));


    }
}
