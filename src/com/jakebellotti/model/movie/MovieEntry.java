package com.jakebellotti.model.movie;

import java.io.File;
import java.util.Optional;

import com.jakebellotti.MediaManager;

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
	 * The movie name that was able to be extracted from the file name.
	 */
	private final String extractedMovieName;
	/**
	 * The definition of the movie.
	 */
	private int movieDefinitionID;
	/**
	 * Creates a new Movie File.
	 * @param file
	 * @param definition
	 */
	public MovieEntry(int id, String fileLocation, String extractedMovieName, int definitionID) {
		this.databaseID = id;
		this.file = new File(fileLocation);
		this.extractedMovieName = extractedMovieName;
		this.movieDefinitionID = definitionID;
	}
	
	public File getFile() {
		return file;
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
		return MediaManager.getMediaRepository().getMovieDefinition(movieDefinitionID);
	}
	/**
	 * Returns the name of the file if there is no definition found, else it just returns the movie title.
	 */
	@Override
	public String toString() {
		//FIXME even though it may be marked as having a movie definition, we can never assume that the definition exists until checking
		return movieDefinitionID == 0? extractedMovieName : getDefinition().get().getTitle();
	}
	
	public void setMovieDefinitionID(int newID) {
		this.movieDefinitionID = newID;
	}
	
	public int getMovieDefinitionID() {
		return this.movieDefinitionID;
	}
	
	/**
	 * @return the databaseID
	 */
	public int getDatabaseID() {
		return databaseID;
	}
	
	public String getExtractedMovieName() {
		return extractedMovieName;
	}

}
