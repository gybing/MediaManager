package com.jakebellotti.plugin;

import java.util.ArrayList;

import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.movie.MovieEntryWrapper;

/**
 *
 * @author Jake Bellotti
 * @since Jun 21, 2016
 */
public class MovieListOrdererRepository {
	
	private static final ArrayList<ListOrderer<MovieEntryWrapper>> orderers = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public static final void add(Class<?> orderer) {
		try {
			Object o = orderer.newInstance();
			if(o instanceof ListOrderer) {
				orderers.add((ListOrderer<MovieEntryWrapper>) o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<ListOrderer<MovieEntryWrapper>> getOrderers() {
		return orderers;
	}

}
