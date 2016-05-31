package com.jakebellotti.model.listorderer.movie;

import java.util.Collections;
import java.util.List;

import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.movie.MovieEntryWrapper;

/**
 * @author Jake Bellotti
 * @date Apr 19, 2016
 */
public class MovieAscendingAlphabeticalListOrderer extends ListOrderer<MovieEntryWrapper> {

	@Override
	public String getName() {
		return "Alphabetically (Ascending A-Z)";
	}

	@Override
	public List<MovieEntryWrapper> order(List<MovieEntryWrapper> list) {
		Collections.sort(list, (a, b) -> a.toString().toLowerCase().compareTo(b.toString().toLowerCase()));
		return list;
	}

}
