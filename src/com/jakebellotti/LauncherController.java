package com.jakebellotti;

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
    	SettingsIO.loadSettings();
    	addEventHandlers();
    }
    
    /**
     * Adds the appropriate event handlers to each of the controls.
     */
    private void addEventHandlers() {
    	launchButton.setOnMouseClicked(this::launchButtonOnMouseClicked);
    	exitButton.setOnMouseClicked(this::exitButtonOnMouseClicked);
    }
    
    private void launchButtonOnMouseClicked(MouseEvent event) {
    	
    }
    
    private void exitButtonOnMouseClicked(MouseEvent event) {
    	
    }

}
