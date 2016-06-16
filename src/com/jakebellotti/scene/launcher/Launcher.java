package com.jakebellotti.scene.launcher;

import com.jakebellotti.MediaManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

/**
 * The launcher for this application, displays settings that should be changed before the program starts, if needed.
 * @author Jake Bellotti
 * @date Feb 16, 2016
 */

public class Launcher extends Application {
	
	private static final FXMLLoader launcherLoader = new FXMLLoader();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		MediaManager.setMainFrameStage(stage);
		final Parent root = launcherLoader.load(Launcher.class.getResource("Launcher.fxml").openStream());
		launcherLoader.setController(MediaManager.getMaincontroller());
		
		
		MediaManager.getMainFrameStage().setScene(new Scene(root, 500, 500, false, SceneAntialiasing.BALANCED));
		MediaManager.getMainFrameStage().show();
	}

	public static FXMLLoader getLauncherLoader() {
		return launcherLoader;
	}

}
