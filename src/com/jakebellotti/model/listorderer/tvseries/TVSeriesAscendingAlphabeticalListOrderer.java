package com.jakebellotti.model.listorderer.tvseries;

import java.util.Collections;
import java.util.List;

import com.jakebellotti.model.TVSeriesListOrderer;
import com.jakebellotti.model.tvseries.TVSeriesEntry;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TVSeriesAscendingAlphabeticalListOrderer extends TVSeriesListOrderer {

	@Override
	public String getName() {
		return "Alphabetically (Ascending A-Z)";
	}

	@Override
	public List<TVSeriesEntry> order(List<TVSeriesEntry> list) {
		Collections.sort(list, (a, b) -> a.toString().toLowerCase().compareTo(b.toString().toLowerCase()));
		return list;
	}

}
