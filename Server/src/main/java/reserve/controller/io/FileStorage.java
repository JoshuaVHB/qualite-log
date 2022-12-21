package reserve.controller.io;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import reserve.controller.AppController;
import reserve.controller.MaterialController;
import reserve.controller.UserController;
import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;
import reserve.model.Reservation;
import reserve.model.User;

public class FileStorage implements AppStorage {
	
	/**
	 * FUTURE -- rewrite comments, the function signature has changed
	 *           also note that this method is allowed to throw IOExceptions now
	 * 
	 * @brief read JSON files and fill Reservation, User and Material lists
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void loadApplicationData(AppController application) throws IOException {
		JSONParser parser = new JSONParser();
		try (FileReader userFile = new FileReader("users.json");			//opening files
			FileReader reservFile = new FileReader("reservations.json");
			FileReader materialFile = new FileReader("material.json")){
			
			JSONArray users = (JSONArray) parser.parse(userFile);
			JSONArray reservations = (JSONArray) parser.parse(reservFile);		//parsing base
			JSONArray materials = (JSONArray) parser.parse(materialFile);
			
			for(JSONObject json : (List<JSONObject>) users)
				application.getUsers().addUser(parseUsers(json));
			for(JSONObject json : (List<JSONObject>) materials)
				application.getMaterials().addMaterial(parseMaterial(json));
			for(JSONObject json : (List<JSONObject>) reservations)
				application.getReservations().addReservation(parseReservations(json, application.getUsers(), application.getMaterials()));
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {		//catch exceptions
			e.printStackTrace();
		}
		return;
		
	}
	
	private static User parseUsers(JSONObject user) {
		JSONObject userObject = (JSONObject) user.get("user");
		
		String id = (String) userObject.get("id");
		String name = (String) userObject.get("name");
		String phone = (String) userObject.get("phone");		//get fields form the JSON parser
		String email = (String) userObject.get("email");
		
		// TODO parse user.isAdmin
		return new User(false, name, phone, id, email);		//generate a user object in the memory
	}
	
	private static Material parseMaterial(JSONObject user) {
		JSONObject userObject = (JSONObject) user.get("user");
		
		String strOS = (String) userObject.get("os");
		String strType = (String) userObject.get("type");		//get fields form the JSON parser
		String name = (String) userObject.get("name");
		String version = (String) userObject.get("version");
		String strNumRef = (String) userObject.get("numref");
		
		OperatingSystem os = OperatingSystem.valueOf(strOS);
		MaterialType type = MaterialType.valueOf(strType);		//convert strings from JSON into the right type
		Integer numRef = Integer.decode(strNumRef);
		
		return new Material(os, type, name, version, numRef);		//generate a user object in the memory
	}
	
	private static Reservation parseReservations(JSONObject reservation, UserController users, MaterialController materials) {
		JSONObject reservationObject = (JSONObject) reservation.get("user");
		
		String ownerId = (String) reservationObject.get("owner");
		String materialID = (String) reservationObject.get("UUID"); //get fields from the JSON parser
		String strFrom = (String) reservationObject.get("phone");
		String strTo = (String) reservationObject.get("email");
		
		User owner = users.getById(ownerId);
		Material owned = materials.getMaterialById(UUID.fromString(materialID));	   //convert strings from JSON into the right type
		LocalDate from = LocalDate.parse(strFrom);
		LocalDate to = LocalDate.parse(strTo);
		
		return new Reservation(owner, owned, from, to); //generate a user object in memory
	}
	
	
	@Override
	public void writeApplicationData(AppController application) {
		// TODO FileStorage#writeApplicationData
	}
	
	
}
