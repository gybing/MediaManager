package com.jakebellotti.scene.movie;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Jake Bellotti Date 7-May-2016
 *
 */
public class RetrieveMovieDefinitionsOptions {

	private static Stage stage;

	public static void open(Stage currentStage) {
		if (stage == null) {
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(currentStage);
		}	
		stage.setTitle("Downloading movie definitions");
		stage.setScene(getScene(stage));
		stage.show();
	}

	private static Scene getScene(Stage stage) {
		try {
			final FXMLLoader loader = new FXMLLoader();
			loader.setController(new RetrieveMovieDefinitionsOptionsController(stage));
			Parent root = loader
					.load(RetrieveMovieDefinitionsOptions.class.getResource("RetrieveMovieDefinitionsOptions.fxml").openStream());
			return new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
