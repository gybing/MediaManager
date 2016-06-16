package com.jakebellotti.model.movie.filter;

import java.util.ArrayList;
import java.util.Optional;

import com.jakebellotti.model.ListFilter;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;

/**
 *
 * @author Jake Bellotti
 * @since 16 Jun 2016
 */
public class MovieGenreFilter implements ListFilter<MovieEntry> {

	private final ArrayList<String> genres;

	public MovieGenreFilter(final ArrayList<String> genres) {
		this.genres = genres;
	}

	@Override
	public boolean shouldAdd(MovieEntry listItem) {
		Optional<MovieDefinition> def = listItem.getDefinition();
		if (!def.isPresent())
			return false;
		String[] movieGenres = def.get().getGenre().split(",");
		for (String g : movieGenres) {
			if (genres.contains(g.trim()))
				return true;
		}
		return false;
	}

}
