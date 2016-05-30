package com.jakebellotti.scene.movie.add;

import java.io.File;
import java.util.Optional;

import com.jakebellotti.MediaManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jblib.javafx.Alerts;

/**
 * 
 * @author Jake Bellotti Date: 10-May-2016
 *
 */
public class AddDirectoryController {

	private MovieDirectoryEntry toReturn = null;
	private final Stage stage;

	@FXML
	private TextField searchTextField;

	@FXML
	private Button browseButton;

	@FXML
	private Button indexDirectoryButton;

	@FXML
	private Label validationLabel;

	@FXML
	private CheckBox scanDirectoryCheckBox;

	@FXML
	private CheckBox removableMediaCheckBox;
	
    @FXML
    private CheckBox scanSubdirectoriesCheckBox;

	public AddDirectoryController(final Stage stage) {
		this.stage = stage;
	}

	@FXML
	public void initialize() {
		stage.setTitle("Add A Directory");
		this.validationLabel.setVisible(false);

		this.browseButton.setOnMouseClicked(this::browseButtonClicked);
		this.indexDirectoryButton.setOnMouseClicked(this::indexDirectoryButtonClicked);
		this.searchTextField.textProperty().addListener(e -> validateDirectory());
	}

	private final boolean validateDirectory() {
		final String directory = this.searchTextField.getText().trim();
		final File directoryFile = new File(directory);
		if (directory == null || directory.length() < 1 || (!directoryFile.exists())
				|| (!directoryFile.isDirectory())) {
			this.validationLabel.setVisible(true);
			this.validationLabel.setText("Directory is not valid!");
			this.validationLabel.setTextFill(Color.RED);
			return false;
		} else {
			this.validationLabel.setVisible(true);
			this.validationLabel.setText("Directory is valid.");
			this.validationLabel.setTextFill(Color.GREEN);
			return true;
		}
	}

	private final void browseButtonClicked(final MouseEvent e) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(stage);
		if (selectedDirectory != null) {
			this.searchTextField.setText(selectedDirectory.getAbsolutePath());
		}
	}

	private final void indexDirectoryButtonClicked(final MouseEvent e) {
		if (!validateDirectory()) {
			Alerts.showErrorAlert("Invalid directory", "Entered directory is not valid",
					"Please select a valid directory before continuing.");
			return;
		}
		this.toReturn = new MovieDirectoryEntry(new File(searchTextField.getText().trim()),
				scanDirectoryCheckBox.isSelected(), removableMediaCheckBox.isSelected(), scanSubdirectoriesCheckBox.isSelected());
		stage.close();
	}

	public static final Optional<MovieDirectoryEntry> getDirectory() {
		try {
			final Stage newStage = new Stage();
			final AddDirectoryController controller = new AddDirectoryController(newStage);
			final FXMLLoader loader = new FXMLLoader();
			loader.setController(controller);
			final Parent root = loader.load(AddDirectoryController.class.getResource("AddDirectory.fxml").openStream());
			newStage.initOwner(MediaManager.getMainFrameStage());
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.setScene(new Scene(root));
			newStage.sizeToScene();
			newStage.showAndWait();
			return Optional.ofNullable(controller.getToReturn());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public MovieDirectoryEntry getToReturn() {
		return toReturn;
	}

	public void setToReturn(MovieDirectoryEntry toReturn) {
		this.toReturn = toReturn;
	}

	public Stage getStage() {
		return stage;
	}

}
