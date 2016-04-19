package com.jakebellotti.model.listfilter;

import java.util.Collections;
import java.util.List;

import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.movie.MovieEntry;

/**
 * @author Jake Bellotti
 * @date Apr 19, 2016
 */
public class DescendingAlphabeticalListOrderer extends ListOrderer<MovieEntry> {

	@Override
	public String getName() {
		return "Alphabetically (Descending Z-A)";
	}

	@Override
	public List<MovieEntry> order(List<MovieEntry> list) {
		Collections.sort(list, (a, b) -> b.toString().compareTo(a.toString()));
		return list;
	}

}
