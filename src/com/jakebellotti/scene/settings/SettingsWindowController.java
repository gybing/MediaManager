package com.jakebellotti.scene.settings;

import com.jakebellotti.model.SettingsScreen;
import com.jakebellotti.scene.settings.impl.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * TODO alphabetically sort settings by name
 * @author Jake Bellotti
 * @date Apr 14, 2016
 */
public class SettingsWindowController {

	/**
	 * An instance of the Stage that was used to open this Scene.
	 */
	private final Stage currentStage;
	private SettingsScreen currentSettingScreen = null;

	@FXML
	private ListView<SettingsScreen> settingCategoryListView;

	@FXML
	private Label settingCategoryLabel;

	@FXML
	private Button applySettingsButton;

	@FXML
	private AnchorPane settingsWindowContentPane;

	@FXML
	private Button cancelButton;

	public SettingsWindowController(Stage currentStage) {
		this.currentStage = currentStage;
	}

	@FXML
	public void initialize() {
		addSettingsScreens();

		this.settingCategoryListView.getSelectionModel().selectedItemProperty()
				.addListener((o, oldVal, newVal) -> this.settingCategoryListViewItemChanged(oldVal, newVal));
		this.applySettingsButton.setOnMouseClicked(this::applySettingsButtonClicked);
		this.cancelButton.setOnMouseClicked(this::cancelButtonClicked);
		
		this.settingCategoryListView.getSelectionModel().selectFirst();
	}

	/**
	 * XXX This is where you add settings screens
	 */
	private final void addSettingsScreens() {
		this.settingCategoryListView.getItems().add(new FileAssociationsSettingsScreen());
		this.settingCategoryListView.getItems().add(new DataSettingsScreen());
	}

	private final void settingCategoryListViewItemChanged(SettingsScreen oldValue, SettingsScreen newValue) {
		if (this.currentSettingScreen != null) {
			if (this.currentSettingScreen.settingsWereModified()) {
				this.currentSettingScreen.saveSettings();
				//TODO alert asking if they want to save their settings
			}
		}

		if (newValue != null) {
			this.settingsWindowContentPane.getChildren().clear();
			this.settingsWindowContentPane.getChildren().add(newValue.getScene());
			this.currentSettingScreen = newValue;
			this.settingCategoryLabel.setText(newValue.getName());
		}
	}

	private void applySettingsButtonClicked(MouseEvent event) {
		if (this.currentSettingScreen != null) {
			this.currentSettingScreen.saveSettings();
			// TODO save and then alert
		}
	}

	private void cancelButtonClicked(MouseEvent event) {
		if (this.currentSettingScreen != null) {
			if (currentSettingScreen.settingsWereModified()) {
				// TODO alert to ask if they want to save settings
			}
		}
		this.currentStage.close();
	}

}
