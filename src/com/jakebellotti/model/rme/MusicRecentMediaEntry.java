package com.jakebellotti.model.rme;

import java.time.LocalDate;

import com.jakebellotti.model.RecentMediaEntry;

import javafx.scene.control.Button;

/**
 *
 * @author Jake Bellotti
 * @since 13 Jun 2016
 */
public class MusicRecentMediaEntry extends RecentMediaEntry {

	public MusicRecentMediaEntry(int databaseID, LocalDate datePlayed) {
		super(databaseID, datePlayed);
	}

	@Override
	public void onSelect(Button button) {
		
	}
	
	@Override
	public String toString() {
		return "(" + getDatePlayed().toString() + ") ";
	}

}
