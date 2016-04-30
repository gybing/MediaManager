package com.jakebellotti.scene.main;

import com.jakebellotti.scene.MediaScene;
import com.jakebellotti.scene.movie.MovieView;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
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
		if (true) {
			contentWindow.getChildren().clear();
			contentWindow.getChildren().add(MovieView.getAnchorPane());
			this.menuBar.getMenus().clear();
			MovieView.getController().addMenuBarItems(menuBar);
			this.setCurrentSceneController(MovieView.getController());
		}

	}

	public MediaScene getCurrentSceneController() {
		return currentSceneController;
	}

	public void setCurrentSceneController(MediaScene currentSceneController) {
		this.currentSceneController = currentSceneController;
	}

}
