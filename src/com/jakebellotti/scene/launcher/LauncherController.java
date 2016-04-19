package com.jakebellotti.scene.launcher;

import com.jakebellotti.MediaManager;
import com.jakebellotti.Settings;
import com.jakebellotti.scene.launcher.loadingtask.*;
import com.jakebellotti.scene.loadingscreen.LoadingScreen;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Jake Bellotti
 * @date Feb 17, 2016
 */

public class LauncherController {

	@FXML
	private CheckBox memorySaverModeCheckBox;

	@FXML
	private Button launchButton;

	@FXML
	private Button exitButton;

	@FXML
	public void initialize() {
		MediaManager.connectDatabase();
		MediaManager.getMainFrameStage().setResizable(false);
		MediaManager.getMainFrameStage().sizeToScene();
		MediaManager.getMainFrameStage().setTitle("Media Manager Launcher");
		applySettingsToScene();
		addEventHandlers();
	}

	/**
	 * Applys the current settings onto the scene (e.g. tick corresponding check
	 * boxes for controls that modify that setting).
	 */
	private void applySettingsToScene() {
		this.memorySaverModeCheckBox.setSelected(Settings.isMemorySaverMode());
	}

	/**
	 * Adds the appropriate event handlers to each of the controls.
	 */
	private void addEventHandlers() {
		memorySaverModeCheckBox.selectedProperty()
				.addListener((o, oldVal, newVal) -> memorySaverCheckBoxSelectionProperty(newVal));
		launchButton.setOnMouseClicked(this::launchButtonOnMouseClicked);
		exitButton.setOnMouseClicked(this::exitButtonOnMouseClicked);
	}

	private void launchButtonOnMouseClicked(MouseEvent event) {
		final LoadingScreen loadingScreen = new LoadingScreen() {
			@Override
			public void onFinish(Stage stage) {
				stage.close();
				MainWindowFrame.load(MediaManager.getMainFrameStage());
			}
		};
		loadingScreen.addTask(new SettingsIOSaveLoadingTask());
		loadingScreen.addTask(new DeserializeIndexedMediaLoadingTask());
		loadingScreen.addTask(new DeserializeMediaDataLoadingTask());
		loadingScreen.addTask(new FinishingDelayLoadingTask());
		
		loadingScreen.open(MediaManager.getMainFrameStage());
	}

	private void exitButtonOnMouseClicked(MouseEvent event) {
		System.exit(0);
	}

	private void memorySaverCheckBoxSelectionProperty(boolean newValue) {

	}

}
