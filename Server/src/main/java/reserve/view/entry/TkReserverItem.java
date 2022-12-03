package reserve.view.entry;

import java.net.HttpURLConnection;
import java.time.LocalDate;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import org.takes.rs.RsWithStatus;

import reserve.controller.AppController;
import reserve.model.Material;
import reserve.model.Reservation;
import reserve.model.User;

public class TkReserverItem implements Take {
	
	private final AppController application;
	
	public TkReserverItem(AppController application) {
		this.application = application;
	}

	@Override
	public Response act(Request req) throws Exception {
		Identity identity = new RqAuth(req).identity();
		Href href = new RqHref.Base(req).href();
		if(identity == Identity.ANONYMOUS)
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		
		User user = application.getUsers().getById(identity.properties().get(FormUtils.IDENTITY_PROP_USER_ID_KEY));
		Material material = null; /* MaterialController.getByNameAndRef(null, null) */ // TODO retrieve the material by ID
		LocalDate fromDate = FormUtils.getParamDate(href, "from-date", false);
		LocalDate toDate = FormUtils.getParamDate(href, "to-date", false);
		
		application.getReservations().addReservation(new Reservation(user, material, fromDate, toDate)); // TODO catch errors and send the message as a response
		return new RsWithStatus(HttpURLConnection.HTTP_ACCEPTED);
	}
	
}
