package com.jakebellotti;

import java.io.File;

/**
 * Contains constants relating to Files found in the data folder
 * @author Jake Bellotti
 * @date Feb 17, 2016
 */

public class DataConstants {
	
	/**
	 * The root data folder, where all of the data is to be kept
	 */
	public static final File DATA_FOLDER = new File("./data/");
	/**
	 * The media folder, which contains seperate categories of media
	 */
	public static final File MEDIA_FOLDER = new File(DATA_FOLDER + "/media/");

}
