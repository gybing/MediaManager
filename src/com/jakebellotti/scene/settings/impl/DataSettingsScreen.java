package com.jakebellotti.scene.settings.impl;

import java.io.IOException;

import com.jakebellotti.MediaManager;
import com.jakebellotti.model.SettingsScreen;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jblib.javafx.Alerts;

/**
 *
 * @author Jake Bellotti
 * @date Apr 19, 2016
 */
public class DataSettingsScreen extends SettingsScreen {

	@FXML
	private Button resetAllMovieDataButton;

	@FXML
	private Button resetAllTVSeriesButton;

	@FXML
	private Button resetAllMovieDefinitionButton;

	@FXML
	private Label movieDataStatusLabel;

	@FXML
	private Label tvSeriesStatusLabel;

	@FXML
	private Label movieDefinitionDataStatusLabel;

	@FXML
	public void initialize() {
		addEventHandlers();
		resetInfoLabels();
	}

	/**
	 * Reset/updates the info labels that tell the user how many entries they
	 * have.
	 */
	private final void resetInfoLabels() {
		final int movieEntries = MediaManager.getMediaRepository().getLoadedMovieEntries().size();
		this.movieDataStatusLabel
				.setText("Currently " + movieEntries + " movie " + (movieEntries == 1 ? "entry" : "entries"));
		
		final int movieDefinitions = MediaManager.getMediaRepository().getLoadedMovieDefinitions().size();
		this.movieDefinitionDataStatusLabel.setText(
				"Currently " + movieDefinitions + " movie " + (movieDefinitions == 1 ? "definition" : "definitions"));
		
		final int seriesEntries = MediaManager.getMediaRepository().getLoadedTVSeries().size();
		this.tvSeriesStatusLabel.setText("Currently " + seriesEntries + " tv series");
	}

	/**
	 * Adds the event handlers to all of the controls
	 */
	private final void addEventHandlers() {
		this.resetAllMovieDataButton.setOnMouseClicked(this::resetAllMovieDataButtonClick);
		this.resetAllMovieDefinitionButton.setOnMouseClicked(this::resetAllMovieDefinitionButtonClicked);
		this.resetAllTVSeriesButton.setOnMouseClicked(this::resetAllTVSeriesButtonClicked);
	}
	
	private final void resetAllTVSeriesButtonClicked(MouseEvent e) {
		
	}
	
	private final void resetAllMovieDefinitionButtonClicked(MouseEvent e) {
		if(MediaManager.getMediaRepository().getLoadedMovieEntries().size() > 0) {
			Alerts.showInformationAlert("Can not reset movie definitions", "Movie entries are linked with definitions", "Please reset all movie entries first.");
			return;
		}
		this.setSettingsModified(true);
		MediaManager.getMediaRepository().getLoadedMovieDefinitions().clear();
		MediaManager.getDatabase().query("DELETE FROM tblMovieDefinition WHERE 1=1");
		MainWindowFrame.getController().getCurrentSceneController().refresh();
		resetInfoLabels();
	}

	private void resetAllMovieDataButtonClick(MouseEvent event) {
		this.setSettingsModified(true);
		MediaManager.getMediaRepository().getLoadedMovieEntries().clear();
		MediaManager.getDatabase().query("DELETE FROM tblMovieEntry WHERE 1=1");
		// FIXME TRUNCATE TABLE is not permitted on 'TBLMOVIEENTRY' because
		// unique/primary key constraints on this table are referenced by
		// enabled foreign key constraints from other tables.
		// MediaManager.getDatabase().query("TRUNCATE TABLE tblMovieEntry");
		// TODO make sure to reset the scene if the current selected valus is
		// null
		MainWindowFrame.getController().getCurrentSceneController().refresh();
		resetInfoLabels();
	}

	@Override
	public void saveSettingsLogic() {

	}

	@Override
	public String getName() {
		return "Data";
	}

	@Override
	public AnchorPane getScene() {
		if (this.scene == null) {
			try {
				loader.setController(this);
				this.scene = loader.load(getClass().getResource("DataSettingsScreen.fxml").openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return scene;
	}

}
