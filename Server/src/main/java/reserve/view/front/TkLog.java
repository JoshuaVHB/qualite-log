package reserve.view.front;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref;

import reserve.model.User;
import reserve.util.Logger;
import reserve.view.entry.FormUtils;

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
		User user = FormUtils.getUserIdentity(req);
		logger.debug(String.format("req static=%s [%s%s]",
				url,
				(user!=null ? "identified " : ""),
				(user!=null&&user.isAdmin() ? "admin " : "")));
		return actor.act(req);
	}
	
}
