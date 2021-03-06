package com.jakebellotti.scene.main;

import com.jakebellotti.MediaManager;
import com.jakebellotti.scene.MediaScene;
import com.jakebellotti.scene.movie.MovieView;
import com.jakebellotti.scene.recent.RecentMedia;
import com.jakebellotti.scene.tvseries.TVSeriesView;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * 
 * @author Jake Bellotti
 * @date Feb 19, 2016
 */

public class MainWindowFrameController {

	/**
	 * The StackPane where all the content goes
	 */
	@FXML
	private StackPane contentWindow;

	@FXML
	private StackPane menuBarPane;

	@FXML
	private MenuBar menuBar;

	private MediaScene currentSceneController = null;

	@FXML
	public void initialize() {
		// TODO determine the screen to show, maybe recent media?
		setAsMovieView();
	}
	
	public void setAsRecentMedia() {
		MediaManager.getMainFrameStage().setTitle("Media Manager - Recent Media");
		setAs(RecentMedia.getAnchorPane(), RecentMedia.getController());
	}

	public void setAsMovieView() {
		MediaManager.getMainFrameStage().setTitle("Media Manager - Movies");
		setAs(MovieView.getAnchorPane(), MovieView.getController());
	}

	public void setAsTVSeriesView() {
		MediaManager.getMainFrameStage().setTitle("Media Manager - TV Series");
		setAs(TVSeriesView.getAnchorPane(), TVSeriesView.getController());
	}
	
	private final void setAs(final AnchorPane pane, final MediaScene scene) {
		contentWindow.getChildren().clear();
		contentWindow.getChildren().add(pane);
		menuBar.getMenus().clear();
		scene.addMenuBarItems(menuBar);
		setCurrentSceneController(scene);
	}

	public MediaScene getCurrentSceneController() {
		return currentSceneController;
	}

	public void setCurrentSceneController(MediaScene currentSceneController) {
		this.currentSceneController = currentSceneController;
	}

}
