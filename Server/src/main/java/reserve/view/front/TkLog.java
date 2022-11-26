package reserve.view.front;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
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
//		logger.debug(String.join("\n", req.head()));
		logger.debug("req static=" + new RqHref.Base(req).href().path());
		return actor.act(req);
	}
	
}
