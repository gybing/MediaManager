package com.jakebellotti.model;

import java.time.LocalDate;

import javafx.scene.control.Button;

/**
 *
 * @author Jake Bellotti
 * @since 13 Jun 2016
 */
public abstract class RecentMediaEntry {
	
	private final int databaseID;
	private final LocalDate datePlayed;
	
	public RecentMediaEntry(int databaseID, LocalDate datePlayed) {
		this.databaseID = databaseID;
		this.datePlayed = datePlayed;
	}
	
	public abstract void onSelect(final Button button);

	public int getDatabaseID() {
		return databaseID;
	}

	public LocalDate getDatePlayed() {
		return datePlayed;
	}

}
