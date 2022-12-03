package reserve;

import reserve.controller.AppController;
import reserve.controller.io.AppStorage;
import reserve.controller.io.FileStorage;
import reserve.util.AnsiLogger;
import reserve.util.LoggerFactory;
import reserve.view.WebServer;

public class Main {
	
	public static LoggerFactory LOGGER_FACTORY = AnsiLogger::new;
	
	public static void main(String[] args) {
		WebServer server = new WebServer();
		AppStorage storage = new FileStorage();
		AppController controler = new AppController(storage);
		server.open(controler);
	}
	
}
