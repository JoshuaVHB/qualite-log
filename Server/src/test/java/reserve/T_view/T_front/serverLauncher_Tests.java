package reserve.T_view.T_front;

import reserve.controller.AppController;
import reserve.controller.MaterialController;
import reserve.controller.ReservationController;
import reserve.controller.UserController;
import reserve.controller.io.AppStorage;
import reserve.controller.io.FileStorage;
import reserve.model.*;
import reserve.view.WebServer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class serverLauncher_Tests {

    public static AppController controller;
    static boolean launched = false;

    public static void launch_server(){

        if (launched) return;
        AppStorage storage = new FileStorage();
        MaterialController materials = new MaterialController();
        ReservationController reservations = new ReservationController();
        UserController users = new UserController();
        controller = new AppController(storage, materials, reservations, users);
        controller.startApplication();

        if(users.getUsers().isEmpty())
            kickstartApp(controller);

        Runtime.getRuntime().addShutdownHook(new Thread(controller::endApplication, "shutdown-hook"));

        WebServer server = new WebServer(controller);
        new Thread(() -> server.open()).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        launched = true;
    }

    private static void kickstartApp(AppController controller) {
        User adminUser = new User(true, "admin", "-", "-", "0000001", "-", "password");
        User testUser = new User(false, "test", "-", "-", "0000002", "-", "password");

        controller.getUsers().addUser(adminUser);
        controller.getUsers().addUser(testUser);


        // also add dummy reservations and materials
        Material mat1 = new Material(OperatingSystem.AN, MaterialType.LAPTOP, "name", "version", 0);
        Material mat2 = new Material(OperatingSystem.AP, MaterialType.LAPTOP, "name2", "version", 0);
        controller.getMaterials().addMaterial(mat1);
        controller.getMaterials().addMaterial(mat2);
        Reservation res1 = new Reservation(adminUser, mat1, LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS));
        controller.getReservations().addReservation(res1);
    }



}
