package reserve.controller.io;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import reserve.controller.MaterialController;
import reserve.controller.ReservationController;
import reserve.controller.UserController;
import reserve.model.Material;
import reserve.model.User;

public class FileStorage implements AppStorage {

	@Override
	public void load() {
		JSONParser parser = new JSONParser();
		try (FileReader userFile = new FileReader("users.json");
			FileReader reservFile = new FileReader("reservations.json");
			FileReader materialFile = new FileReader("material.json")){
			
			JSONArray users = (JSONArray) parser.parse(userFile);
			JSONArray reservations = (JSONArray) parser.parse(reservFile);
			JSONArray material = (JSONArray) parser.parse(materialFile);
			
			users.forEach(user -> parseUsers((JSONObject)  user));
			reservations.forEach(res -> parseUsers((JSONObject)  res));
			
			
			
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return;
		
	}
	
	private static void parseUsers(JSONObject user) {
		JSONObject userObject = (JSONObject) user.get("user");
		
		String id = (String) userObject.get("id");
		String name = (String) userObject.get("name");
		String phone = (String) userObject.get("phone");
		String email = (String) userObject.get("email");
		
		UserController.createUser(name, phone, id, email);
	}
	
	private static void parseMaterial(JSONObject user) {
		JSONObject userObject = (JSONObject) user.get("user");
		
		String strOS = (String) userObject.get("os");
		String strType = (String) userObject.get("type");
		String name = (String) userObject.get("name");
		String version = (String) userObject.get("version");
		String strNumRef = (String) userObject.get("numref");
		
		
	}
	
	
	private static void parseReservations(JSONObject reservation) {
		JSONObject reservationObject = (JSONObject) reservation.get("user");
		
		String ownerId = (String) reservationObject.get("owner");
		String nameMaterial = (String) reservationObject.get("materialname");
		Integer refMaterial = (Integer) reservationObject.get("materialref");
		String strFrom = (String) reservationObject.get("phone");
		String strTo = (String) reservationObject.get("email");
		
		User owner = UserController.getById(ownerId);
		Material owned = MaterialController.getByNameAndRef(nameMaterial, refMaterial);
		LocalDate from = LocalDate.parse(strFrom);
		LocalDate to = LocalDate.parse(strTo);
		
		ReservationController.makeReservation(owner, owned, from, to);
	}
	
	
	@Override
	public void write() {
		throw new IllegalAccessError("not implemented");
	}
	
	
}
