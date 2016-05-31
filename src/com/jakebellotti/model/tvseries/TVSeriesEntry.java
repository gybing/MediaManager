package com.jakebellotti.model.tvseries;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TVSeriesEntry {

	private final int databaseID;

	private final String name;

	private final int seriesDefinitionID;

	public TVSeriesEntry(final int databaseID, final String name, final int seriesDefinitionID) {
		this.databaseID = databaseID;
		this.name = name;
		this.seriesDefinitionID = seriesDefinitionID;
	}

	public int getDatabaseID() {
		return databaseID;
	}

	public String getName() {
		return name;
	}

	public int getSeriesDefinitionID() {
		return seriesDefinitionID;
	}

}
