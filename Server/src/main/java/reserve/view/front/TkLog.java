package reserve.view.front;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.rq.RqHref;

import reserve.util.Logger;

public class TkLog implements Take {
	
	private final Logger logger;
	private final Take actor;
	
	public TkLog(Logger logger, Take actor) {
		this.logger = logger;
		this.actor = actor;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		String url = new RqHref.Base(req).href().path();
		Identity identity = new RqAuth(req).identity();
		logger.debug("req static=" + url + " identified=" + (identity!=Identity.ANONYMOUS));
		return actor.act(req);
	}
	
}
