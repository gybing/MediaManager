package com.jakebellotti.model.tvseries;

import java.util.ArrayList;
import java.util.Optional;

import com.jakebellotti.MediaManager;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TVSeriesEntry extends TVSeriesNode {

	private final int databaseID;

	private final int seriesDefinitionID;

	private final ArrayList<TVSeriesSeason> seasons = new ArrayList<>();

	public TVSeriesEntry(final int databaseID, final String name, final int seriesDefinitionID) {
		super(name);
		this.databaseID = databaseID;
		this.seriesDefinitionID = seriesDefinitionID;
	}

	/**
	 * 
	 * @return Optional.empty() if there is no definition assigned, or the
	 *         TVSeriesDefinition wrapped in an Optional instance.
	 */
	public Optional<TVSeriesDefinition> getDefinition() {
		return MediaManager.getMediaRepository().getTVSeriesDefinition(seriesDefinitionID);
	}

	public int getDatabaseID() {
		return databaseID;
	}

	public int getSeriesDefinitionID() {
		return seriesDefinitionID;
	}

	public ArrayList<TVSeriesSeason> getSeasons() {
		return seasons;
	}

	public String printInfo() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[" + toString() + "]\n");
		builder.append("Seasons: " + seasons.size() + "\n");
		for (TVSeriesSeason season : seasons) {
			builder.append(season.toString() + "\n");
			for (TVSeriesEpisode e : season.getEpisodes()) {
				builder.append(e.toString() + "\n");
			}
		}
		return builder.toString();
	}

}
