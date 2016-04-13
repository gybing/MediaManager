package com.jakebellotti.scene.launcher;

import com.jakebellotti.MediaManager;
import com.jakebellotti.Settings;
import com.jakebellotti.io.SettingsIO;
import com.jakebellotti.scene.loadingscreen.LoadingScreen;
import com.jakebellotti.scene.loadingscreen.LoadingTask;
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
		MediaManager.connectDatabase();
		MediaManager.getMainFrameStage().setResizable(false);
		MediaManager.getMainFrameStage().sizeToScene();
		MediaManager.getMainFrameStage().setTitle("Media Manager Launcher");
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
		MainWindowFrame.load(MediaManager.getMainFrameStage());
	}

	private void exitButtonOnMouseClicked(MouseEvent event) {
//		System.exit(0);
		LoadingScreen screen = new LoadingScreen();
		screen.addTask(new LoadingTask(){

			@Override
			public String taskDescription() {
				return "Doing task 1";
			}

			@Override
			public String getFinishText() {
				return "Finished task 1";
			}

			@Override
			public boolean canCancel() {
				return false;
			}

			@Override
			public void onCancel() {
			}

			@Override
			public void doTask() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}});
		
		screen.addTask(new LoadingTask(){

			@Override
			public String taskDescription() {
				return "Doing task 2";
			}

			@Override
			public String getFinishText() {
				return "Finished task 2";
			}

			@Override
			public boolean canCancel() {
				return false;
			}

			@Override
			public void onCancel() {
			}

			@Override
			public void doTask() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}});
		
		screen.addTask(new LoadingTask(){

			@Override
			public String taskDescription() {
				return "Doing task 3";
			}

			@Override
			public String getFinishText() {
				return "Finished task 3";
			}

			@Override
			public boolean canCancel() {
				return false;
			}

			@Override
			public void onCancel() {
			}

			@Override
			public void doTask() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}});
		
		screen.open(MediaManager.getMainFrameStage());
	}

	private void memorySaverCheckBoxSelectionProperty(boolean newValue) {

	}

}
