package reserve.T_view.T_front;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import static reserve.T_view.T_front.serverLauncher_Tests.launch_server;

public class T_Connexion {

    private static WebDriver driver;

    @BeforeAll
    public static void setup() {

        launch_server();

        //////////////////////////////

        driver = new ChromeDriver();
        driver.get("http://127.0.0.1:8080/");

        // - Log as admin

        driver.manage().deleteAllCookies();
        driver.navigate().to("http://127.0.0.1:8080/connexion/");

    }

    @AfterAll
    public static void quit() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    @Test
    public void should_not_connect_when_entering_dummy_values() {


        WebElement matricule_field =  driver.findElement(By.id("sel-matField"));
        WebElement password_field =  driver.findElement(By.id("password"));
        WebElement connexion_button =  driver.findElement(By.id("sel-conButton"));

        matricule_field.sendKeys("7272727");
        password_field.sendKeys("INSERT INTO CLIENT VALUES('dummy')");

        connexion_button.click();

        String url = driver.getCurrentUrl();

        Assertions.assertEquals("http://127.0.0.1:8080/connexion/", url);
    }

    @Test
    public void should_not_connect_when_entering_correct_values() {


        WebElement matricule_field =  driver.findElement(By.id("sel-matField"));
        WebElement password_field =  driver.findElement(By.id("password"));
        WebElement connexion_button =  driver.findElement(By.id("sel-conButton"));

        matricule_field.sendKeys("0000001");
        password_field.sendKeys("password");

        connexion_button.click();

        String url = driver.getCurrentUrl();

        Assertions.assertEquals("http://127.0.0.1:8080/accueil/", url);
    }



}
