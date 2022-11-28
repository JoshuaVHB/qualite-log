package reserve.controller.io;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import reserve.controller.MaterialController;
import reserve.controller.ReservationController;
import reserve.controller.UserController;
import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;
import reserve.model.User;

public class FileStorage implements AppStorage {
	

	
	
	/**
	 * @brief read JSON files and fill Reservation, User and Material lists
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@Override
	public void load() {
		JSONParser parser = new JSONParser();
		try (FileReader userFile = new FileReader("users.json");			//opening files
			FileReader reservFile = new FileReader("reservations.json");
			FileReader materialFile = new FileReader("material.json")){
			
			JSONArray users = (JSONArray) parser.parse(userFile);
			JSONArray reservations = (JSONArray) parser.parse(reservFile);		//parsing base
			JSONArray material = (JSONArray) parser.parse(materialFile);
			
			users.forEach(user -> parseUsers((JSONObject)  user));
			reservations.forEach(res -> parseUsers((JSONObject)  res));		//call the parse functions 
			material.forEach(mat -> parseUsers((JSONObject)  mat));
			
			
			
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {		//catch exceptions
			e.printStackTrace();
		}
		return;
		
	}
	
	private static void parseUsers(JSONObject user) {
		JSONObject userObject = (JSONObject) user.get("user");
		
		String id = (String) userObject.get("id");
		String name = (String) userObject.get("name");
		String phone = (String) userObject.get("phone");		//get fields form the JSON parser
		String email = (String) userObject.get("email");
		
		UserController.createUser(name, phone, id, email);		//generate a user object in the memory
	}
	
	private static void parseMaterial(JSONObject user) {
		JSONObject userObject = (JSONObject) user.get("user");
		
		String strOS = (String) userObject.get("os");
		String strType = (String) userObject.get("type");		//get fields form the JSON parser
		String name = (String) userObject.get("name");
		String version = (String) userObject.get("version");
		String strNumRef = (String) userObject.get("numref");
		
		OperatingSystem os = OperatingSystem.valueOf(strOS);
		MaterialType type = MaterialType.valueOf(strType);		//convert strings from JSON into the right type
		Integer numRef = Integer.decode(strNumRef);
		
		MaterialController.addMaterial(os, type, name, version, numRef);		//generate a user object in the memory
		
	}
	
	
	private static void parseReservations(JSONObject reservation) {
		JSONObject reservationObject = (JSONObject) reservation.get("user");
		
		String ownerId = (String) reservationObject.get("owner");
		String nameMaterial = (String) reservationObject.get("materialname");
		Integer refMaterial = (Integer) reservationObject.get("materialref");		//get fields form the JSON parser
		String strFrom = (String) reservationObject.get("phone");
		String strTo = (String) reservationObject.get("email");
		
		User owner = UserController.getById(ownerId);
		Material owned = MaterialController.getByNameAndRef(nameMaterial, refMaterial);	   //convert strings from JSON into the right type
		LocalDate from = LocalDate.parse(strFrom);
		LocalDate to = LocalDate.parse(strTo);
		
		ReservationController.makeReservation(owner, owned, from, to);		//generate a user object in the memory
	}
	
	
	@Override
	public void write() {
		
	}
	
	
}
