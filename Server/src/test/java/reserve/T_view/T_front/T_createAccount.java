package reserve.T_view.T_front;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import reserve.model.User;

import static reserve.T_view.T_front.serverLauncher_Tests.controller;
import static reserve.T_view.T_front.serverLauncher_Tests.launch_server;

public class T_createAccount {

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
        driver.navigate().to("http://127.0.0.1:8080/inscription/");

    }

    @AfterAll
    public static void quit() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }


    @Test
    public void should_add_new_user_to_users_list() {

        int start = controller.getUsers().getUsers().size();
        User dummy = new User(false,
                "dummy",
                "dummy",
                "dummy",
                "1234567",
                "dummy@dummy.dummy",
                "dummy123");

        WebElement admin_check = driver.findElement(By.id("filter-include-role"));
        WebElement prenom_field = driver.findElement(By.id("prenom"));
        WebElement nom_field = driver.findElement(By.id("nom"));
        WebElement email_field = driver.findElement(By.id("email"));
        WebElement password_field = driver.findElement(By.id("mdp"));
        WebElement confirm_field = driver.findElement(By.id("cmdp"));
        WebElement submit_button = driver.findElement(By.id("submit"));

        if (dummy.isAdmin()) admin_check.click();
        prenom_field.sendKeys(dummy.getFirstName());
        nom_field.sendKeys(dummy.getLastName());
        email_field.sendKeys(dummy.getEmail());
        password_field.sendKeys(dummy.getPassword());
        confirm_field.sendKeys(dummy.getPassword());

        submit_button.click();


        // -- Check
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.id("btn_popup")).click();

        Assertions.assertEquals(start+1,  controller.getUsers().getUsers().size());


    }


}
