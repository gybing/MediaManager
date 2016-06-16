package com.jakebellotti.scene.movie;

import java.util.ArrayList;

import com.jakebellotti.MediaManager;
import com.jakebellotti.model.SelectedGenreWrapper;
import com.jakebellotti.model.movie.filter.MovieGenreFilter;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import jblib.javafx.JavaFXUtils;

/**
 *
 * @author Jake Bellotti
 * @since 16 Jun 2016
 */
public class FilterMoviesController {
	
	private Stage stage;
	
    @FXML
    private ListView<SelectedGenreWrapper> genresListView;

    @FXML
    private Button saveButton;
    
    @FXML
    private Button selectAllButton;

    @FXML
    private Button deselectAllButton;

    @FXML
    public void initialize() {
    	System.out.println("init");
    	addEventHandlers();
    	setupGenresListView();
    	
    	addGenres();
    }
    
    private final void addEventHandlers() {
    	saveButton.setOnMouseClicked(this::saveButtonMouseClicked);
    	deselectAllButton.setOnMouseClicked(this::deselectAllButtonClicked);
    	selectAllButton.setOnMouseClicked(this::selectAllButtonClicked);
    }
    
    private final void selectAllButtonClicked(MouseEvent e) {
    	genresListView.getItems().forEach(item -> {
    		if(!item.isSelected())
    			item.getSelection().setSelected(true);
    	});
    }
    
    private final void deselectAllButtonClicked(MouseEvent e) {
    	genresListView.getItems().forEach(item -> {
    		if(item.isSelected())
    			item.getSelection().setSelected(false);
    	});
    }
    
    private final void saveButtonMouseClicked(MouseEvent e) {
    	updateListFilters();
    	MainWindowFrame.getController().getCurrentSceneController().refresh();
    	stage.close();
    }
    
    private final void updateListFilters() {
    	MediaManager.getMediaRepository().getMovieEntryFilters().clear();
    	
    	updateGenresListFilter();
    }
    
    private final void updateGenresListFilter() {
    	int shownGenres = 0;
    	for(SelectedGenreWrapper wrapper: genresListView.getItems()) {
    		if(wrapper.isSelected())
    			shownGenres++;
    	}
    	//We actually need to filter genres because not all are selected
    	if(shownGenres != genresListView.getItems().size()) {
    		ArrayList<String> selectedGenres = new ArrayList<>();
    		
    		for(SelectedGenreWrapper wrapper: genresListView.getItems()) {
    			if(wrapper.isSelected()) 
    				selectedGenres.add(wrapper.getGenre());
    		}
        	MediaManager.getMediaRepository().addMovieEntryFilter(new MovieGenreFilter(selectedGenres));
    	}
    }
    
    private final void setupGenresListView() {
    	JavaFXUtils.setListViewCellFactory(genresListView, (cell, item, b) -> {
    		HBox box = new HBox();
    		box.getChildren().add(item.getSelection());
    		box.getChildren().add(new Label(item.getGenre()));
    		cell.setGraphic(box);
    	});
    }
    
    private void addGenres() {
    	genresListView.getItems().clear();
    	MediaManager.getDatabase().getUniqueMovieGenres().forEach(genre -> genresListView.getItems().add(new SelectedGenreWrapper(genre)));
    }
    
    public void setStage(Stage s) {
    	this.stage = s;
    }
    
    public Stage getStage() {
    	return stage;
    }

}
