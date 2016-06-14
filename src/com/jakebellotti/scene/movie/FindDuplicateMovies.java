package com.jakebellotti.scene.movie;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jake Bellotti
 * @since 14 Jun 2016
 */
public class FindDuplicateMovies {
	
	private static FindDuplicateMoviesController controller;

	public static AnchorPane getAnchorPane() {
		try {
			controller = new FindDuplicateMoviesController();
			FXMLLoader loader = new FXMLLoader();
			loader.setController(controller);
			return loader.load(FindDuplicateMovies.class.getResource("FindDuplicateMovies.fxml").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static FindDuplicateMoviesController getController() {
		return controller;
	}

}
