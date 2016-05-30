package com.jakebellotti.scene.main;

import com.jakebellotti.scene.MediaScene;
import com.jakebellotti.scene.movie.MovieView;
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
		// TODO determine the screen to show
		setAsMovieView();
	}

	public void setAsMovieView() {
		setAs(MovieView.getAnchorPane(), MovieView.getController());
	}

	public void setAsTVSeriesView() {
		setAs(TVSeriesView.getAnchorPane(), TVSeriesView.getController());
	}
	
	private final void setAs(final AnchorPane pane, final MediaScene scene) {
		contentWindow.getChildren().clear();
		contentWindow.getChildren().add(pane);
		this.menuBar.getMenus().clear();
		scene.addMenuBarItems(menuBar);
		this.setCurrentSceneController(scene);
	}

	public MediaScene getCurrentSceneController() {
		return currentSceneController;
	}

	public void setCurrentSceneController(MediaScene currentSceneController) {
		this.currentSceneController = currentSceneController;
	}

}
