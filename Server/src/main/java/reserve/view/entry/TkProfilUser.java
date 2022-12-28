package reserve.view.entry;

import java.io.IOException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.misc.Href;
import org.takes.rq.RqForm;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormBase;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;

import reserve.controller.AppController;
import reserve.controller.UserController;
import reserve.controller.io.JsonSerializer;
import reserve.model.Material;
import reserve.model.Reservation;
import reserve.model.User;
import reserve.view.WebServer;
import reserve.view.front.TkSpecificResource;

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
