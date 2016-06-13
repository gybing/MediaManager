package com.jakebellotti.scene.recent;

import java.util.ArrayList;

import com.jakebellotti.MediaManager;
import com.jakebellotti.model.RecentMediaEntry;
import com.jakebellotti.scene.MediaScene;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jake Bellotti
 * @since 13 Jun 2016
 */
public class RecentMediaController implements MediaScene {

	@FXML
	private AnchorPane root;

	@FXML
	private Label movieNameLabel;

	@FXML
	private ListView<RecentMediaEntry> recentTVSeriesListView;

	@FXML
	private ListView<RecentMediaEntry> recentMoviesListView;

	@FXML
	private ListView<RecentMediaEntry> recentMusicListView;

	@FXML
	private Button openMediaButton;

	@FXML
	private ProgressBar loadingProgressBar;

	@FXML
	public void initialize() {
		// TODO start loading events
		addEventHandlers();
		openMediaButton.setDisable(true);
		openMediaButton.setText("");
		loadingProgressBar.setVisible(false);

		Thread thread = new Thread(this::retrieveData);
		thread.start();
	}
	
	private final void addEventHandlers() {
		recentMoviesListView.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> recentMoviesListViewItemSelected());
	}
	
	private final void recentMoviesListViewItemSelected() {
		final RecentMediaEntry selected = recentMoviesListView.getSelectionModel().getSelectedItem();
		if(selected != null) {
			selected.onSelect(openMediaButton);
		}
	}

	private final void retrieveData() {
		// TODO get from the database
		final ArrayList<RecentMediaEntry> movies = MediaManager.getDatabase().selectRecentMovieEntry();
		Platform.runLater(() -> {
			movies.forEach(rme -> recentMoviesListView.getItems().add(rme));
			// TODO append from the database
		});
	}

	@Override
	public void refresh() {

	}

	@Override
	public void addMenuBarItems(MenuBar menuBar) {
		menuBar.getMenus().addAll(MainWindowFrame.getWindowMenu(), MainWindowFrame.getHelpMenu());
	}

}
