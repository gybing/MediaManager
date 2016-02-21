package com.jakebellotti.scene.launcher;

import com.jakebellotti.Settings;
import com.jakebellotti.io.SettingsIO;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

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
		Launcher.getMainFrameStage().setResizable(false);
		Launcher.getMainFrameStage().sizeToScene();
		Launcher.getMainFrameStage().setTitle("Media Manager Launcher");
		SettingsIO.loadSettings();
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
		SettingsIO.saveSettings();
		MainWindowFrame.load(Launcher.getMainFrameStage());
	}

	private void exitButtonOnMouseClicked(MouseEvent event) {

	}

	private void memorySaverCheckBoxSelectionProperty(boolean newValue) {

	}

}
