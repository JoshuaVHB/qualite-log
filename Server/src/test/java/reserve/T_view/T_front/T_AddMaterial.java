package reserve.T_view.T_front;

import static reserve.T_view.T_front.serverLauncher_Tests.controller;
import static reserve.T_view.T_front.serverLauncher_Tests.launch_server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;

public class T_AddMaterial {

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
        driver.navigate().to("http://127.0.0.1:8080/creerMateriel/");

    }

    @AfterAll
    public static void quit() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    @Test
    public void should_create_material_when_form_is_filled_correctly() {

        Material dummy = new Material(OperatingSystem.AN, MaterialType.LAPTOP, "DummyName", "10.0", 727);
        int start = controller.getMaterials().getAllMaterials().size();

        // -- Fill the form

        Select typeMat = new Select(driver.findElement(By.id("type")));
        Select os = new Select(driver.findElement(By.id("OS")));
        WebElement name = driver.findElement(By.id("nom"));
        WebElement version = driver.findElement(By.id("version"));
        WebElement reference = driver.findElement(By.id("reference"));

        os.selectByVisibleText("Android");
        typeMat.selectByVisibleText("Ordinateur portable");

        name.sendKeys(dummy.getName());
        version.sendKeys(dummy.getVersion());
        reference.sendKeys("" + dummy.getNumRef());

        // -- Submit

        driver.findElement(By.id("submit")).click();

        // -- Check if the back-end list has increased

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(
                start+1,controller.getMaterials().getAllMaterials().size()
        );
    }


}
