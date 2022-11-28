package reserve.view.entry;

import java.net.HttpURLConnection;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.rs.RsHtml;
import org.takes.rs.RsWithStatus;

import reserve.controller.UserController;
import reserve.model.User;

public class TkReserverItem implements Take {

	@Override
	public Response act(Request req) throws Exception {
		Identity identity = new RqAuth(req).identity();
		if(identity == Identity.ANONYMOUS)
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		User user = UserController.getUserById(identity.properties().get(FormUtils.IDENTITY_PROP_USER_ID_KEY));
		
		return new RsHtml(user.toString());
	}
	
}
