package com.jakebellotti.scene.settings;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Jake Bellotti
 * @date Apr 14, 2016
 */
public class SettingsWindow {

	public static void open(Stage currentStage) {
		final Stage newStage = new Stage();
		final FXMLLoader loader = new FXMLLoader();
		try {
			loader.setController(new SettingsWindowController(newStage));
			Parent root = loader.load(SettingsWindow.class.getResource("SettingsWindow.fxml").openStream());
			newStage.setScene(new Scene(root));
			newStage.sizeToScene();
			newStage.initOwner(currentStage);
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.setTitle("Media Manager - Settings");
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
