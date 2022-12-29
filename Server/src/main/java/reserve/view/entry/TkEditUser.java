package reserve.view.entry;

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

public class TkEditUser implements Take {

	private final UserController users;
	
	public TkEditUser(UserController controller) {
		this.users = controller;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		User editorUser = FormUtils.getUserIdentity(req);
		RqForm form = new RqFormBase(req);

		if(editorUser == null || !editorUser.isAdmin())
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		String editedUserId = FormUtils.getParamString(form, "edited-user", UserController.USER_ID_FORMAT, true);
		
		if(FormUtils.getParamBool(form, "delete", true) != null) {
			// remove user
			User user = users.getById(editedUserId);
			if(user == null)
				return new RsWithStatus(new RsText("Unknown user"), HttpURLConnection.HTTP_BAD_REQUEST);
			users.removeUser(user);
			return new RsWithStatus(HttpURLConnection.HTTP_OK);
		}
		
		boolean isAdmin = FormUtils.getParamBool(form, "isadmin", false);
		String name = FormUtils.getParamString(form, "name", ".*", false); // TODO add regex to match specs
		String phone = FormUtils.getParamString(form, "phone", ".*", false); // TODO add regex to match specs
		String mail = FormUtils.getParamString(form, "mail", ".*", false); // TODO add regex to match specs
		String password = FormUtils.getParamString(form, "password", ".*", false);
		
		if(editedUserId == null) {
			// create a new user
			String newId = users.getNextUserId();
			User user = new User(isAdmin, name, phone, editedUserId, mail, password);
			try {
				users.addUser(user);
				return new RsText(newId);
			} catch (IllegalArgumentException e) {
				return new RsWithStatus(new RsText(e.getMessage()), HttpURLConnection.HTTP_BAD_REQUEST);
			}
		} else {
			// edit the user
			User user = users.getById(editedUserId);
			if(!editedUserId.equals(editorUser.getId()) && !isAdmin)
				return new RsWithStatus(new RsText("You cannot disown yourself of admin privileges"), HttpURLConnection.HTTP_BAD_REQUEST);
			user.setEmail(mail);
			user.setName(name);
			user.setPhone(phone);
			user.setAdmin(isAdmin);
			return new RsWithStatus(HttpURLConnection.HTTP_OK);
		}
	}

}
