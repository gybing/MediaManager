package com.jakebellotti.model.tvseries;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TVSeriesDefinition {

	private final int databaseID;
	private final String name;
	private final String firstAirDate;
	private final String lastAirDate;
	private final String homePageURL;
	private final String posterURL;
	private final String backdropURL;
	private final int episodeCount;
	private final int seasonCount;
	private final String overview;

	public TVSeriesDefinition(int databaseID, String name, String firstAirDate, String lastAirDate, String homePageURL,
			String posterURL, String backdropURL, int episodeCount, int seasonCount, String overview) {
		this.databaseID = databaseID;
		this.name = name;
		this.firstAirDate = firstAirDate;
		this.lastAirDate = lastAirDate;
		this.homePageURL = homePageURL;
		this.posterURL = posterURL;
		this.backdropURL = backdropURL;
		this.episodeCount = episodeCount;
		this.seasonCount = seasonCount;
		this.overview = overview;
	}

	public int getDatabaseID() {
		return databaseID;
	}

	public String getName() {
		return name;
	}

	public String getFirstAirDate() {
		return firstAirDate;
	}

	public String getLastAirDate() {
		return lastAirDate;
	}

	public String getHomePageURL() {
		return homePageURL;
	}

	public String getPosterURL() {
		return posterURL;
	}

	public String getBackdropURL() {
		return backdropURL;
	}

	public int getEpisodeCount() {
		return episodeCount;
	}

	public int getSeasonCount() {
		return seasonCount;
	}

	public String getOverview() {
		return overview;
	}

}
