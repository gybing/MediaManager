package com.jakebellotti.model.rme;

import java.time.LocalDate;

import com.jakebellotti.model.RecentMediaEntry;

import javafx.scene.control.Button;

/**
 *
 * @author Jake Bellotti
 * @since 13 Jun 2016
 */
public class TVSeriesRecentMediaEntry extends RecentMediaEntry {

	public TVSeriesRecentMediaEntry(int databaseID, LocalDate datePlayed) {
		super(databaseID, datePlayed);
	}

	@Override
	public void onSelect(Button button) {
		
	}

}
