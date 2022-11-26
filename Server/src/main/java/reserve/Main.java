package reserve;

import reserve.controller.AppController;
import reserve.controller.io.AppStorage;
import reserve.controller.io.FileStorage;
import reserve.view.WebServer;

public class Main {
	
	public Main() {
		WebServer server = new WebServer();
		AppStorage storage = new FileStorage();
		AppController controler = new AppController(storage);
		server.open(controler);
	}
	
}
