package com.jakebellotti.scene.launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The launcher for this application, displays settings that should be changed before the program starts, if needed.
 * @author Jake Bellotti
 * @date Feb 16, 2016
 */

public class Launcher extends Application {
	
	private static final FXMLLoader launcherLoader = new FXMLLoader();
	private static Stage mainFrameStage = null;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		mainFrameStage = stage;
		final Parent root = launcherLoader.load(Launcher.class.getResource("Launcher.fxml").openStream());
		mainFrameStage.setScene(new Scene(root));
		mainFrameStage.show();
		//TODO center on screen
	}
	
	public static Stage getMainFrameStage() {
		return mainFrameStage;
	}

	public static FXMLLoader getLauncherLoader() {
		return launcherLoader;
	}

}
