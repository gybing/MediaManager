package com.jakebellotti.scene.tvseries.datapane;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * @author Jake Bellotti
 * @sice 1-May-2016
 *
 */
public class TVSeriesDataPanes {

	private static AnchorPane seriesDataPane = null;
	private static final SeriesDataPaneController seriesController = new SeriesDataPaneController();

	private static AnchorPane seasonDataPane = null;
	private static final SeasonDataPaneController seasonController = new SeasonDataPaneController();

	private static AnchorPane episodeDataPane = null;
	private static final EpisodeDataPaneController episodeController = new EpisodeDataPaneController();

	public static final AnchorPane getSeriesDataPane() {
		if (seriesDataPane == null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setController(seriesController);
				seriesDataPane = loader.load(TVSeriesDataPanes.class.getResource("SeriesDataPane.fxml").openStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return seriesDataPane;
	}

	public static final AnchorPane getSeasonDataPane() {
		if (seasonDataPane == null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setController(seasonController);
				seasonDataPane = loader.load(TVSeriesDataPanes.class.getResource("SeasonDataPane.fxml").openStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return seasonDataPane;
	}

	public static final AnchorPane getEpisodeDataPane() {
		if (episodeDataPane == null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setController(episodeController);
				episodeDataPane = loader.load(TVSeriesDataPanes.class.getResource("EpisodeDataPane.fxml").openStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return episodeDataPane;
	}

	public static SeriesDataPaneController getSeriesController() {
		return seriesController;
	}

	public static SeasonDataPaneController getSeasonController() {
		return seasonController;
	}

	public static EpisodeDataPaneController getEpisodeController() {
		return episodeController;
	}

}
