package com.jakebellotti.scene.movie.add;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jakebellotti.model.FileNameCleanser;
import com.jakebellotti.model.movie.NewMovieEntry;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class AddMovieWindow {

	private final FXMLLoader loader = new FXMLLoader();
	private final List<FileNameCleanser> filters = new ArrayList<>();
	private final List<File> newFiles = new ArrayList<>();
	private final List<NewMovieEntry> newMovieEntries = new ArrayList<>();

	private AnchorPane root = null;
	private AddMovieController controller = null;
	private Stage stage = null;
	private int addedMovies = -1;

	public AddMovieWindow(List<File> newFiles) {
		this(newFiles, new ArrayList<FileNameCleanser>());
	}

	public AddMovieWindow(List<File> newFiles, List<FileNameCleanser> cleansers) {
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

	public int showAndReturnValues(Stage currentStage) {
		this.stage = new Stage();
		load();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(currentStage);
		stage.setScene(new Scene(root));
		stage.sizeToScene();
		stage.showAndWait();
		return addedMovies;
	}

	private void load() {
		if (root == null) {
			try {
				setController(new AddMovieController(this));
				loader.setController(getController());
				root = loader.load(AddMovieWindow.class.getResource("AddMovie.fxml").openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<FileNameCleanser> getFilters() {
		return filters;
	}

	public List<File> getNewFiles() {
		return newFiles;
	}

	public List<NewMovieEntry> getNewMovieEntries() {
		return newMovieEntries;
	}

	public AddMovieController getController() {
		return controller;
	}

	public void setController(AddMovieController controller) {
		this.controller = controller;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public int getAddedMovies() {
		return addedMovies;
	}

	public void setAddedMovies(int addedMovies) {
		this.addedMovies = addedMovies;
	}

}
