package reserve.view.front;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.rs.RsWithBody;

import reserve.Main;
import reserve.util.Logger;
import reserve.view.WebServer;

public class TkSpecificResource implements Take {
	
	private static final boolean USE_CACHE = false;
	private static final Pattern SSR_CONDITION_PATTERN = Pattern.compile("\\{\\{(.*?)\\}\\}"); // server-side rendering condition: {{condition}}
	
	private static final Logger logger = Main.LOGGER_FACTORY.getLogger("web-files");
	
	private final String path;
	private final Map<String, String> resources = new HashMap<>();
	
	public TkSpecificResource(String path) {
		this.path = path;
	}
	
	public Response act(Request req) throws Exception {
		
		/*
		 * Java really isn't designed for dynamic web page composition,
		 * this is the best I could do to emulate server-side rendering.
		 * Next time I'll use php or js, java is good as a back-end
		 * language but not practical enough.
		 */
		StringBuilder sb = new StringBuilder(loadFile(path));
		int searchIndex = sb.length();
		while((searchIndex = sb.lastIndexOf("<!--", searchIndex-1)) != -1) {
			int newLine = sb.indexOf("\n", searchIndex);
			String condPart = sb.substring(searchIndex+4, newLine).strip();
			Matcher m = SSR_CONDITION_PATTERN.matcher(condPart);
			int commentEnd = sb.indexOf("-->", searchIndex);
			if(!m.matches())
				continue;
			String condition = m.group(1);
			if(isConditionRespected(condition, req)) {
//				String body = sb.substring(searchIndex+4+m.end(0), commentEnd);
//				sb.replace(searchIndex, commentEnd+3, body);
				sb.delete(commentEnd, commentEnd+3);            // remove "-->"
				sb.delete(searchIndex, searchIndex+4+m.end(0)); // remove "<!--{{...}}"
			} else {
				sb.delete(searchIndex, commentEnd+3); // remove the whole block
			}
		}
		
		return new RsWithBody(sb.toString());
	}
	
	private boolean isConditionRespected(String condition, Request request) throws IOException {
		Identity identity = new RqAuth(request).identity();
		boolean authentified = identity != Identity.ANONYMOUS;
		
		switch(condition) {
		case "LOGGED":     return authentified;
		case "NOT_LOGGED": return !authentified;
		default: logger.warn("Unknown condition '" + condition + "'"); return false;
		}
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
