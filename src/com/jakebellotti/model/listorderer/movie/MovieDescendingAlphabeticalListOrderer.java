package com.jakebellotti.model.listorderer.movie;

import java.util.Collections;
import java.util.List;

import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.movie.MovieEntryWrapper;

/**
 * @author Jake Bellotti
 * @date Apr 19, 2016
 */
public class MovieDescendingAlphabeticalListOrderer extends ListOrderer<MovieEntryWrapper> {

	@Override
	public String getName() {
		return "Alphabetically (Descending Z-A)";
	}

	@Override
	public List<MovieEntryWrapper> order(List<MovieEntryWrapper> list) {
		Collections.sort(list, (a, b) -> b.toString().toLowerCase().compareTo(a.toString().toLowerCase()));
		return list;
	}

}
