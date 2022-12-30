package reserve.view.front;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.takes.HttpException;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithType;

public class TkClassPathWithType implements Take {

	private final String prefix = "";
	
	@Override
	public Response act(Request request) throws Exception {
		final String name = String.format(
            "%s%s", prefix, new RqHref.Base(request).href().path()
        );
        final InputStream input = this.getClass()
            .getResourceAsStream(name);
        if (input == null) {
            throw new HttpException(
                HttpURLConnection.HTTP_NOT_FOUND,
                String.format("%s not found in classpath", name)
            );
        }
        return new RsWithType(new RsWithBody(input), getMimeType(name));
	}
	
	private static final String getMimeType(String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf('.')+1);
		switch(ext) {
		case "css": return "text/css";
		case "js": return "text/javascript";
		default: return "text/plain";
		}
	}

}
