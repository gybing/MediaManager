package com.jakebellotti.model.listorderer.movie;

import java.util.Collections;
import java.util.List;

import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.movie.MovieEntryWrapper;

/**
 * 
 * @author Jake Bellotti
 * Date 9-May-2016
 *
 */
public class MovieMetascoreListOrderer extends ListOrderer<MovieEntryWrapper> {

	@Override
	public String getName() {
		return "By Metascore (Descending)";
	}

	@Override
	public List<MovieEntryWrapper> order(List<MovieEntryWrapper> list) {
		Collections.sort(list, (a, b) -> {
			Integer aScore = new Integer(-1);
			Integer bScore = new Integer(-1);
			if (a.getMovieEntry().getDefinition().isPresent()) {
				aScore = a.getMovieEntry().getDefinition().get().getMetascore();
			}
			if (b.getMovieEntry().getDefinition().isPresent()) {
				bScore = b.getMovieEntry().getDefinition().get().getMetascore();
			}
			return bScore.compareTo(aScore);
		});
		return list;
	}

}
