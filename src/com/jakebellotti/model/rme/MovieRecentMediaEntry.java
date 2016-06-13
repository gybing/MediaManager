package com.jakebellotti.model.rme;

import java.time.LocalDate;
import java.util.Optional;

import com.jakebellotti.FileOpener;
import com.jakebellotti.MediaManager;
import com.jakebellotti.model.RecentMediaEntry;
import com.jakebellotti.model.movie.MovieEntry;

import javafx.scene.control.Button;

/**
 *
 * @author Jake Bellotti
 * @since 13 Jun 2016
 */
public class MovieRecentMediaEntry extends RecentMediaEntry {

	public MovieRecentMediaEntry(int databaseID, LocalDate datePlayed) {
		super(databaseID, datePlayed);
	}

	@Override
	public void onSelect(Button button) {
		String buttonText = "Open Movie";
		Optional<MovieEntry> e = MediaManager.getMediaRepository().getMovieEntryForID(getDatabaseID());
		if (e.isPresent()) {
			buttonText = "Open '" + e.get().toString() + "'";
			button.setOnMouseClicked(file -> {
//				TODO should we also record opening this file in the database?
				FileOpener.openMedia(e.get().getFile());
			});
		}
		
		button.setText(buttonText);
		button.setDisable(false);
	}

	@Override
	public String toString() {
		Optional<MovieEntry> e = MediaManager.getMediaRepository().getMovieEntryForID(getDatabaseID());
		if (e.isPresent())
			return "(" + getDatePlayed().toString() + ") - " + e.get().toString();
		return "(" + getDatePlayed().toString() + ") movie #" + getDatabaseID();
	}

}
