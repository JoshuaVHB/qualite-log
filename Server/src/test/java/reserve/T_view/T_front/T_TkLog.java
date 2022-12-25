package reserve.T_view.T_front;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * This should work on CHROME
 */
public class T_TkLog {

    // TODO : trouver des tests

    @Test
    public void should_succed_when_() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://127.0.0.1:8080/");

        String title = driver.getTitle();
        assertEquals("Document", title);

        driver.quit();
    }



}
