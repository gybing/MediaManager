package com.jakebellotti.scene.tvseries.datapane;

import java.util.Optional;

import com.jakebellotti.model.tvseries.TVSeriesDefinition;
import com.jakebellotti.model.tvseries.TVSeriesEntry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jake Bellotti
 * @since 1 Jun 2016
 */
public class SeriesDataPaneController {

	@FXML
	private Label theMovieDBURLLabel;

	@FXML
	private Label firstAirDateLabel;

	@FXML
	private Label lastAirDateLabel;

	@FXML
	private Label genresLabel;

	@FXML
	private Label episodeCountLabel;

	@FXML
	private Label seasonCountLabel;

	@FXML
	private TextArea plotTextArea;
	
    @FXML
    private AnchorPane plotAnchorPane;

	@FXML
	public void initialize() {
		updateSeries(null);
	}

	public void updateSeries(final TVSeriesEntry series) {
		if (series == null) {
			this.theMovieDBURLLabel.setText("");
			firstAirDateLabel.setText("");
			lastAirDateLabel.setText("");
			genresLabel.setText("");
			episodeCountLabel.setText("");
			seasonCountLabel.setText("");
			plotTextArea.setText("");
			return;
		}
		final Optional<TVSeriesDefinition> definition = series.getDefinition();
		if(! definition.isPresent()) {
			updateSeries(null);
			return;
		}
		series.getDefinition().ifPresent(def -> {
			// TODO add the movie db id to definitions
			// this.theMovieDBURLLabel.setText(Constants.THE_MOVIE_DB_TV_LINK +
			// def.get);
			this.theMovieDBURLLabel.setText("N/A");
			this.firstAirDateLabel.setText(def.getFirstAirDate());
			this.lastAirDateLabel.setText(def.getLastAirDate());
			this.genresLabel.setText("N/A");
			this.episodeCountLabel.setText("" + def.getEpisodeCount());
			this.seasonCountLabel.setText("" + def.getSeasonCount());
			String overview = def.getOverview().replace("\\n\\n", "").replace("\\\"", "\"");
			this.plotTextArea.setText(overview);
			return;
		});
	}

}
