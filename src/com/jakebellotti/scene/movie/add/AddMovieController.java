package com.jakebellotti.scene.movie.add;

import java.util.ArrayList;
import java.util.Iterator;

import com.jakebellotti.MediaManager;
import com.jakebellotti.model.FileNameCleanser;
import com.jakebellotti.model.filenamecleanser.DefaultFileNameCleanser;
import com.jakebellotti.model.movie.NewMovieEntry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class AddMovieController {

	private final AddMovieWindow addMovieWindow;

	@FXML
	private TableView<NewMovieEntry> outputDataTableView;

	@FXML
	private TableColumn<TableView<NewMovieEntry>, String> outputDataFileLocationColumn;

	@FXML
	private TableColumn<TableView<NewMovieEntry>, String> outputDataExtractedNameColumn;

	@FXML
	private Button discardEntriesButton;

	@FXML
	private Button addEntriesButton;

	@FXML
	private Label outputDataLabel;

	@FXML
	private ListView<FileNameCleanser> availableFiltersListView;

	@FXML
	private ListView<FileNameCleanser> selectedFiltersListView;

	@FXML
	private Button addOneAvailableFiltersButton;

	@FXML
	private Button addAllAvailableFiltersButton;

	@FXML
	private Button removeOneSelectedFiltersButton;

	@FXML
	private Button removeAllSelectedFiltersButton;

	@FXML
	private Button availableFiltersSelectAllButton;

	@FXML
	private Button availableFiltersDeselectAllButton;

	@FXML
	private Button selectedFiltersSelectAllButton;

	@FXML
	private Button selectedFiltersDeselectAllButton;

	public AddMovieController(AddMovieWindow addMovieWindow) {
		this.addMovieWindow = addMovieWindow;
	}

	@FXML
	public void initialize() {
		this.availableFiltersListView.getItems().addAll(this.addMovieWindow.getFilters());
		
		//Add the default cleanser
		this.selectedFiltersListView.getItems().add(new DefaultFileNameCleanser());

		// Add event handlers
		this.outputDataExtractedNameColumn
				.setCellValueFactory(new PropertyValueFactory<TableView<NewMovieEntry>, String>("movieName"));
		this.outputDataFileLocationColumn
				.setCellValueFactory(new PropertyValueFactory<TableView<NewMovieEntry>, String>("movieNameBefore"));
		this.addOneAvailableFiltersButton.setOnMouseClicked(this::addOneAvailableFiltersButtonClicked);
		this.addAllAvailableFiltersButton.setOnMouseClicked(this::addAllAvailableFiltersButtonClicked);
		
		this.removeOneSelectedFiltersButton.setOnMouseClicked(this::removeOneSelectedFiltersButtonClicked);
		this.removeAllSelectedFiltersButton.setOnMouseClicked(this::removeAllSelectedFiltersButtonClicked);
		
		this.discardEntriesButton.setOnMouseClicked(this::discardEntriesButtonClicked);
		this.addEntriesButton.setOnMouseClicked(this::addEntriesButtonClicked);

		this.availableFiltersListView.getSelectionModel().selectFirst();

		// Add data
		updateOutputData();
	}
	
	private void addEntriesButtonClicked(MouseEvent event) {
		int added = MediaManager.getDatabase().addMovieEntries(this.outputDataTableView.getItems());
		this.addMovieWindow.setAddedMovies(added);
		this.addMovieWindow.getStage().close();
	}
	
	private void discardEntriesButtonClicked(MouseEvent event) {
		this.addMovieWindow.getStage().close();
		//TODO confirm
	}

	private void addOneAvailableFiltersButtonClicked(MouseEvent event) {
		FileNameCleanser selected = this.availableFiltersListView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			this.availableFiltersListView.getItems().remove(selected);
			this.selectedFiltersListView.getItems().add(selected);
			updateOutputData();
		}
	}

	private void addAllAvailableFiltersButtonClicked(MouseEvent event) {
		Iterator<FileNameCleanser> iter = this.availableFiltersListView.getItems().iterator();
		while(iter.hasNext()) {
			FileNameCleanser current = iter.next();
			this.selectedFiltersListView.getItems().add(current);
			iter.remove();
		}
		this.selectedFiltersListView.getSelectionModel().selectLast();
		updateOutputData();
	}
	
	private void removeOneSelectedFiltersButtonClicked(MouseEvent event) {
		FileNameCleanser selected = this.selectedFiltersListView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			this.selectedFiltersListView.getItems().remove(selected);
			this.availableFiltersListView.getItems().add(selected);
			updateOutputData();
		}
	}
	
	private void removeAllSelectedFiltersButtonClicked(MouseEvent event) {
		Iterator<FileNameCleanser> iter = this.selectedFiltersListView.getItems().iterator();
		while(iter.hasNext()) {
			FileNameCleanser current = iter.next();
			this.availableFiltersListView.getItems().add(current);
			iter.remove();
		}
		this.availableFiltersListView.getSelectionModel().selectLast();
		updateOutputData();
	}

	private String cleanseStringWithFilters(String s) {
		String toReturn = new String(s);
		for (FileNameCleanser filter : this.selectedFiltersListView.getItems()) {
			toReturn = filter.cleanseFileName(toReturn);
		}
		return toReturn;
	}

	private void updateOutputData() {
		this.outputDataTableView.getItems().clear();
		ArrayList<NewMovieEntry> newMovieEntries = new ArrayList<>();
		this.addMovieWindow.getNewFiles().forEach(file -> {
			newMovieEntries.add(new NewMovieEntry(file, cleanseStringWithFilters(file.getName())));
		});
		this.outputDataTableView.getItems().addAll(newMovieEntries);
	}

	public AddMovieWindow getAddMovieWindow() {
		return addMovieWindow;
	}

}
