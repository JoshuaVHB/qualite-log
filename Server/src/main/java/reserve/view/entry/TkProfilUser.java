package reserve.view.entry;

import org.json.simple.JSONObject;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsText;

import reserve.controller.UserController;
import reserve.controller.io.JsonSerializer;
import reserve.model.User;

public class TkProfilUser implements Take {
	
	final UserController users;

	public TkProfilUser(UserController users) {
		this.users = users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response act(Request req) throws Exception {
		User user = FormUtils.getUserIdentity(req);
		
		JSONObject serialiezUser = JsonSerializer.serializeUser(user);
		JSONObject response = new JSONObject();
		response.put("items", serialiezUser);
		return new RsText(response.toJSONString());
	}
	
}
