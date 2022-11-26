package reserve.view.front;

import java.io.IOException;
import java.io.InputStream;

import org.takes.HttpException;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsWithBody;

public class TkSpecificResource implements Take {
	
	private final String path;
	
	public TkSpecificResource(String path) {
		this.path = path;
	}
	
	public Response act(Request req) throws Exception {
		try (InputStream is = TkSpecificResource.class.getResourceAsStream(path)) {
			return new RsWithBody(is.readAllBytes());
		} catch (IOException | NullPointerException e) {
			throw new HttpException(500, "Cannot serve resource " + path + ": " + e.getMessage());
		}
	}
	
}
