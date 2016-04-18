package com.jakebellotti;

/**
 * 
 * @author Jake Bellotti
 * @date Feb 17, 2016
 */

public class Settings {
	
	private static boolean memorySaverMode = DefaultSettings.MEMORY_SAVER_MODE;
	
	private static String[] videoFileAssociations = DefaultSettings.DEFAULT_VIDEO_EXTENSIONS;

	public static boolean isMemorySaverMode() {
		return memorySaverMode;
	}

	public static void setMemorySaverMode(boolean memorySaverMode) {
		Settings.memorySaverMode = memorySaverMode;
	}

	public static String[] getVideoFileAssociations() {
		return videoFileAssociations;
	}

	public static void setVideoFileAssociations(String[] videoFileAssociations) {
		Settings.videoFileAssociations = videoFileAssociations;
	}

}
