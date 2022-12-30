package reserve.T_view.T_front;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static reserve.T_view.T_front.serverLauncher_Tests.controller;
import static reserve.T_view.T_front.serverLauncher_Tests.launch_server;

public class T_ListeProfiles {

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

        WebElement matricule_field =  driver.findElement(By.id("sel-matField"));
        WebElement password_field =  driver.findElement(By.id("password"));
        WebElement connexion_button =  driver.findElement(By.id("sel-conButton"));

        matricule_field.sendKeys("0000001");
        password_field.sendKeys("password");

        connexion_button.click();
        driver.navigate().to("http://127.0.0.1:8080/profils/");

    }

    @AfterAll
    public static void quit() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    @Test
    public void should_display_all_profiles() {

        List<WebElement> profiles = driver.findElements(By.className("content"));

        Assertions.assertEquals(controller.getUsers().getUsers().size(), profiles.size());

    }

}
