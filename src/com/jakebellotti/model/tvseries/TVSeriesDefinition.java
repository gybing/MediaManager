package com.jakebellotti.model.tvseries;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TVSeriesDefinition {
	
//	ID								INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
//	firstAirDate					VARCHAR(255) DEFAULT NULL,
//homePageURL						VARCHAR(255) DEFAULT NULL,
//name							VARCHAR(255) DEFAULT NULL,
//numberOfEpisodes				INTEGER DEFAULT NULL,
//overview						VARCHAR(255) DEFAULT NULL,
//posterURL						VARCHAR(255) DEFAULT NULL
	
	private final int id;
	
	public TVSeriesDefinition(int id) {
		this.id = id;
	}

}
