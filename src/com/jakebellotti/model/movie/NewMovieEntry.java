package com.jakebellotti.model.movie;

import java.io.File;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class NewMovieEntry {
	
	private final File fileLocation;
	private final String movieNameBefore;
	private final String movieName;
	
	public NewMovieEntry(File fileLocation, String movieName) {
		this.fileLocation = fileLocation;
		this.movieNameBefore = fileLocation.getName();
		this.movieName = movieName;
	}

	public File getFileLocation() {
		return fileLocation;
	}

	public String getMovieName() {
		return movieName;
	}

	public String getMovieNameBefore() {
		return movieNameBefore;
	}

}
