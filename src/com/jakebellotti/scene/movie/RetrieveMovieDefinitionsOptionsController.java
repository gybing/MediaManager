package com.jakebellotti.scene.movie;

import java.util.ArrayList;
import java.util.Optional;

import com.jakebellotti.Images;
import com.jakebellotti.MediaManager;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.NewMovieDefinition;
import com.jakebellotti.scene.loadingscreen.LoadingScreen;
import com.jakebellotti.scene.loadingscreen.LoadingTask;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * TODO check that this updates information every time it is opened or not
 * 
 * @author Jake Bellotti Date 7-May-2016
 *
 */
public class RetrieveMovieDefinitionsOptionsController {

	private final Stage stage;

	@FXML
	private Label entriesAssignedLabel;

	@FXML
	private Label entriesUnassignedLabel;

	@FXML
	private CheckBox overrideExistingDefinitionsCheckBox;

	@FXML
	private Button searchAutomaticallyButton;

	public RetrieveMovieDefinitionsOptionsController(Stage stage) {
		this.stage = stage;
	}
	
	//TODO make this custom because the old system ins't very good

	@FXML
	public void initialize() {
		int total = MediaManager.getMediaRepository().getLoadedMovieEntries().size();
		int hasAssignedCounter = 0;

		for (MovieEntry entry : MediaManager.getMediaRepository().getLoadedMovieEntries()) {
			if (entry.getDefinition().isPresent())
				hasAssignedCounter++;
		}

		this.entriesAssignedLabel.setText(
				"Currently, " + hasAssignedCounter + "/" + total + " movie entries have definitions assigned.");
		this.entriesUnassignedLabel.setText((total - hasAssignedCounter) + " movie entries still need definitions.");
		this.searchAutomaticallyButton.setOnMouseClicked(this::searchAutomaticallyButtonClicked);

		// Currently, 4800/9000 movie entries have definitions assigned.
		// 4200 movie entries still need definitions.
	}

	private final void searchAutomaticallyButtonClicked(MouseEvent event) {
		RetrieveMovieDefinitions getDefs = new RetrieveMovieDefinitions(stage, this.overrideExistingDefinitionsCheckBox.isSelected());
		getDefs.open();
	}
}
