package reserve.view.entry;

import org.takes.Request;
import org.takes.Response;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.Pass;
import org.takes.misc.Href;
import org.takes.misc.Opt;
import org.takes.rq.RqHref;

public class PsLogout implements Pass {

	@Override
	public Opt<Identity> enter(Request request) throws Exception {
		Href href = new RqHref.Base(request).href();
		
		if(FormUtils.hasParam(href, "logout"))
			return new Opt.Single<>(Identity.ANONYMOUS);
		
		return new Opt.Empty<>();
	}
	
	@Override
	public Response exit(Response response, Identity identity) throws Exception {
		return response;
	}
}
