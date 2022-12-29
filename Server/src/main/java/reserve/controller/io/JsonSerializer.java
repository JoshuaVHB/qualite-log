package reserve.controller.io;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import reserve.controller.MaterialController;
import reserve.controller.UserController;
import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;
import reserve.model.Reservation;
import reserve.model.User;

@SuppressWarnings("unchecked")
public class JsonSerializer {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
	
	private static <T> JSONArray serializeList(List<T> list, Function<T, JSONObject> serializer) {
		JSONArray jsonMaterials = new JSONArray();
		for(T obj : list)
			jsonMaterials.add(serializer.apply(obj));
		return jsonMaterials;
	}
	
	public static JSONArray serializeMaterialList(List<Material> materials, boolean includeReservations) {
		return serializeList(materials, m -> serializeMaterial(m, includeReservations));
	}
	
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
			jsonReservation.put("userName", reservation.getOwner().getFirstName() + " " + reservation.getOwner().getLastName());
			jsonReservation.put("userId", reservation.getOwner().getId());
			jsonReservation.put("upToDate", reservation.getEnding().format(DATE_FORMATTER));
			jsonReservation.put("fromDate", reservation.getBeginning().format(DATE_FORMATTER));
			json.put("reservation", jsonReservation);
		}
		return json;
	}

	public static JSONArray serializeUserList(List<User> users) {
		return serializeList(users, JsonSerializer::serializeUser);
	}
	
	public static JSONObject serializeUser(User user) {
		JSONObject json = new JSONObject();
		String isAdmin = "false";
		if(user.isAdmin()) isAdmin = "true";
		json.put("isAdmin", isAdmin);
		json.put("id",         user.getId().toString());
		json.put("first-name", user.getFirstName());
		json.put("last-name",  user.getLastName());
		json.put("email",      user.getEmail());
		json.put("phone",      user.getPhone());
		json.put("password",   user.getPassword());
		return json;
	}

	public static JSONArray serializeReservationList(List<Reservation> reservations) {
		return serializeList(reservations, JsonSerializer::serializeReservation);
	}
	
	public static JSONObject serializeReservation(Reservation reservation) {
		JSONObject json = new JSONObject();
		json.put("to",         reservation.getEnding().toString());
		json.put("from",       reservation.getBeginning().toString());
		json.put("owner",      reservation.getOwner().getId().toString());
		json.put("materialID", reservation.getMaterial().getId().toString());
		return json;
	}

	public static User parseUser(JSONObject user) {
		// get fields form the JSON parser
		boolean isAdmin = "true".equals(user.get("isAdmin"));
		String id = (String) user.get("id");
		String firstName = (String) user.get("first-name");
		String lastName = (String) user.get("last-name");
		String phone = (String) user.get("phone");
		String email = (String) user.get("email");
		String password = (String) user.get("password");
		
		return new User(isAdmin, firstName, lastName, phone, id, email, password);
	}

	public static Material parseMaterial(JSONObject material) {
		// get fields form the JSON parser
		String strOS = (String) material.get("os");
		String strType = (String) material.get("type");
		String name = (String) material.get("name");
		String version = (String) material.get("version");
		int numRef = (int)(long)(Long) material.get("numref");
		String uuidStr = (String) material.get("id");
		
		// convert strings from JSON into the right type
		OperatingSystem os = OperatingSystem.valueOf(strOS);
		MaterialType type = MaterialType.valueOf(strType);
		UUID id = UUID.fromString(uuidStr);
		
		return new Material(os, type, name, version, numRef, id);
	}

	public static Reservation parseReservations(JSONObject reservation, UserController users, MaterialController materials) {
		// get fields from the JSON parser
		String ownerId = (String) reservation.get("owner");
		String materialID = (String) reservation.get("materialID"); 
		String strFrom = (String) reservation.get("from");
		String strTo = (String) reservation.get("to");
		
		// convert strings from JSON into the right type
		User owner = users.getById(ownerId);
		Material owned = materials.getMaterialById(UUID.fromString(materialID));
		LocalDate from = LocalDate.parse(strFrom);
		LocalDate to = LocalDate.parse(strTo);
	
		Objects.requireNonNull(owner, "No user account with id " + ownerId);
		Objects.requireNonNull(owned, "No material with id " + materialID);
		
		return new Reservation(owner, owned, from, to);
	}
	
}
