package reserve.controller.io;

import java.io.IOException;

import reserve.controller.AppController;

public interface AppStorage {
	
	/**
	 * Writes all the application data such that it may be restored
	 * using {@link #loadApplicationData(AppController)}.
	 * 
	 * @param application the application data
	 */
	public void writeApplicationData(AppController application) throws IOException;

	/**
	 * Loads all stored application data previously written by
	 * {@link #writeApplicationData(AppController)}.
	 * 
	 * @param application the application data to be filled
	 */
	public void loadApplicationData(AppController application) throws IOException;
	
}
