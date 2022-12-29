package reserve.view.entry;

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

import reserve.controller.ReservationController;
import reserve.model.Reservation;
import reserve.model.User;

public class TkEndReservation implements Take {

	private final ReservationController reservations;
	
	public TkEndReservation(ReservationController reservations) {
		this.reservations = reservations;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		User user = FormUtils.getUserIdentity(req);
		RqForm form = new RqFormBase(req);

		if(user == null || !user.isAdmin())
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		UUID materialID = FormUtils.getParamUUID(form, "materialId", false);
		String userId = FormUtils.getParamString(form, "userId", null, false);
		LocalDate fromDate = FormUtils.getParamDate(form, "from-date", false);
		
		Reservation reservation = reservations.getReservation(materialID, userId, fromDate);
		if(reservation == null)
			return new RsWithStatus(new RsText("No matching reservation"), HttpURLConnection.HTTP_BAD_REQUEST);
		reservations.closeReservation(user, reservation);
		return new RsWithStatus(HttpURLConnection.HTTP_OK);
	}

}
