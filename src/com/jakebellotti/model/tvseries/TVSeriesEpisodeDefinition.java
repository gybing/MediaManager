package com.jakebellotti.model.tvseries;

/**
 *
 * @author Jake Bellotti
 * @since Jun 1, 2016
 */
public class TVSeriesEpisodeDefinition {

	private final int databaseID;
	private final String name;
	private final String overview;
	private final int themoviedbID;
	private final String stillImageURL;

	public TVSeriesEpisodeDefinition(int databaseID, String name, String overview, int themoviedbID, String stillImageURL) {
		this.databaseID = databaseID;
		this.name = name;
		this.overview = overview;
		this.themoviedbID = themoviedbID;
		this.stillImageURL = stillImageURL;
	}

	public String getName() {
		return name;
	}

	public String getOverview() {
		return overview;
	}

	public int getThemoviedbID() {
		return themoviedbID;
	}

	public String getStillImageURL() {
		return stillImageURL;
	}

	public int getDatabaseID() {
		return databaseID;
	}

}
