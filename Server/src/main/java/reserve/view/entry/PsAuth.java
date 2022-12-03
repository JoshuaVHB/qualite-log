package reserve.view.entry;

import java.util.HashMap;
import java.util.Map;

import org.takes.Request;
import org.takes.Response;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.Pass;
import org.takes.misc.Opt;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormBase;

import reserve.Main;
import reserve.controller.UserController;
import reserve.model.User;
import reserve.util.Logger;

public class PsAuth implements Pass {
	
	private static final Logger logger = Main.LOGGER_FACTORY.getLogger("auth", Logger.LEVEL_INFO);
	
	private final UserController users;
	
	public PsAuth(UserController users) {
		this.users = users;
	}
	
	@Override
	public Opt<Identity> enter(Request request) throws Exception {
		RqForm form = new RqFormBase(request); // using POST form
		
		String userName = FormUtils.getParamString(form, "username", UserController.USER_NAME_FORMAT, true);
		String password = FormUtils.getParamString(form, "password", UserController.PASSWORD_FORMAT, true);
		
		if(FormUtils.hasParam(form, "logout"))
			return new Opt.Single<>(Identity.ANONYMOUS);
		
		if(userName == null || password == null)
			return new Opt.Empty<>();
		
		User authentifiedUser = users.authentifyUser(userName, password);
		logger.info("Trying to authentify " + userName + ", " + (authentifiedUser==null ? "invalid crendentials" : "success"));
		
		if(authentifiedUser == null)
			return new Opt.Empty<>();
		
		Map<String, String> props = new HashMap<>();
		props.put(FormUtils.IDENTITY_PROP_USER_ID_KEY, authentifiedUser.getId());
		return new Opt.Single<>(new Identity.Simple(String.format("urn:%s:%s", "auth", authentifiedUser.getId()), props));
	}
	
	@Override
	public Response exit(Response response, Identity identity) throws Exception {
		return response;
	}
	
}
