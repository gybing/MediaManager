package com.jakebellotti.scene.movie.add;

import java.io.File;
import java.util.ArrayList;

import com.jakebellotti.model.FileNameCleanser;
import com.jakebellotti.model.movie.NewMovieEntry;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class AddMovieWindow {

	private final FXMLLoader loader = new FXMLLoader();
	private final ArrayList<FileNameCleanser> filters = new ArrayList<>();
	private final ArrayList<File> newFiles = new ArrayList<>();
	private final ArrayList<NewMovieEntry> newMovieEntries = new ArrayList<>();

	private Parent root = null;
	private AddMovieController controller = null;
	private Stage stage = null;

	public AddMovieWindow(ArrayList<File> newFiles, ArrayList<FileNameCleanser> cleansers) {
		for (FileNameCleanser cleanser : cleansers)
			addFileNameCleanser(cleanser);
		for (File file : newFiles) {
			addFile(file);
		}
	}

	public void addFileNameCleanser(FileNameCleanser cleanser) {
		this.filters.add(cleanser);
	}

	public void addFile(File file) {
		this.newFiles.add(file);
	}

	public ArrayList<NewMovieEntry> showAndReturnValues(Stage currentStage) {
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(currentStage);
		stage.setScene(new Scene(root));
		stage.sizeToScene();
		stage.showAndWait();
		return newMovieEntries;
	}

	private void load() throws Exception {
		if (root == null) {
			root = loader.load(AddMovieWindow.class.getClassLoader().getResource("AddMovie.fxml").openStream());
			setController(new AddMovieController(this));
			loader.setController(root);
		}
	}
	
	public ArrayList<FileNameCleanser> getFilters() {
		return filters;
	}

	public ArrayList<File> getNewFiles() {
		return newFiles;
	}

	public ArrayList<NewMovieEntry> getNewMovieEntries() {
		return newMovieEntries;
	}

	public AddMovieController getController() {
		return controller;
	}

	public void setController(AddMovieController controller) {
		this.controller = controller;
	}

}
