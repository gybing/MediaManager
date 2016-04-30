package com.jakebellotti.scene.settings.impl;

import java.io.IOException;

import com.jakebellotti.MediaManager;
import com.jakebellotti.model.MediaRepository;
import com.jakebellotti.model.SettingsScreen;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * @author Jake Bellotti
 * @date Apr 19, 2016
 */
public class DataSettingsScreen extends SettingsScreen {
	
    @FXML
    private Button resetAllMovieDataButton;

    @FXML
    private Button resetAllTVSeriesButton;

    @FXML
    private Label movieDataStatusLabel;

    @FXML
    private Label tvSeriesStatusLabel;
    
    @FXML
    public void initialize() {
    	addEventHandlers();
    }
    
    /**
     * Adds the event handlers to all of the controls
     */
    private final void addEventHandlers() {
    	this.resetAllMovieDataButton.setOnMouseClicked(this::resetAllMovieDataButtonClick);
    }
    
    private void resetAllMovieDataButtonClick(MouseEvent event) {
    	this.setSettingsModified(true);
    	MediaRepository.getLoadedMovieEntries().clear();
    	MediaManager.getDatabase().query("DELETE FROM tblMovieEntry WHERE 1=1");
    	//FIXME result set is open and can't truncate because of dependencies
//    	MediaManager.getDatabase().query("TRUNCATE TABLE tblMovieEntry");
    	MainWindowFrame.getController().getCurrentSceneController().refresh();
    }

	@Override
	public void saveSettingsLogic() {
		
	}

	@Override
	public String getName() {
		return "Data";
	}

	@Override
	public AnchorPane getScene() {
		if(this.scene == null) {
			try {
				loader.setController(this);
				this.scene = loader.load(getClass().getResource("DataSettingsScreen.fxml").openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return scene;
	}

}
