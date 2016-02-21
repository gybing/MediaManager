package com.jakebellotti.scene.main;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Jake Bellotti
 * @date Feb 19, 2016
 */

public class MainWindowFrame {
	
	private static final FXMLLoader loader = new FXMLLoader();
	
	public static void load(Stage stage) {
		try {
			final Parent root = loader.load(MainWindowFrame.class.getResource("MainWindowFrame.fxml").openStream());
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			stage.setResizable(true);
			stage.setTitle("Main Frame Window");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
