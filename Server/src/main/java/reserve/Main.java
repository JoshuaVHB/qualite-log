package reserve;

import reserve.controller.AppController;
import reserve.controller.io.AppStorage;
import reserve.controller.io.FileStorage;
import reserve.util.AnsiLogger;
import reserve.util.Logger;
import reserve.view.WebServer;

public class Main {
	
	public static void main(String[] args) {
		WebServer server = new WebServer();
		AppStorage storage = new FileStorage();
		AppController controler = new AppController(storage);
		server.open(controler);
	}
	
	/** "Inline factory" for loggers */
	public static Logger getLogger(String name, int logLevel) {
		return new AnsiLogger(name, logLevel);
	}
	
}
