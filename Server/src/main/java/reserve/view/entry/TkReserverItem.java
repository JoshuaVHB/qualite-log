package reserve.view.entry;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;
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

public class TkReserverItem implements Take {
	
	private final AppController application;
	
	public TkReserverItem(AppController application) {
		this.application = application;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response act(Request req) throws Exception {
		RqForm form = new RqFormBase(req);
		User user = FormUtils.getUserIdentity(req);
		if(user == null)
			return new RsWithStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
		List<Material> materials = getParamMaterialList(form, "items");
		LocalDate fromDate = FormUtils.getParamDate(form, "from", false);
		LocalDate toDate = FormUtils.getParamDate(form, "to", false);
		
		JSONObject response = new JSONObject();
		
		for(Material material : materials) {
			Reservation reservation = new Reservation(user, material, fromDate, toDate);
			try {
				application.getReservations().addReservation(reservation);
				response.put(material.getId().toString(), "ok");
			} catch (IllegalStateException e) {
				response.put(material.getId().toString(), e.getMessage());
			}
		}
		
		return new RsText(response.toJSONString());
	}

	private List<Material> getParamMaterialList(RqForm href, String param) throws IllegalArgumentException, IOException {
		List<Material> materials = new ArrayList<>();
		String paramValue = FormUtils.getParamString(href, param, null, false);
		for(String mId : paramValue.split(",")) {
			UUID uuid = UUID.fromString(mId);
			Material m = application.getMaterials().getMaterialById(uuid);
			if(m == null)
				throw new IllegalArgumentException("No material with id " + mId);
			materials.add(m);
		}
		return materials;
	}
	
}
