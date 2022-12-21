package reserve.view.front;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.rs.RsWithBody;

import reserve.view.WebServer;

public class TkSpecificResource implements Take {
	
	private static final boolean USE_CACHE = false;
	
	private final String path;
	private final Map<String, String> resources = new HashMap<>();
	
	public TkSpecificResource(String path) {
		this.path = path;
	}
	
	public Response act(Request req) throws Exception {
		Identity identity = new RqAuth(req).identity();
		boolean authentified = identity != Identity.ANONYMOUS;
		
		String text = loadFile(path);
		text = text.replaceAll("\\$\\$LOGGED\\$\\$", authentified ? loadFile("/parts/_logged.html") : loadFile("/parts/_not_logged.html"));
		return new RsWithBody(text);
	}
	
	private String loadFile(String path) {
		return USE_CACHE ?
				resources.computeIfAbsent(path, TkSpecificResource::readFileFromResources) :
				readFileFromResources(path);
	}
	
	private static String readFileFromResources(String path) {
		try (InputStream is = TkSpecificResource.class.getResourceAsStream(path)) {
			return new String(is.readAllBytes(), Charset.forName("utf-8"));
		} catch (IOException | NullPointerException e) {
			WebServer.logger.merr(e, "Could not load resource '" + path + "'");
			return "";
		}
	}
	
}
