package reserve.view.entry;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormBase;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;

import reserve.controller.UserController;
import reserve.model.User;

public class TkUserManager implements Take {

	// TODO test all this
	
	private final UserController users;
	
	public TkUserManager(UserController controller) {
		this.users = controller;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		User user = FormUtils.getUserIdentity(req);
		RqForm form = new RqFormBase(req);
		
		if(user == null)
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		CRUDAction action = FormUtils.getParamEnum(form, "action", CRUDAction.class, false);
		switch(action) {
		case CREATE: return createUser(user, form);
		case DELETE: return deleteUser(user, form);
		case UPDATE: return updateUser(user, form);
		case READ: return new RsWithStatus(new RsText("No read action on users"), HttpURLConnection.HTTP_BAD_GATEWAY);
		default: throw new IllegalStateException("unreachable");
		}
	}

	private Response createUser(User requestSender, RqForm form) throws IllegalArgumentException, IOException {
		if(!requestSender.isAdmin())
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		boolean isAdmin = FormUtils.getParamBool(form, "isadmin", false);
		String name = FormUtils.getParamString(form, "name", ".*", false); // TODO add regex to match specs
		String phone = FormUtils.getParamString(form, "phone", ".*", false); // TODO add regex to match specs
		String mail = FormUtils.getParamString(form, "mail", ".*", false); // TODO add regex to match specs
		String id = "ID"; // TODO what is id supposed to be exactly ?
		// if it is a unique id it should be a "final UUID" instead of a "String"
		
		User user = new User(isAdmin, name, phone, id, mail);
		try {
			users.addUser(user);
			return new RsWithStatus(HttpURLConnection.HTTP_OK);
		} catch (IllegalArgumentException e) {
			return new RsWithStatus(new RsText(e.getMessage()), HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
	
	private Response deleteUser(User requestSender, RqForm form) throws IllegalArgumentException, IOException {
		if(!requestSender.isAdmin())
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		String userId = FormUtils.getParamString(form, "id", ".*", false); // TODO add regex to match specs
		User user = users.getById(userId);
		if(user == null)
			return new RsWithStatus(new RsText("This user does not exist"), HttpURLConnection.HTTP_BAD_REQUEST);
		if(!users.removeUser(user)) // TODO FIX removing users currently *does not* removes associated reservations, this is a bug
			return new RsWithStatus(new RsText("Could not remove user"), HttpURLConnection.HTTP_INTERNAL_ERROR);
		return new RsWithStatus(HttpURLConnection.HTTP_OK);
	}
	
	private Response updateUser(User user, RqForm form) throws IllegalArgumentException, IOException {
		// TODO list modifiable fields in User and implement #updateUser
		return new RsWithStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
	}
	
}
