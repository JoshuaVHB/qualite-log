package reserve.T_view.T_front;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import reserve.controller.AppController;
import reserve.controller.MaterialController;
import reserve.controller.ReservationController;
import reserve.controller.UserController;
import reserve.controller.io.AppStorage;
import reserve.controller.io.FileStorage;
import reserve.model.Material;
import reserve.view.WebServer;

import static reserve.T_view.T_front.T_serverLauncher.controller;
import static reserve.T_view.T_front.T_serverLauncher.launch_server;

public class T_ListeMateriel {


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
        driver.navigate().to("http://127.0.0.1:8080/listeMateriel/");

    }

    @AfterAll
    public static void quit() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }


    @Test
    public void should_remove_material_from_back_when_remove_button_pressed() {

        int size = controller.getMaterials().getAllMaterials().size();
        Material dummy = controller.getMaterials().getAllMaterials().get(size-1);
        WebElement deleteButton = driver.findElement(By.id(""+dummy.getId()));

        deleteButton.click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(size-1, controller.getMaterials().getAllMaterials().size());

    }

    @Test
    public void should_filter_by_type() {}


    @Test
    public void should_filter_by_OS() {}

    @Test
    public void should_make_reservation_when_valid_dates() {

    }

    @Test
    public void should_not_make_reservation_when_invalid_dates() {



    }
}
