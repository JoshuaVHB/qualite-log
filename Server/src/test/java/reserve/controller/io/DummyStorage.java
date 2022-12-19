package reserve.controller.io;

import reserve.controller.AppController;
import reserve.model.Material;
import reserve.model.MaterialType;
import reserve.model.OperatingSystem;

/** A mock for application storage. */
public class DummyStorage implements AppStorage {

	@Override
	public void loadApplicationData(AppController application) {
		application.getMaterials().addMaterial(new Material(OperatingSystem.AN, MaterialType.LAPTOP, "AN-laptop", "v0.1", 1));
		application.getMaterials().addMaterial(new Material(OperatingSystem.AP, MaterialType.LAPTOP, "AN-laptop", "v0.1", 5));
		application.getMaterials().addMaterial(new Material(OperatingSystem.XX, MaterialType.MOBILE, "AN-mobile", "v0.1", 1321));
		application.getMaterials().addMaterial(new Material(OperatingSystem.XX, MaterialType.MOBILE, "AN-mobile", "v0.1", 624));
		application.getMaterials().addMaterial(new Material(OperatingSystem.AP, MaterialType.TABLET, "AP-tablet", "v0.2", 1));
		application.getMaterials().addMaterial(new Material(OperatingSystem.AP, MaterialType.TABLET, "AP-tablet", "v0.1", 2));
	}
	
	@Override
	public void writeApplicationData(AppController application) {
		// NOOP
	}
	
}
