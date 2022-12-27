package reserve.view.entry;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormBase;
import org.takes.rs.RsText;

import reserve.controller.MaterialController;
import reserve.controller.UserController;
import reserve.controller.io.JsonSerializer;
import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;
import reserve.model.User;

public class TkListUsers implements Take {
	
	private final UserController users;
	
	public TkListUsers(UserController users) {
		this.users = users;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Response act(Request req) throws Exception {
		RqForm href = new RqFormBase(req); // using GET request
		
		
		List<User> res = users.getAllUsers();
		System.out.println(res);
		
		JSONArray serializedUsers = JsonSerializer.serializeUserList(res);
		
		JSONObject response = new JSONObject();
		response.put("items", serializedUsers);
		return new RsText(response.toJSONString());
	}
	
}
