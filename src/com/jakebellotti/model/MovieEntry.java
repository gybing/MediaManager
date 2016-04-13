package com.jakebellotti.model;

import java.io.File;

/**
 * 
 * @author Jake Bellotti
 * @date Mar 21, 2016
 */

public class MovieEntry {
	
	/**
	 * The ID of this entry in the database.
	 */
	private final int databaseID;
	/**
	 * The File location of the movie.
	 */
	private final File file;
	/**
	 * The definition of the movie.
	 */
	private final MovieDefinition definition;
	/**
	 * Creates a new Movie File.
	 * @param file
	 * @param definition
	 */
	public MovieEntry(int id, File file, MovieDefinition definition) {
		this.databaseID = id;
		this.file = file;
		this.definition = definition;
	}
	/**
	 * 
	 * @return Whether or not the file exists in it's specified location.
	 */
	public boolean fileExists() {
		return file.exists();
	}
	/**
	 * 
	 * @return The movie definition.
	 */
	public MovieDefinition getDefinition() {
		return definition;
	}
	/**
	 * Returns the name of the file if there is no definition found, else it just returns the movie title.
	 */
	@Override
	public String toString() {
		return definition == null? file.getName() : definition.getTitle();
	}
	/**
	 * @return the databaseID
	 */
	public int getDatabaseID() {
		return databaseID;
	}

}
