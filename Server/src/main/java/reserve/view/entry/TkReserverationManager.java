package reserve.view.entry;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.UUID;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormBase;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;

import reserve.controller.AppController;
import reserve.model.Material;
import reserve.model.Reservation;
import reserve.model.User;

public class TkReserverationManager implements Take {
	
	private final AppController application;
	
	public TkReserverationManager(AppController application) {
		this.application = application;
	}

	@Override
	public Response act(Request req) throws Exception {
		User user = FormUtils.getUserIdentity(req);
		RqForm form = new RqFormBase(req);
		
		if(user == null)
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		CRUDAction action = FormUtils.getParamEnum(form, "action", CRUDAction.class, false);
		switch(action) {
		case CREATE: return createReservation(user, form);
		case DELETE: return deleteReservation(user, form);
		case UPDATE: return new RsWithStatus(new RsText("No update action on reservations"), HttpURLConnection.HTTP_BAD_GATEWAY);
		case READ: return new RsWithStatus(new RsText("No read action on reservations"), HttpURLConnection.HTTP_BAD_GATEWAY);
		default: throw new IllegalStateException("unreachable");
		}
	}
	
	private Response createReservation(User requestSender, RqForm form) throws IllegalArgumentException, IOException {
		LocalDate beginning = FormUtils.getParamDate(form, "beginning", false);
		LocalDate ending = FormUtils.getParamDate(form, "ending", false);
		UUID materialId = FormUtils.getParamUUID(form, "material", false);
		Material material = application.getMaterials().getMaterialById(materialId);
		
		if(material == null)
			return new RsWithStatus(new RsText("No such material"), HttpURLConnection.HTTP_BAD_REQUEST);
		
		Reservation reservation = new Reservation(requestSender, material, beginning, ending);
		try {
			application.getReservations().addReservation(reservation);
			return new RsWithStatus(HttpURLConnection.HTTP_OK);
		} catch (IllegalArgumentException e) {
			return new RsWithStatus(new RsText(e.getMessage()), HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}
	
	@SuppressWarnings("unused")
	private Response deleteReservation(User requestSender, RqForm form) throws IllegalArgumentException, IOException {
		LocalDate beginning = FormUtils.getParamDate(form, "beginning", false);
		LocalDate ending = FormUtils.getParamDate(form, "ending", false);
		UUID materialId = FormUtils.getParamUUID(form, "material", false);
		Material material = application.getMaterials().getMaterialById(materialId);
		String userId = FormUtils.getParamString(form, "user", ".*", false);
		User user = application.getUsers().getById(userId);
		
		if(material == null)
			return new RsWithStatus(new RsText("No such material"), HttpURLConnection.HTTP_BAD_REQUEST);
		if(user == null)
			return new RsWithStatus(new RsText("No such user"), HttpURLConnection.HTTP_BAD_REQUEST);
		if(!user.equals(requestSender) && !requestSender.isAdmin())
			return new RsWithStatus(new RsText("Not permitted to delete a reservation of another client"), HttpURLConnection.HTTP_FORBIDDEN);
		
		// TODO FIX implement #deleteReservation
//		application.getReservations().
		throw new IllegalStateException("unimplemented");
	}
	
}
