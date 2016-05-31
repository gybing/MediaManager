package com.jakebellotti.model.tvseries;

public class TVSeriesEpisode extends TVSeriesNode {

	private final int databaseID;
	private final String fileLocation;
	private final int episodeNumber;
	private final int tvSeriesSeasonID;
	private final int tvSeriesEpisodeDefinitionID;

	public TVSeriesEpisode(final int databaseID, final String fileLocation, final int episodeNumber,
			final int tvSeriesSeasonID, final int tvSeriesEpisodeDefinitionID) {
		super("Episode " + episodeNumber);
		this.databaseID = databaseID;
		this.fileLocation = fileLocation;
		this.episodeNumber = episodeNumber;
		this.tvSeriesSeasonID = tvSeriesSeasonID;
		this.tvSeriesEpisodeDefinitionID = tvSeriesEpisodeDefinitionID;
	}

	public int getDatabaseID() {
		return databaseID;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

	public int getTvSeriesSeasonID() {
		return tvSeriesSeasonID;
	}

	public int getTvSeriesEpisodeDefinitionID() {
		return tvSeriesEpisodeDefinitionID;
	}

}
