package com.jakebellotti.scene.tvseries.datapane;

import java.util.Optional;

import com.jakebellotti.MediaManager;
import com.jakebellotti.model.tvseries.TVSeriesDefinition;
import com.jakebellotti.model.tvseries.TVSeriesEntry;
import com.jakebellotti.scene.main.MainWindowFrame;
import com.jakebellotti.scene.tvseries.DownloadTVSeries;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jblib.javafx.Alerts;

/**
 *
 * @author Jake Bellotti
 * @since 1 Jun 2016
 */
public class SeriesDataPaneController {
	
	private TVSeriesEntry series = null;

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
    private Button automaticallyScrapeDataButton;

    @FXML
    private Button searchForDataButton;

	@FXML
	public void initialize() {
		this.automaticallyScrapeDataButton.setOnMouseClicked(this::automaticallyScrapeDataButtonClicked);
		this.searchForDataButton.setOnMouseClicked(this::searchForDataButtonClicked);
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
		this.series = series;
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
	
	private final void searchForDataButtonClicked(MouseEvent e) {
		
	}
	
	private final void automaticallyScrapeDataButtonClicked(MouseEvent e) {
		if(this.series != null) {
			DownloadTVSeries.open(series);
			MediaManager.getTvSeriesDefinitionRetriever().scrapeData(series).ifPresent(definition -> {
				//TODO do this on a separate thread / screen 
				if(! MediaManager.getDatabase().insertTVSeriesDefinition(series, definition)) {
					Alerts.showErrorAlert("Definition could not be retrieved", "", "");
					return;
				}
				series.downloadImages();
				MainWindowFrame.getController().getCurrentSceneController().refresh();
				//TODO reselect the currently selected one
			});
		}
	}

}
