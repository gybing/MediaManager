package com.jakebellotti.scene.tvseries.datapane;

import com.jakebellotti.Constants;
import com.jakebellotti.model.tvseries.TVSeriesSeason;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author Jake Bellotti
 * @since 1 Jun 2016
 */
public class SeasonDataPaneController {

	@FXML
	private Label seasonNumberLabel;

	@FXML
	private Label episodeCountLabel;

	@FXML
	private Hyperlink urlHyperlink;
	
    @FXML
    private TextArea plotTextArea;

	@FXML
	public void initialize() {
		updateSeason(null);
	}

	public void updateSeason(final TVSeriesSeason season) {
		if (season == null) {
			seasonNumberLabel.setText("");
			episodeCountLabel.setText("");
			urlHyperlink.setText("");
			plotTextArea.setText("");
			return;
		}
		seasonNumberLabel.setText("" + season.getSeasonNumber());
		episodeCountLabel.setText("" + season.getEpisodeCount());
		urlHyperlink.setText(Constants.THE_MOVIE_DB_TV_LINK + season.getTheMovieDBID());
		plotTextArea.setText(season.getOverview());
	}

}
