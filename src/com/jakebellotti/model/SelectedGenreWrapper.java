package com.jakebellotti.model;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Jake Bellotti
 * @since 16 Jun 2016
 */
public class SelectedGenreWrapper {
	
	private final CheckBox selection = new CheckBox();
	private final String genre;
	
	public SelectedGenreWrapper(final String genre) {
		this.genre = genre;
		selection.setSelected(true);
	}
	
	public boolean isSelected() {
		return selection.isSelected();
	}

	public CheckBox getSelection() {
		return selection;
	}

	public String getGenre() {
		return genre;
	}

}
