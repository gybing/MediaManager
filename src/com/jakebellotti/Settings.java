package com.jakebellotti;

/**
 * 
 * @author Jake Bellotti
 * @date Feb 17, 2016
 */

public class Settings {
	
	private static boolean memorySaverMode = false;

	public static boolean isMemorySaverMode() {
		return memorySaverMode;
	}

	public static void setMemorySaverMode(boolean memorySaverMode) {
		Settings.memorySaverMode = memorySaverMode;
	}

}
