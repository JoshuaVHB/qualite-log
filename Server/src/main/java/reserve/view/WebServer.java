package reserve.view;

import java.io.IOException;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.Fork;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkRedirect;

import reserve.controller.AppController;
import reserve.util.AnsiLogger;
import reserve.util.Logger;
import reserve.view.front.TkLog;
import reserve.view.front.TkSpecificResource;

public class WebServer {
	
	private static final int PORT = 8080;
	
	private static final Logger logger = getLogger("web-server", Logger.LEVEL_INFO);

	/**
	 * Opens the web server, this method does not return.
	 * Once opened users can access the web interface.
	 */
	public void open(AppController controller) {
		try {
			logger.info("Opening server on localhost:"+PORT);
			new FtBasic(new TkLog(getLogger("route", Logger.LEVEL_DEBUG),
				new TkFork(
					new FkRegex("/media/.*", new TkClasspath()), // media files (images...)
					indexPage("/connexion"),                     // connection page
					indexPage("/")                               // main page (index)
			)), PORT).start(Exit.NEVER);
		} catch (IOException e) {
			logger.merr(e, "Could not open server");
		}
	}
	
	// TODO change to a factory
	private static Logger getLogger(String name, int logLevel) {
		return new AnsiLogger(name, logLevel);
	}
	
	private static Fork indexPage(String staticUrlPath) {
		// match all paths that begin with the static part
		return new FkRegex(staticUrlPath+".*",
			// log requests to this path
			new TkLog(getLogger("path-"+staticUrlPath, Logger.LEVEL_DEBUG), new TkFork(
				// redirect '/connexion' to '/connexion/'
				new FkRegex(staticUrlPath, new TkRedirect(staticUrlPath+"/")).setRemoveTrailingSlash(false),
				// upon '/connexion', send '/connexion/index.html'
				new FkRegex(staticUrlPath+"/", new TkSpecificResource(staticUrlPath + "/index.html")).setRemoveTrailingSlash(false),
				// send any other file in the directory directly
				new FkRegex(staticUrlPath+"/[^/]*", new TkClasspath()))));
	}
	
}
