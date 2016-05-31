package com.jakebellotti.model.tvseries;

import java.util.ArrayList;

public class TVSeriesSeason extends TVSeriesNode {
	
	private final ArrayList<TVSeriesEpisode> episodes = new ArrayList<>();

	public TVSeriesSeason(String name) {
		super(name);
	}

	public ArrayList<TVSeriesEpisode> getEpisodes() {
		return episodes;
	}

}
