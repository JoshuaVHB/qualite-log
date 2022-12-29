package reserve.view;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.takes.facets.auth.PsChain;
import org.takes.facets.auth.PsCookie;
import org.takes.facets.auth.TkAuth;
import org.takes.facets.auth.codecs.CcCompact;
import org.takes.facets.auth.codecs.CcHex;
import org.takes.facets.auth.codecs.CcSafe;
import org.takes.facets.auth.codecs.CcXor;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.FbStatus;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.fork.FkAnonymous;
import org.takes.facets.fork.FkAuthenticated;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.Fork;
import org.takes.facets.fork.TkFork;
import org.takes.http.FtBasic;
import org.takes.rs.RsRedirect;
import org.takes.rs.RsText;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkRedirect;

import reserve.Main;
import reserve.controller.AppController;
import reserve.util.Logger;
import reserve.view.entry.FbError;
import reserve.view.entry.FormUtils;
import reserve.view.entry.PsAuth;
import reserve.view.entry.PsLogout;
import reserve.view.entry.TkEditItem;
import reserve.view.entry.TkEditUser;
import reserve.view.entry.TkEndReservation;
import reserve.view.entry.TkListItems;
import reserve.view.entry.TkListUsers;
import reserve.view.entry.TkProfilUser;
import reserve.view.entry.TkReserverItem;
import reserve.view.front.TkLog;
import reserve.view.front.TkRedirectDir;
import reserve.view.front.TkSpecificResource;

public class WebServer {
	
	private static final int PORT = 8080;
	private static final boolean REDIRECT_ON_404 = false;
	private static final boolean MASK_INTERNAL_ERRORS = false;
	
	public static final Logger logger = Main.LOGGER_FACTORY.getLogger("web-server", Logger.LEVEL_INFO);

	private final AppController application;
	
	public WebServer(AppController application) {
		this.application = application;
	}
	
	/**
	 * Opens the web server, this method does not return.
	 * Once opened users can access the web interface.
	 */
	public void open() {
		try {
			logger.info("Opening server on localhost:"+PORT);
			FormUtils.setIdentificationManager(application.getUsers());
			new FtBasic(
				new TkFallback(
					new TkAuth(
						new TkLog(Main.LOGGER_FACTORY.getLogger("route", Logger.LEVEL_DEBUG),
							new TkFork(
								new FkRegex("/media/.*", new TkClasspath()),
								new FkRegex("/api/list_users", new TkListUsers(application.getUsers())),
								new FkRegex("/api/list_items", new TkListItems(application.getMaterials())),
								new FkRegex("/api/reserve_items", new TkReserverItem(application)),
								new FkRegex("/api/end_reservation", new TkEndReservation(application.getReservations())),
								new FkRegex("/api/edit_item", new TkEditItem(application.getMaterials())),
								new FkRegex("/api/edit_user", new TkEditUser(application.getUsers())),
								new FkRegex("/connexion.*",
									new TkFork(
										new FkAuthenticated(new TkRedirect("/")),
										new FkAnonymous(new TkFork(indexPage("/connexion")))
									)
								),
								new FkRegex("/profil_item", new TkProfilUser(application.getUsers())),
								new FkRegex("/", new TkRedirect("/accueil")),
								indexPage("/connexion"),
								indexPage("/accueil"),
								indexPage("/creerMateriel"),
								indexPage("/inscription"),
								indexPage("/listeMateriel"),
								indexPage("/modifieMateriel"),
								indexPage("/profil"),
								indexPage("/profils"),
								indexPage("/materielReserve"),
								indexPage("/")
							)
						),
						new PsChain(
							new PsLogout(),
							new PsCookie(new CcSafe(new CcHex(new CcXor(new CcCompact(), "secret-code")))),
							new PsAuth(application.getUsers())
					    )
					),
					new FbChain(
						(MASK_INTERNAL_ERRORS ? new FbChain(
							FbError.withStatus(IllegalArgumentException.class, HttpURLConnection.HTTP_BAD_REQUEST),
							FbError.withStatus(IllegalStateException.class, HttpURLConnection.HTTP_BAD_REQUEST)
						) : new FbChain()),
						new FbStatus(404, REDIRECT_ON_404 ? new RsRedirect("/") : new RsText("404. No page to be seen here"))
					)
				), PORT).start(WebServer::shouldStop);
		} catch (IOException e) {
			logger.merr(e, "Could not open server");
		}
	}
	
	private static Fork indexPage(String staticUrlPath) {
		List<Fork> forks = new ArrayList<>();
		String dirRegex;
		
		if(!staticUrlPath.equals("/")) {
			// redirect '/connexion' to '/connexion/'
			forks.add(new FkRegex(staticUrlPath, new TkRedirectDir()).setRemoveTrailingSlash(false));
			// upon '/connexion', send '/connexion/index.html'
			forks.add(new FkRegex(staticUrlPath+"/", new TkSpecificResource(staticUrlPath + "/index.html")).setRemoveTrailingSlash(false));
			// send any other file in the directory directly
			forks.add(new FkRegex(staticUrlPath+"/\\w+\\.\\w+", new TkClasspath()));
			dirRegex = staticUrlPath+"(/\\w+\\.\\w+|)";
		} else {
			forks.add(new FkRegex("/", new TkSpecificResource("/index.html")));
			forks.add(new FkRegex("/\\w+\\.\\w+", new TkClasspath()));
			dirRegex = "/(\\w+\\.\\w+|)";
		}
		// match all paths that begin with the static part
		return new FkRegex(dirRegex,
			// log requests to this path
			new TkLog(Main.LOGGER_FACTORY.getLogger("path-"+staticUrlPath, Logger.LEVEL_DEBUG), new TkFork(forks.toArray(Fork[]::new))));
	}
	
	private static boolean shouldStop() {
		// stop the server when sending anything to the console
		try {
			return System.in.available() > 0;
		} catch (IOException e) {
			return false;
		}
	}
	
}
