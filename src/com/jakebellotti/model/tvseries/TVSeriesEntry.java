package com.jakebellotti.model.tvseries;

import java.util.ArrayList;

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

	public int getDatabaseID() {
		return databaseID;
	}

	public int getSeriesDefinitionID() {
		return seriesDefinitionID;
	}

	public ArrayList<TVSeriesSeason> getSeasons() {
		return seasons;
	}

}
