package com.jakebellotti.scene.main;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
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
			stage.setResizable(true);
			stage.sizeToScene();
			stage.setMinHeight(stage.getHeight());
			stage.setMinWidth(stage.getWidth());
			
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
	        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
			stage.setTitle("Main Frame Window");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MainWindowFrameController getController() {
		return loader.getController();
	}

}
