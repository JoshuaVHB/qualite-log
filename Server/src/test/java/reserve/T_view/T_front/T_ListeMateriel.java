package reserve.T_view.T_front;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import reserve.controller.MaterialController;
import reserve.model.Material;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static reserve.T_view.T_front.serverLauncher_Tests.controller;
import static reserve.T_view.T_front.serverLauncher_Tests.launch_server;

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
        if (size==0) {
            controller.getMaterials().addMaterial(new Material());
        }
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
    public void should_make_reservation_when_valid_dates() {


        List<Material> mats = controller.getMaterials().getAllMaterials();
        MaterialController.filterByAvailability(mats);

        int start = controller.getReservations().getNumberOfReservations();

        if (mats.size()>0) {
            Material mat = mats.get(0);
            WebElement reservation_checkbox = driver.findElement(By.id("cbox-" + mat.getId()));
            reservation_checkbox.click();

            WebElement begin = driver.findElement(By.id("reservation-from"));
            WebElement end = driver.findElement(By.id("reservation-to"));

            LocalDate today = LocalDate.now();
            LocalDate tomorrow = LocalDate.now().plusDays(1);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            begin.sendKeys(today.format(dateTimeFormatter));
            end.sendKeys(tomorrow.format(dateTimeFormatter));

            driver.findElement(By.id("reserve-submit-btn")).click();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Assertions.assertEquals(start + 1, controller.getReservations().getNumberOfReservations());
        }
    }

}
