package com.jakebellotti.scene.main;

import com.jakebellotti.scene.movie.MovieView;

import javafx.fxml.FXML;
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
	public void initialize() {
//		contentWindow.getChildren().add(new SubScene(PresentationView.getScene(), 1300, 615));
		contentWindow.getChildren().add(MovieView.getAnchorPane());
	}

}
