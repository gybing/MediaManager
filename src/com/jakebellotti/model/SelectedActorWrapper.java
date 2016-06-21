package com.jakebellotti.model;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Jake Bellotti
 * @since Jun 16, 2016
 */
public class SelectedActorWrapper {
	
	private final CheckBox selection = new CheckBox();
	private final String actor;
	
	public SelectedActorWrapper(final String actor) {
		this.actor = actor;
		selection.setSelected(true);
	}
	
	public boolean isSelected() {
		return selection.isSelected();
	}

	public CheckBox getSelection() {
		return selection;
	}

	public String getActor() {
		return actor;
	}

}
