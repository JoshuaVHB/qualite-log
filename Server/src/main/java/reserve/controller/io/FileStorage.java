package reserve.controller.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import reserve.controller.AppController;

public class FileStorage implements AppStorage {

	private static final File STORAGE_DIR = new File("storage");
	private static final File USERS_FILE = new File(STORAGE_DIR, "users.json");
	private static final File MATERIALS_FILE = new File(STORAGE_DIR, "material.json");
	private static final File RESERVATIONS_FILE = new File(STORAGE_DIR, "reservations.json");
	
	private static void ensureFilesExist() throws IOException {
		if(!STORAGE_DIR.exists())
			STORAGE_DIR.mkdir();
		for(File f : Arrays.asList(USERS_FILE, MATERIALS_FILE, RESERVATIONS_FILE)) {
			if(!f.exists())
				f.createNewFile();
		}
	}
	
	/**
	 * Read JSON files and fill Reservation, User and Material lists
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void loadApplicationData(AppController application) throws IOException {
		ensureFilesExist();
		JSONParser parser = new JSONParser();
		try (InputStream userFile = new FileInputStream(USERS_FILE);
			InputStream reservFile = new FileInputStream(RESERVATIONS_FILE);
			InputStream materialFile = new FileInputStream(MATERIALS_FILE)) {
			
			JSONArray users = readArray(userFile, parser);
			JSONArray reservations = readArray(reservFile, parser);
			JSONArray materials = readArray(materialFile, parser);
			
			for(JSONObject json : (List<JSONObject>) users)
				application.getUsers().addUser(JsonSerializer.parseUser(json));
			for(JSONObject json : (List<JSONObject>) materials)
				application.getMaterials().addMaterial(JsonSerializer.parseMaterial(json));
			for(JSONObject json : (List<JSONObject>) reservations)
				application.getReservations().addReservation(JsonSerializer.parseReservations(json, application.getUsers(), application.getMaterials()));
		} catch (NumberFormatException | NullPointerException e) {
			throw new IOException("Invalid storage files", e);
		} catch (ParseException e) {
			throw new IOException("Could not parse a json fragment", e);
		}
	}
	
	private static JSONArray readArray(InputStream is, JSONParser parser) throws IOException, ParseException {
		String fileContent = new String(is.readAllBytes());
		return fileContent.isBlank() ? new JSONArray() : (JSONArray) parser.parse(fileContent);
	}
	
	@Override
	public void writeApplicationData(AppController application) throws IOException {
		ensureFilesExist();
		try (FileOutputStream userFile = new FileOutputStream(USERS_FILE);
			FileOutputStream reservationsFile = new FileOutputStream(RESERVATIONS_FILE);
			FileOutputStream materialsFile = new FileOutputStream(MATERIALS_FILE)) {

			JSONArray usersList = JsonSerializer.serializeUserList(application.getUsers().getUsers());
			userFile.write(usersList.toJSONString().getBytes());
			JSONArray reservationsList = JsonSerializer.serializeReservationList(application.getReservations().getAllReservations());
			reservationsFile.write(reservationsList.toJSONString().getBytes());
			JSONArray materialsList = JsonSerializer.serializeMaterialList(application.getMaterials().getAllMaterials(), false);
			materialsFile.write(materialsList.toJSONString().getBytes());
		}
	}
	
}
