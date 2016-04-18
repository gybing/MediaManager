package com.jakebellotti.scene.settings.impl;

import java.io.IOException;

import com.jakebellotti.Settings;
import com.jakebellotti.model.SettingsScreen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * @author Jake Bellotti
 * @date Apr 14, 2016
 */
public class FileAssociationsSettingsScreen extends SettingsScreen {

    @FXML
    private ListView<String> videoFileAssociationsListView;

    @FXML
    private Button videoFileAssociationsDefaultButton;

    @FXML
    private Button videoFileAssociationsAddButton;

    @FXML
    private Button videoFileAssociationsRemoveButton;

    @FXML
    private ListView<?> musicFileAssociationsListView;

    @FXML
    private Button musicFileAssociationsDefaultButton;

    @FXML
    private Button musicFileAssociationsAddButton;

    @FXML
    private Button musicFileAssociationsRemoveButton;
    
    @FXML
    public void initialize() {
    	addEventHandlers();
    	addData();
    }
    
    private final void addEventHandlers() {
    	
    }
    
    private final void addData() {
    	this.videoFileAssociationsListView.getItems().addAll(Settings.getVideoFileAssociations());
    }

	@Override
	public void saveSettingsLogic() {
	}

	@Override
	public String getName() {
		return "File Associations";
	}

	@Override
	public AnchorPane getScene() {
		if(scene == null) {
			try {
				loader.setController(this);
				scene = loader.load(FileAssociationsSettingsScreen.class.getResource("FileAssociationsSettingsScreen.fxml").openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return scene;
	}

}
