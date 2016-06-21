package com.jakebellotti.model.movie.filter;

import java.util.ArrayList;
import java.util.Optional;

import com.jakebellotti.model.ListFilter;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;

/**
 *
 * @author Jake Bellotti
 * @since Jun 16, 2016
 */
public class MovieActorFilter implements ListFilter<MovieEntry> {
	
	private final ArrayList<String> actors;

	public MovieActorFilter(final ArrayList<String> actors) {
		this.actors = actors;
	}

	@Override
	public boolean shouldAdd(MovieEntry listItem) {
		Optional<MovieDefinition> def = listItem.getDefinition();
		if (!def.isPresent())
			return false;
		String[] movieActors = def.get().getActors().split(",");
		for (String g : movieActors) {
			if (actors.contains(g.trim()))
				return true;
		}
		return false;
	}

}
