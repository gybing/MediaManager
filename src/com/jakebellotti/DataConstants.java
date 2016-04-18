package com.jakebellotti;

import java.io.File;

/**
 * Contains constants relating to Files found in the data folder
 * 
 * @author Jake Bellotti
 * @date Feb 17, 2016
 */

public class DataConstants {
	/**
	 * The root data folder, where all of the data is to be kept.
	 */
	public static final File DATA_FOLDER = new File("./data/");
	/**
	 * A folder to store all images.
	 */
	public static final File IMAGE_ROOT_FOLDER = new File(DATA_FOLDER + "/img/");
	/**
	 * The folder where all of the images for movies are kept.
	 */
	public static final File MOVIE_IMAGE_FOLDER = new File(IMAGE_ROOT_FOLDER + "/movie");
	/**
	 * The folder where all of the cover art for music is kept.
	 */
	public static final File MUSIC_IMAGE_FOLDER = new File(IMAGE_ROOT_FOLDER + "/music");
	/**
	 * Where settings should be stored.
	 */
	public static final File SETTINGS_FILE = new File(DATA_FOLDER + "/settings.xml");

}
