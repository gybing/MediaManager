package com.jakebellotti.scene.movie.add;

import com.jakebellotti.model.FileNameCleanser;
import com.jakebellotti.model.movie.NewMovieEntry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
		System.out.println("Attempting to add: "+ addMovieWindow.getNewFiles().size() + " new movie entries");
	}

	public AddMovieWindow getAddMovieWindow() {
		return addMovieWindow;
	}

}
