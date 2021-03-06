package com.jakebellotti.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.jakebellotti.MediaManager;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.MovieEntryWrapper;
import com.jakebellotti.model.tvseries.TVSeriesDefinition;
import com.jakebellotti.model.tvseries.TVSeriesEntry;
import com.jakebellotti.model.tvseries.TVSeriesEpisodeDefinition;

/**
 * Holds all the media data for this program.
 * 
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class MediaRepository {

	private final HashMap<Integer, TVSeriesEpisodeDefinition> tvSeriesEpisodeDefinitions = new HashMap<>();
	private final HashMap<Integer, TVSeriesDefinition> tvSeriesDefinitions = new HashMap<>();

	private final ArrayList<TVSeriesEntry> loadedTVSeries = new ArrayList<>();
	/**
	 * An ArrayList to hold all of the MovieEntry objects. This should never
	 * change after the data has been loaded, unless the entry is being removed
	 * for good.
	 */
	private final ArrayList<MovieEntry> loadedMovieEntries = new ArrayList<>();
	/**
	 * The list of filters for movie entries.
	 */
	private final ArrayList<ListFilter<MovieEntry>> movieEntryFilters = new ArrayList<>();
	/**
	 * A HashMap to hold all of the MovieDefinition objects.
	 */
	private final HashMap<Integer, MovieDefinition> movieDefinitions = new HashMap<>();
	/**
	 * The search text on the movie view that is factored in when retrieving the
	 * displayed movies.
	 */
	private String movieSearchText;

	// MOVIES

	public final void addMovieDefinitions(final ArrayList<MovieDefinition> definitionList) {
		definitionList.forEach(def -> assignMovieDefinition(def.getDatabaseID(), def));
	}

	public final void assignMovieDefinition(int id, MovieDefinition definition) {
		movieDefinitions.put(id, definition);
	}

	public final Optional<MovieDefinition> getMovieDefinition(int id) {
		return Optional.ofNullable(movieDefinitions.get(id));
	}

	public final void addMovieEntries(ArrayList<MovieEntry> movieEntries) {
		movieEntries.forEach((e) -> addMovieEntry(e));
	}

	public final void addMovieEntry(MovieEntry movieEntry) {
		loadedMovieEntries.add(movieEntry);
	}
	
	public final Optional<MovieEntry> getMovieEntryForID(final int id) {
		for (MovieEntry e : MediaManager.getMediaRepository().getLoadedMovieEntries()) {
			if(e.getDatabaseID() == id)
				return Optional.ofNullable(e);
		}
		return Optional.empty();
	}

	// TV SERIES
	

	public final void assignTVSeriesDefinition(final int key, final TVSeriesDefinition definition) {
		this.tvSeriesDefinitions.put(key, definition);
	}

	public final void addTVSeriesDefinitions(final ArrayList<TVSeriesDefinition> definitionList) {
		definitionList.forEach(def -> assignTVSeriesDefinition(def.getDatabaseID(), def));
	}

	public final Optional<TVSeriesDefinition> getTVSeriesDefinition(int id) {
		return Optional.ofNullable(tvSeriesDefinitions.get(id));
	}

	public final void addTVSeriesEntries(ArrayList<TVSeriesEntry> tvSeriesEntries) {
		tvSeriesEntries.forEach(this::addTVSeriesEntry);
	}

	public final void addTVSeriesEntry(TVSeriesEntry entry) {
		this.loadedTVSeries.add(entry);
	}
	
	public final void addTVSeriesEpisodeDefinitions(final ArrayList<TVSeriesEpisodeDefinition> definitions) {
		definitions.forEach(def -> assignTVSeriesEpisodeDefinition(def.getDatabaseID(), def));
	}
	
	public final void assignTVSeriesEpisodeDefinition(final int key, final TVSeriesEpisodeDefinition definition) {
		this.tvSeriesEpisodeDefinitions.put(key, definition);
	}

	// MUSIC

	/**
	 * 
	 * @return An ArrayList of MovieEntry objects, that have passed the filter
	 *         testing.
	 */
	public final ArrayList<MovieEntryWrapper> getDisplayedMovieEntries() {
		final ArrayList<MovieEntryWrapper> toReturn = new ArrayList<>();
		for (MovieEntry currentMovieEntry : loadedMovieEntries) {
			if (shouldAddMovieEntry(currentMovieEntry)) {
				if (this.movieSearchText == null || this.movieSearchText.length() < 0) {
					toReturn.add(new MovieEntryWrapper(currentMovieEntry));
				} else {
					if (currentMovieEntry.toString().toLowerCase().contains(this.movieSearchText.toLowerCase())) {
						toReturn.add(new MovieEntryWrapper(currentMovieEntry));
					}
				}
			}
		}
		return toReturn;
	}

	public HashMap<Integer, TVSeriesDefinition> getTvSeriesDefinitions() {
		return tvSeriesDefinitions;
	}

	public final ArrayList<TVSeriesEntry> getDisplayedTVSeriesEntries() {
		// TODO eventually implement filtering logic to TV series
		return loadedTVSeries;
	}

	public void addMovieEntryFilter(final ListFilter<MovieEntry> filter) {
		movieEntryFilters.add(filter);
	}
	
	public ArrayList<ListFilter<MovieEntry>> getMovieEntryFilters() {
		return movieEntryFilters;
	}
	
	/**
	 * Tests a MovieEntry object against all of the ListFilter objects to
	 * determine whether or not this MovieEntry should be added.
	 * 
	 * @param movieEntry
	 * @return
	 */
	private final boolean shouldAddMovieEntry(MovieEntry movieEntry) {
		for (ListFilter<MovieEntry> currentFilter : movieEntryFilters) {
			if(currentFilter == null)
				continue;
			if (!currentFilter.shouldAdd(movieEntry))
				return false;
		}
		return true;
	}

	public final ArrayList<MovieEntry> getLoadedMovieEntries() {
		return loadedMovieEntries;
	}

	public ArrayList<TVSeriesEntry> getLoadedTVSeries() {
		return loadedTVSeries;
	}

	public final HashMap<Integer, MovieDefinition> getLoadedMovieDefinitions() {
		return this.movieDefinitions;
	}

	public String getMovieSearchText() {
		return movieSearchText;
	}

	public void setMovieSearchText(String movieSearchText) {
		this.movieSearchText = movieSearchText;
	}

	public HashMap<Integer, TVSeriesEpisodeDefinition> getTvSeriesEpisodeDefinitions() {
		return tvSeriesEpisodeDefinitions;
	}
}
