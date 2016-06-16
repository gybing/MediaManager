package com.jakebellotti.model.movie;

import java.util.ArrayList;

/**
 *
 * @author Jake Bellotti
 * @since 14 Jun 2016
 */
public class DuplicateMovieEntry {
	
	private final MovieDefinition definition;
	private final ArrayList<MovieEntry> duplicates;
	
	public DuplicateMovieEntry(final MovieDefinition def, final ArrayList<MovieEntry> duplicates) {
		this.definition = def;
		this.duplicates = duplicates;
	}

	public MovieDefinition getDefinition() {
		return definition;
	}

	public ArrayList<MovieEntry> getDuplicates() {
		return duplicates;
	}

}
