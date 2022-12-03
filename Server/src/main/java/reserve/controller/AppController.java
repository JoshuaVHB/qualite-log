package reserve.controller;

import java.io.IOException;

import reserve.Main;
import reserve.controller.io.AppStorage;
import reserve.util.Logger;

public class AppController {
	
	private static final Logger logger = Main.LOGGER_FACTORY.getLogger("app");
	
	private final AppStorage storage;
	private final MaterialController materials;
	private final ReservationController reservations;
	private final UserController users;
	
	public AppController(AppStorage storage, MaterialController materials, ReservationController reservations, UserController users) {
		this.storage = storage;
		this.materials = materials;
		this.reservations = reservations;
		this.users = users;
	}
	
	public void startApplication() {
		try {
			storage.loadApplicationData(this);
		} catch (IOException e) {
			logger.merr(e, "Unable to load application data");
			System.exit(1);
		}
	}
	
	public void endApplication() {
		try {
			storage.writeApplicationData(this);
		} catch (IOException e) {
			logger.merr(e, "Unable to write application data");
		}
	}
	
	public MaterialController getMaterials() {
		return materials;
	}
	
	public ReservationController getReservations() {
		return reservations;
	}
	
	public UserController getUsers() {
		return users;
	}
	
}
