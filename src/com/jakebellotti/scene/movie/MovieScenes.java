package com.jakebellotti.scene.movie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Holds reference to any Scenes associated with movies.
 * @author Jake Bellotti
 * @since 16 Jun 2016
 */
public class MovieScenes {
	
	private static FilterMoviesController filterMoviesController;
	private static AnchorPane filterMoviesAnchorPane;
	private static Scene filterMoviesScene;
	
	public static final void openFilterMoviesAnchorPane() {
		try {
			if(filterMoviesAnchorPane == null) {
				filterMoviesController = new FilterMoviesController();
				FXMLLoader loader = new FXMLLoader();
				loader.setController(filterMoviesController);
				filterMoviesAnchorPane = loader.load(MovieScenes.class.getResource("FilterMovies.fxml").openStream());
			}
			
			final Stage stage = new Stage();
			if(filterMoviesController.getStage() != null)
				filterMoviesController.setStage(null);
				
			filterMoviesController.setStage(stage);
			stage.setTitle("Media Manager - Manage Movie Filters");
			
			if(filterMoviesScene == null)
				filterMoviesScene = new Scene(filterMoviesAnchorPane);
			
			stage.setScene(filterMoviesScene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static final void resetMovieFilters() {
		filterMoviesAnchorPane = null;
		filterMoviesAnchorPane = null;
		filterMoviesScene = null;
	}

}
