package com.jakebellotti.scene.movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import com.jakebellotti.MediaManager;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * IWONDER if this works?
 * @author Jake Bellotti
 * @since 8 Jun 2016
 */
public class FindDuplicateMoviesController {

	@FXML
	private ListView<DuplicateMovieEntry> movieTitleListView;

	@FXML
	private ListView<MovieEntry> duplicateListView;

	@FXML
	private Label selectedFileLocationLabel;

	@FXML
	private Label selectedFileNameLabel;

	@FXML
	private Label selectedFileSizeLabel;

	@FXML
	private Button openMovieButton;

	private final HashMap<MovieDefinition, ArrayList<MovieEntry>> duplicateEntries = new HashMap<>();

	@FXML
	public void initialize() {
		resetInterface();
		addEventHandlers();
		
		findDuplicates();
		findOnlyDuplicates();
		addDuplicateEntries();
		
		movieTitleListView.getSelectionModel().selectFirst();
	}
	
	private final void addEventHandlers() {
		movieTitleListView.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> movieTitleListViewItemSelected());
		duplicateListView.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> duplicateListViewItemSelected());
	}
	
	private final void movieTitleListViewItemSelected() {
		final DuplicateMovieEntry selected = movieTitleListView.getSelectionModel().getSelectedItem();
		if(selected != null) {
			duplicateListView.getItems().setAll(selected.getDuplicates());	
		}
	}
	
	
	private final void duplicateListViewItemSelected() {
		
	}

	private final void resetInterface() {
		selectedFileLocationLabel.setText("");
		selectedFileNameLabel.setText("");
		selectedFileSizeLabel.setText("");
		openMovieButton.setOnMouseClicked(null);
	}

	private final void findDuplicates() {
		for (MovieEntry e : MediaManager.getMediaRepository().getLoadedMovieEntries()) {
			Optional<MovieDefinition> definition = e.getDefinition();
			definition.ifPresent(def -> addDuplicate(e, def));
		}
	}

	private final void addDuplicate(final MovieEntry entry, MovieDefinition def) {
		for(MovieDefinition currentDef: duplicateEntries.keySet()) {
			if(currentDef.getTitle().equals(def.getTitle()) && currentDef.getYear() == def.getYear()) {
				duplicateEntries.get(def).add(entry);
				return;
			}
		}
		duplicateEntries.put(def, new ArrayList<>());
	}

	/**
	 * Removes any results from the HashMap that are not duplicates.
	 */
	private final void findOnlyDuplicates() {
		for (MovieDefinition key : duplicateEntries.keySet()) {
			final ArrayList<MovieEntry> list = duplicateEntries.get(key);
			boolean remove = (list.size() < 2);
			if (remove)
				duplicateEntries.remove(key);
		}
	}

	/**
	 * Adds the duplicate entries to the ListViews on the interface.
	 */
	private final void addDuplicateEntries() {
		final ArrayList<DuplicateMovieEntry> entries = new ArrayList<>();
		for (MovieDefinition key : duplicateEntries.keySet()) {
			final MovieDefinition definition = MediaManager.getMediaRepository().getLoadedMovieDefinitions().get(key);
			final ArrayList<MovieEntry> list = duplicateEntries.get(key);
			entries.add(new DuplicateMovieEntry(definition, list));
		}
		Collections.sort(entries, (a, b) -> {
			final String str1 = a.getDefinition().getTitle().toLowerCase();
			final String str2 = b.getDefinition().getTitle().toLowerCase();
			return str1.compareTo(str2);
		});
		duplicateEntries.clear();
		duplicateListView.getItems().clear();
		movieTitleListView.getItems().setAll(entries);
	}

}
