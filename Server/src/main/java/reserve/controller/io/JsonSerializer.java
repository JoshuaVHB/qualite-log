package reserve.controller.io;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import reserve.model.Material;
import reserve.model.Reservation;

public class JsonSerializer {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
	
	@SuppressWarnings("unchecked")
	public static JSONArray serializeMaterialList(List<Material> materials, boolean includeReservations) {
		JSONArray jsonMaterials = new JSONArray();
		for(Material m : materials)
			jsonMaterials.add(serializeMaterial(m, includeReservations));
		return jsonMaterials;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject serializeMaterial(Material material, boolean includeReservation) {
		JSONObject json = new JSONObject();
		json.put("id",      material.getId().toString());
		json.put("os",      material.getOs().name());
		json.put("name",    material.getName());
		json.put("type",    material.getType().name());
		json.put("numref",  material.getNumRef());
		json.put("version", material.getVersion());
		Reservation reservation = material.getReservation();
		if(includeReservation && reservation != null) {
			JSONObject jsonReservation = new JSONObject();
			jsonReservation.put("userName", reservation.getOwner().getName());
			jsonReservation.put("upToDate", reservation.getEnding().format(DATE_FORMATTER));
			json.put("reservation", jsonReservation);
		}
		return json;
	}
	
}
