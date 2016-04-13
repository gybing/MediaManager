package com.jakebellotti.scene.movie;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * @author Jake Bellotti
 * @date Feb 24, 2016
 */

public class MovieView {
	
	private static AnchorPane scene;
	private static final FXMLLoader loader = new FXMLLoader();
	
	public static AnchorPane getAnchorPane() {
		if(scene == null) {
			try {
				scene = loader.load(MovieView.class.getResource("MovieView.fxml").openStream());
				return scene;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
