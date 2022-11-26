package reserve;

import java.io.IOException;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.tk.TkClasspath;

public class WebServer {
	
	private static final int PORT = 8080;

	public static void main(String[] args) {
		try {
			new FtBasic(new TkFork(
				new FkRegex("/.*", new TkClasspath())
			), PORT).start(Exit.NEVER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
