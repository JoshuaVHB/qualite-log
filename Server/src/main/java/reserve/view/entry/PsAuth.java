package reserve.view.entry;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.takes.HttpException;
import org.takes.Request;
import org.takes.Response;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.Pass;
import org.takes.misc.Href;
import org.takes.misc.Opt;
import org.takes.rq.RqHref;

import reserve.Main;
import reserve.controller.UserController;
import reserve.model.User;
import reserve.util.Logger;

public class PsAuth implements Pass {
	
	private static final Logger logger = Main.getLogger("auth", Logger.LEVEL_INFO);
	
	@Override
	public Opt<Identity> enter(Request request) throws Exception {
		Href href = new RqHref.Base(request).href();
		logger.info("Auth request");
		
		String userName = FormUtils.getParamString(href, "username", UserController.USER_NAME_FORMAT, true);
		String password = FormUtils.getParamString(href, "password", UserController.PASSWORD_FORMAT, true);
		
		if(FormUtils.hasParam(href, "logout")) {
			logger.info("logout");
			return new Opt.Single<>(Identity.ANONYMOUS);
		}
		
		if(userName == null || password == null)
			return new Opt.Empty<>();
		
		logger.info("Trying to authentify " + userName+":"+password);
		
		User authentifiedUser = UserController.authentifyUser(userName, password);
		if(authentifiedUser != null) {
			Map<String, String> props = new HashMap<>();
			props.put(FormUtils.IDENTITY_PROP_USER_ID_KEY, authentifiedUser.getId());
			logger.info("authentified " + authentifiedUser.getId());
			return new Opt.Single<>(new Identity.Simple(String.format("urn:%s:%s", "auth", authentifiedUser.getId()), props));
		}
		
		logger.info("Invalid credentials");
		throw new HttpException(HttpURLConnection.HTTP_FORBIDDEN, "Invalid credentials");
	}
	
	@Override
	public Response exit(Response response, Identity identity) throws Exception {
		return response;
	}
	
}
