package reserve.view.front;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import org.takes.rs.RsRedirect;

public class TkRedirectDir implements Take {

	@Override
	public Response act(Request req) throws Exception {
		Href href = new RqHref.Base(req).href();
		return new RsRedirect(href.path(""));
	}

}
