package reserve.view.entry;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsText;

import reserve.controller.UserController;
import reserve.controller.io.JsonSerializer;
import reserve.model.User;

public class TkListUsers implements Take {

	final UserController users;

	public TkListUsers(UserController users) {
		this.users = users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response act(Request req) throws Exception {
		List<User> res = users.getAllUsers();
		JSONArray serializedUsers = JsonSerializer.serializeUserList(res);

		JSONObject response = new JSONObject();
		response.put("items", serializedUsers);
		return new RsText(response.toJSONString());
	}

}
