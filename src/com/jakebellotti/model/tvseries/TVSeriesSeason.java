package com.jakebellotti.model.tvseries;

import java.util.ArrayList;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TVSeriesSeason extends TVSeriesNode {

	private final ArrayList<TVSeriesEpisode> episodes = new ArrayList<>();

	private final int databaseID;
	private final int seasonNumber;
	private final int episodeCount;
	private final int theMovieDBID;
	// TODO use TheMovieDBImageString instead of a regular String.
	private final String posterURL;
	private final int tvSeriesEntryID;

	public TVSeriesSeason(final int databaseID, final int seasonNumber, final int episodeCount, final int theMovieDBID,
			final String posterURL, final int tvSeriesEntryID) {
		super("Season " + seasonNumber);
		this.databaseID = databaseID;
		this.seasonNumber = seasonNumber;
		this.episodeCount = episodeCount;
		this.theMovieDBID = theMovieDBID;
		this.posterURL = posterURL;
		this.tvSeriesEntryID = tvSeriesEntryID;
	}

	public int getDatabaseID() {
		return databaseID;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}

	public int getEpisodeCount() {
		return episodeCount;
	}

	public int getTheMovieDBID() {
		return theMovieDBID;
	}

	public String getPosterURL() {
		return posterURL;
	}

	public int getTvSeriesEntryID() {
		return tvSeriesEntryID;
	}

	public ArrayList<TVSeriesEpisode> getEpisodes() {
		return episodes;
	}

}
