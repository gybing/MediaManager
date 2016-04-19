package com.jakebellotti.fx.impl;

import com.jakebellotti.fx.ListViewModifier;
import com.jakebellotti.model.movie.MovieEntry;

import javafx.scene.control.ListView;

/**
 * 
 * @author Jake Bellotti
 * @date Apr 19, 2016
 *
 */
public class DetailsListViewModifier extends ListViewModifier<MovieEntry> {

	@Override
	public void change(ListView<MovieEntry> listView) {
		
	}

	@Override
	public String getName() {
		return "Details";
	}

}
