package com.jakebellotti.model;

import java.util.ArrayList;

/**
 * Holds all the media data for this program.
 * 
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class MediaRepository {

	/**
	 * An ArrayList to hold all of the MovieEntry objects. This should never
	 * change after the data has been loaded, unless the entry is being removed
	 * for good.
	 */
	private static final ArrayList<MovieEntry> loadedMovieEntries = new ArrayList<>();
	/**
	 * The list of filters for movie entries.
	 */
	private static final ArrayList<ListFilter<MovieEntry>> movieEntryFilters = new ArrayList<>();

	/**
	 * 
	 * @return An ArrayList of MovieEntry objects, that have passed the filter
	 *         testing.
	 */
	public static final ArrayList<MovieEntry> getDisplayedMovieEntries() {
		final ArrayList<MovieEntry> toReturn = new ArrayList<>();
		for (MovieEntry currentMovieEntry : loadedMovieEntries) {
			if (shouldAddMovieEntry(currentMovieEntry))
				toReturn.add(currentMovieEntry);
		}
		return toReturn;
	}

	/**
	 * Tests a MovieEntry object against all of the ListFilter objects to
	 * determine whether or not this MovieEntry should be added.
	 * 
	 * @param movieEntry
	 * @return
	 */
	private static final boolean shouldAddMovieEntry(MovieEntry movieEntry) {
		for (ListFilter<MovieEntry> currentFilter : movieEntryFilters) {
			if (!currentFilter.shouldAdd(movieEntry))
				return false;
		}
		return true;
	}
}
