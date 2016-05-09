package com.jakebellotti.model.listorderer;

import java.util.Collections;
import java.util.List;

import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.movie.MovieEntryWrapper;

/**
 * 
 * @author Jake Bellotti Date 9-May-2016
 *
 */
public class AverageScoreListOrderer extends ListOrderer<MovieEntryWrapper> {

	@Override
	public String getName() {
		return "By Average Score (Descending)";
	}

	@Override
	public List<MovieEntryWrapper> order(List<MovieEntryWrapper> list) {
		Collections.sort(list, (a, b) -> {
			Double aScore = new Double(0);
			Double bScore = new Double(0);
			if (a.getMovieEntry().getDefinition().isPresent()) {
				double aMeta = a.getMovieEntry().getDefinition().get().getMetascore();
				double aIMDB = a.getMovieEntry().getDefinition().get().getImdbRating();
				if (aMeta < 0)
					aMeta = 0;
				if (aIMDB < 0)
					aIMDB = 0;
				aScore = ((aIMDB * 10) + aMeta);
				if (aScore > 0)
					aScore = (aScore / 2);
			}
			if (b.getMovieEntry().getDefinition().isPresent()) {
				double bMeta = b.getMovieEntry().getDefinition().get().getMetascore();
				double bIMDB = b.getMovieEntry().getDefinition().get().getImdbRating();
				if (bMeta < 0)
					bMeta = 0;
				if (bIMDB < 0)
					bIMDB = 0;
				bScore = ((bIMDB * 10) + bMeta);
				if (bScore > 0)
					bScore = (bScore / 2);
			}
			return bScore.compareTo(aScore);
		});
		return list;
	}

}
