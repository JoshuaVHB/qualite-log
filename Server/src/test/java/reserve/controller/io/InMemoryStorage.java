package reserve.controller.io;

import reserve.controller.AppController;

/** A mock for application storage. */
public class InMemoryStorage implements AppStorage {

	@Override
	public void loadApplicationData(AppController application) {
		
	}
	
	@Override
	public void writeApplicationData(AppController application) {
		// NOOP
	}
	
}
