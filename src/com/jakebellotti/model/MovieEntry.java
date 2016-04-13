package com.jakebellotti.model;

import java.io.File;
import java.util.Optional;

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
	private final int movieDefinitionID;
	/**
	 * Creates a new Movie File.
	 * @param file
	 * @param definition
	 */
	public MovieEntry(int id, String fileLocation, int definitionID) {
		this.databaseID = id;
		this.file = new File(fileLocation);
		this.movieDefinitionID = definitionID;
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
	public Optional<MovieDefinition> getDefinition() {
		return MediaRepository.getMovieDefinition(movieDefinitionID);
	}
	/**
	 * Returns the name of the file if there is no definition found, else it just returns the movie title.
	 */
	@Override
	public String toString() {
		return movieDefinitionID == 0? file.getName() : getDefinition().get().getTitle();
	}
	/**
	 * @return the databaseID
	 */
	public int getDatabaseID() {
		return databaseID;
	}

}
