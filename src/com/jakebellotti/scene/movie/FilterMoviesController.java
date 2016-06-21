package com.jakebellotti.scene.movie;

import java.util.ArrayList;

import com.jakebellotti.MediaManager;
import com.jakebellotti.model.SelectedActorWrapper;
import com.jakebellotti.model.SelectedGenreWrapper;
import com.jakebellotti.model.movie.filter.MovieActorFilter;
import com.jakebellotti.model.movie.filter.MovieGenreFilter;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

	private final ArrayList<SelectedActorWrapper> actors = new ArrayList<>();

	@FXML
	private ListView<SelectedGenreWrapper> genresListView;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Button selectAllGenresButton;

	@FXML
	private Button deselectAllGenresButton;

	@FXML
	private ListView<SelectedActorWrapper> actorsListView;

	@FXML
	private Button selectAllActorsButton;

	@FXML
	private Button deselectAllActorsButton;

	@FXML
	private TextField searchActorsTextField;

	@FXML
	public void initialize() {
		addEventHandlers();
		setupGenresListView();
		setupActorsListView();

		addGenres();
		addActors();
	}

	private final void addEventHandlers() {
		saveButton.setOnMouseClicked(this::saveButtonMouseClicked);
		deselectAllGenresButton.setOnMouseClicked(this::deselectAllGenresButtonClicked);
		selectAllGenresButton.setOnMouseClicked(this::selectAllGenresButtonClicked);
		selectAllActorsButton.setOnMouseClicked(this::selectAllActorsButtonClicked);
		deselectAllActorsButton.setOnMouseClicked(this::deselectAllActorsButtonClicked);
		cancelButton.setOnMouseClicked(this::cancelButtonMouseClicked);
		searchActorsTextField.textProperty().addListener(e -> searchActorsTextFieldTextChanged());
	}
	
	private final void searchActorsTextFieldTextChanged() {
		final String search = searchActorsTextField.getText() == null ? "": searchActorsTextField.getText().trim();
		if(search.length() < 1) {
			actorsListView.getItems().setAll(actors);
			return;
		}
		
		actorsListView.getItems().clear();
		actors.forEach(actor -> {
			if(actor.getActor().toLowerCase().contains(search.toLowerCase()))
				actorsListView.getItems().add(actor);
		});
	}

	private final void selectAllActorsButtonClicked(MouseEvent e) {
		actors.forEach(item -> {
			if (!item.isSelected())
				item.getSelection().setSelected(true);
		});
	}

	private final void deselectAllActorsButtonClicked(MouseEvent e) {
		actors.forEach(item -> {
			if (item.isSelected())
				item.getSelection().setSelected(false);
		});
	}

	private final void cancelButtonMouseClicked(MouseEvent e) {
		stage.close();
	}

	private final void selectAllGenresButtonClicked(MouseEvent e) {
		genresListView.getItems().forEach(item -> {
			if (!item.isSelected())
				item.getSelection().setSelected(true);
		});
	}

	private final void deselectAllGenresButtonClicked(MouseEvent e) {
		genresListView.getItems().forEach(item -> {
			if (item.isSelected())
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
		updateActorsListFilter();
	}

	private final void updateActorsListFilter() {
		int shownGenres = 0;
		for (SelectedActorWrapper wrapper : actorsListView.getItems()) {
			if (wrapper.isSelected())
				shownGenres++;
		}
		// We actually need to filter actors because not all are selected
		if (shownGenres != actorsListView.getItems().size()) {
			ArrayList<String> selectedActors = new ArrayList<>();

			for (SelectedActorWrapper wrapper : actorsListView.getItems()) {
				if (wrapper.isSelected())
					selectedActors.add(wrapper.getActor());
			}
			MediaManager.getMediaRepository().addMovieEntryFilter(new MovieActorFilter(selectedActors));
		}
	}

	private final void updateGenresListFilter() {
		int shownGenres = 0;
		for (SelectedGenreWrapper wrapper : genresListView.getItems()) {
			if (wrapper.isSelected())
				shownGenres++;
		}
		// We actually need to filter genres because not all are selected
		if (shownGenres != genresListView.getItems().size()) {
			ArrayList<String> selectedGenres = new ArrayList<>();

			for (SelectedGenreWrapper wrapper : genresListView.getItems()) {
				if (wrapper.isSelected())
					selectedGenres.add(wrapper.getGenre());
			}
			MediaManager.getMediaRepository().addMovieEntryFilter(new MovieGenreFilter(selectedGenres));
		}
	}

	private final void setupActorsListView() {
		JavaFXUtils.setListViewCellFactory(actorsListView, (cell, item, b) -> {
			HBox box = new HBox();
			box.getChildren().add(item.getSelection());
			box.getChildren().add(new Label(item.getActor()));
			cell.setGraphic(box);
		});
	}

	/**
	 * Changes the cell factory of the genres list view to allow selection of
	 * genres with a checkbox.
	 */
	private final void setupGenresListView() {
		JavaFXUtils.setListViewCellFactory(genresListView, (cell, item, b) -> {
			HBox box = new HBox();
			box.getChildren().add(item.getSelection());
			box.getChildren().add(new Label(item.getGenre()));
			cell.setGraphic(box);
		});
	}

	public void addActors() {
		actorsListView.getItems().clear();
		actors.clear();
		MediaManager.getDatabase().getUniqueMovieActors().forEach(actor -> actors.add(new SelectedActorWrapper(actor)));
		actorsListView.getItems().setAll(actors);
	}

	/**
	 * Clears the genre list view and adds all unique genres that were selected
	 * from the database.
	 */
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
