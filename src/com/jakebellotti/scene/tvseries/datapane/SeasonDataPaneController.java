package com.jakebellotti.scene.tvseries.datapane;

import com.jakebellotti.model.tvseries.TVSeriesSeason;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

/**
 *
 * @author Jake Bellotti
 * @since 1 Jun 2016
 */
public class SeasonDataPaneController {

	private static final String THE_MOVIE_DB_LINK = "https://www.themoviedb.org/tv/";

	@FXML
	private Label seasonNumberLabel;

	@FXML
	private Label episodeCountLabel;

	@FXML
	private Hyperlink urlHyperlink;

	@FXML
	public void initialize() {
		updateSeason(null);
	}

	public void updateSeason(final TVSeriesSeason season) {
		if (season == null) {
			seasonNumberLabel.setText("");
			episodeCountLabel.setText("");
			urlHyperlink.setText("");
			return;
		}
		seasonNumberLabel.setText("" + season.getSeasonNumber());
		episodeCountLabel.setText("" + season.getEpisodeCount());
		urlHyperlink.setText(THE_MOVIE_DB_LINK + season.getTheMovieDBID());
	}

}
