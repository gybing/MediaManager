package com.jakebellotti.model.tvseries;

import java.io.File;
import java.util.ArrayList;

import com.jakebellotti.Settings;

import javafx.scene.image.Image;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TVSeriesSeason extends TVSeriesNode {

	private final ArrayList<TVSeriesEpisode> episodes = new ArrayList<>();
	private Image poster = null;

	private final int databaseID;
	private final int seasonNumber;
	private final int episodeCount;
	private final int theMovieDBID;
	// TODO use TheMovieDBImageString instead of a regular String.
	private final String posterURL;
	private final int tvSeriesEntryID;
	private final String overview;

	public TVSeriesSeason(final int databaseID, final int seasonNumber, final int episodeCount, final int theMovieDBID,
			final String posterURL, final int tvSeriesEntryID, final String overview) {
		this.databaseID = databaseID;
		this.seasonNumber = seasonNumber;
		this.episodeCount = episodeCount;
		this.theMovieDBID = theMovieDBID;
		this.posterURL = posterURL;
		this.tvSeriesEntryID = tvSeriesEntryID;
		this.overview = overview;
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

	public Image getPoster() {
		if(poster == null) {
			final File posterFile = new File("./data/img/tvseries/poster/" + databaseID + "_season_poster.jpg");
			if(Settings.isMemorySaverMode())
				return new Image(posterFile.toURI().toString());
			poster = new Image(posterFile.toURI().toString());
		}
		return poster;
	}

	public String getOverview() {
		return overview;
	}

	@Override
	public String getDisplayName() {
		return "Season " + seasonNumber;
	}

}
