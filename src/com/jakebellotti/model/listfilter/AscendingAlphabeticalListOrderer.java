package com.jakebellotti.model.listfilter;

import java.util.Collections;
import java.util.List;

import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.movie.MovieEntry;

/**
 * @author Jake Bellotti
 * @date Apr 19, 2016
 */
public class AscendingAlphabeticalListOrderer extends ListOrderer<MovieEntry> {

	@Override
	public String getName() {
		return "Alphabetically (Ascending A-Z)";
	}

	@Override
	public List<MovieEntry> order(List<MovieEntry> list) {
		Collections.sort(list, (a, b) -> a.toString().compareTo(b.toString()));
		return list;
	}

}
