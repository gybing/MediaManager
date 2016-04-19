package com.jakebellotti.fx;

import javafx.scene.control.ListView;

/**
 * Modifies a list view.
 * 
 * @author Jake Bellotti
 * @date Apr 19, 2016
 *
 */
public abstract class ListViewModifier<T extends Object> {

	public abstract void change(ListView<T> listView);

	public abstract String getName();

	@Override
	public String toString() {
		return getName();
	}

}
