package reserve.T_view.T_front;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reserve.T_view.T_front.serverLauncher_Tests.launch_server;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * This should work on CHROME
 */
public class T_Accueil {

    private static WebDriver driver;

    @BeforeAll
    public static void setupDriver() {

        launch_server();
        driver = new ChromeDriver();
        driver.get("http://127.0.0.1:8080/");

    }

    @AfterEach
    public void goBackToHomepage() {

        driver.navigate().to("http://127.0.0.1:8080/accueil");
    }

    @AfterAll
    public static void quit() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    @Test
    public void should_be_accueil_on_load() {

        String title = driver.getTitle();
        String url = driver.getCurrentUrl();

        assertEquals("Page d'accueil", title);
        assertEquals("http://127.0.0.1:8080/accueil/", url);

    }

    @Nested
    @DisplayName("1-NOT LOGGED")
    public class NotLogged {

        @Test()
        //@Disabled // For some reasons, it doesnt work, but works on its own
        public void should_redirect_to_connexion_page_on_button_press() {
            driver.manage().deleteAllCookies();
            driver.navigate().refresh();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            WebElement connexionButton = driver.findElement(By.id("sel-conButton"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/connexion/", url);
        }

        @Test
        public void should_redirect_to_material_list_page_on_button_press() {

            WebElement connexionButton = driver.findElement(By.id("sel-listeMatButton"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/listeMateriel/", url);
        }



    }

    @Nested
    @DisplayName("2-LOGGED AS ADMIN")
    // Cette annotation permet de bypass le fait de devoir rendre les méthodes @BeforeAll statiques.
    // Java ne supporte pas les méthodes statiques dans les nested-classes ! POG
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class LoggedAsAdmin {
        @BeforeAll
        public void login() {
            driver.manage().deleteAllCookies();
            driver.navigate().to("http://127.0.0.1:8080/connexion/");

            WebElement matricule_field =  driver.findElement(By.id("sel-matField"));
            WebElement password_field =  driver.findElement(By.id("password"));
            WebElement connexion_button =  driver.findElement(By.id("sel-conButton"));

            matricule_field.sendKeys("0000001");
            password_field.sendKeys("password");

            connexion_button.click();
        }

        @After
        public void logout() {
            driver.manage().deleteAllCookies();
        }

        @Test
        public void should_redirect_to_profil() {

            WebElement connexionButton = driver.findElement(By.id("sel-profil"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/profil/", url);

        }
        @Test
        public void should_redirect_to_all_materials() {

            WebElement connexionButton = driver.findElement(By.id("sel-listeMatButton"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/listeMateriel/", url);

        }

        @Test
        public void should_redirect_to_reserved_materials() {

            WebElement connexionButton = driver.findElement(By.id("sel-matReserved"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/materielReserve/", url);

        }

        @Test
        public void should_redirect_to_account_creation() {

            WebElement connexionButton = driver.findElement(By.id("sel-createAccount"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/inscription/", url);

        }

        @Test
        public void should_redirect_to_material_creation() {

            WebElement connexionButton = driver.findElement(By.id("sel-createMaterial"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/creerMateriel/", url);

        }

        @Test
        public void should_redirect_to_all_profils() {

            WebElement connexionButton = driver.findElement(By.id("sel-allProfils"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/profils/", url);

        }

    }

    @Nested
    @DisplayName("2-LOGGED AS USER")
    // Cette annotation permet de bypass le fait de devoir rendre les méthodes @BeforeAll statiques.
    // Java ne supporte pas les méthodes statiques dans les nested-classes ! POG
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class LoggedAsUser {
        @BeforeAll
        public void login() {
            driver.navigate().to("http://127.0.0.1:8080/connexion/");

            WebElement matricule_field =  driver.findElement(By.id("sel-matField"));
            WebElement password_field =  driver.findElement(By.id("password"));
            WebElement connexion_button =  driver.findElement(By.id("sel-conButton"));

            matricule_field.sendKeys("0000002");
            password_field.sendKeys("password");

            connexion_button.click();
        }

        @After
        public void logout() {
            driver.manage().deleteAllCookies();
        }

        @Test
        public void should_redirect_to_profil() {

            WebElement connexionButton = driver.findElement(By.id("sel-profil"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/profil/", url);

        }
        @Test
        public void should_redirect_to_all_materials() {

            WebElement connexionButton = driver.findElement(By.id("sel-listeMatButton"));
            connexionButton.click();
            String url = driver.getCurrentUrl();
            assertEquals("http://127.0.0.1:8080/listeMateriel/", url);

        }

    }





}
