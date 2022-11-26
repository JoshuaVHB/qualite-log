package reserve;

import reserve.controller.AppController;
import reserve.controller.io.AppStorage;
import reserve.controller.io.InMemoryStorage;
import reserve.view.WebServer;

public class MainTest {

	public static void main(String[] args) {
		WebServer server = new WebServer();
		AppStorage storage = new InMemoryStorage();
		AppController controler = new AppController(storage);
		server.open(controler);
	}
	
}
