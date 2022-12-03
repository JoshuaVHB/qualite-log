package reserve;

import reserve.controller.AppController;
import reserve.controller.MaterialController;
import reserve.controller.ReservationController;
import reserve.controller.UserController;
import reserve.controller.io.AppStorage;
import reserve.controller.io.FileStorage;
import reserve.view.WebServer;

/**
 * PENSEZ A RAJOUTER UNE RUN CONFIGURATION DE TEST JUNIT AVEC MAINTEST EN MAIN !!
 * BANDE DE PLOUCS DE ECLIPSEUX
 * NO BITCHES ?
 */

public class MainTest {

	public static void main(String[] args) {
		AppStorage storage = new FileStorage();
		MaterialController materials = new MaterialController();
		ReservationController reservations = new ReservationController();
		UserController users = new UserController();
		AppController controller = new AppController(storage, materials, reservations, users);
		controller.startApplication();
		
		Runtime.getRuntime().addShutdownHook(new Thread(controller::endApplication, "shutdown-hook"));
		
		WebServer server = new WebServer(controller);
		server.open();
	}
	
}
