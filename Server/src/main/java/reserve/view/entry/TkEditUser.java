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

		if(editorUser == null)
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		String editedUserId = FormUtils.getParamString(form, "id", UserController.USER_ID_FORMAT, true);
		
		if(FormUtils.getParamBool(form, "delete", true) != null) {
			// remove user
			User user = users.getById(editedUserId);
			if(!editorUser.isAdmin())
				return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
			if(user == null || editorUser.getId().equals(editedUserId))
				return new RsWithStatus(new RsText("Unknown user"), HttpURLConnection.HTTP_BAD_REQUEST);
			users.removeUser(user);
			return new RsWithStatus(HttpURLConnection.HTTP_OK);
		}
		
		boolean isAdmin = FormUtils.getParamBool(form, "is-admin", false);
		String firstName = FormUtils.getParamString(form, "first-name", UserController.USER_NAME_FORMAT, false);
		String lastName = FormUtils.getParamString(form, "last-name", UserController.USER_NAME_FORMAT, false);
//		String phone = FormUtils.getParamString(form, "phone", ".*", false);
		String phone = "-"; // not implemented on the front end
		String mail = FormUtils.getParamString(form, "mail", UserController.USER_MAIL_FORMAT, false);
		
		if(editedUserId == null) {
			if(!editorUser.isAdmin())
				return new RsWithStatus(new RsText("You must be an administrator"), HttpURLConnection.HTTP_UNAUTHORIZED);
				
			// create a new user
			String password = FormUtils.getParamString(form, "password", UserController.USER_PASSWORD_FORMAT, false);
			String newId = users.getNextUserId();
			User user = new User(isAdmin, firstName, lastName, phone, newId, mail, password);
			try {
				users.addUser(user);
				return new RsText(newId);
			} catch (IllegalArgumentException e) {
				return new RsWithStatus(new RsText(e.getMessage()), HttpURLConnection.HTTP_BAD_REQUEST);
			}
		} else {
			if((!editedUserId.equals(editorUser.getId()) && !editorUser.isAdmin()) ||
				(!editorUser.isAdmin() && isAdmin))
				return new RsWithStatus(new RsText("You must be an administrator"), HttpURLConnection.HTTP_UNAUTHORIZED);
			
			// edit the user
			User user = users.getById(editedUserId);
			if(editedUserId.equals(editorUser.getId()) && editorUser.isAdmin() && !isAdmin)
				return new RsWithStatus(new RsText("You cannot disown yourself of admin privileges"), HttpURLConnection.HTTP_BAD_REQUEST);
			user.setEmail(mail);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPhone(phone);
			user.setAdmin(isAdmin);
			UserController.logger.debug("Edited user " + user);
			return new RsWithStatus(HttpURLConnection.HTTP_OK);
		}
	}

}
