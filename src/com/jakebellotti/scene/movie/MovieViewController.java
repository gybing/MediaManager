package com.jakebellotti.scene.movie;

import java.io.File;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import com.jakebellotti.scene.movie.add.AddMovieWindow;
import com.jakebellotti.scene.movie.search.MovieSearchScreen;
import com.jakebellotti.scene.settings.SettingsWindow;
import com.jakebellotti.MediaManager;
import com.jakebellotti.Settings;
import com.jakebellotti.fx.ListViewModifier;
import com.jakebellotti.fx.impl.DetailsListViewModifier;
import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.MediaRepository;
import com.jakebellotti.model.filenamecleanser.FileNameCleanserRepository;
import com.jakebellotti.model.listfilter.AscendingAlphabeticalListOrderer;
import com.jakebellotti.model.listfilter.DescendingAlphabeticalListOrderer;
import com.jakebellotti.model.movie.MovieEntry;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * 
 * @author Jake Bellotti
 * @date Mar 20, 2016
 */

public class MovieViewController {

	@FXML
	private AnchorPane root;

	@FXML
	private ListView<MovieEntry> movieList;
	/**
	 * Allows the user to select how they want to view the movie list, for
	 * example text with a thumbnail or just text.
	 */
	@FXML
	private ComboBox<ListViewModifier<MovieEntry>> viewTypeComboBox;
	/**
	 * Where the user can type in a search query to further filter the movies.
	 */
	@FXML
	private TextField searchTextField;
	/**
	 * Resets or gets rid of all of the search filters applied to this movie
	 * list.
	 */
	@FXML
	private Button resetFiltersButton;
	/**
	 * Opens up the filter management screen so the user can apply or remove
	 * filters.
	 */
	@FXML
	private Button manageFiltersButton;
	/**
	 * Where the user can select how they want to order their movie list by.
	 */
	@FXML
	private ComboBox<ListOrderer<MovieEntry>> orderByComboBox;
	/**
	 * Moves up one in the movie list.
	 */
	@FXML
	private Button upMoviesButton;
	/**
	 * Moves down one in the movie list.
	 */
	@FXML
	private Button downMoviesButton;
	/**
	 * A picture of the movie cover.
	 */
	@FXML
	private ImageView moviePoster;
	/**
	 * The label at the top of the screen that has the movie name and release
	 * year
	 */
	@FXML
	private Label movieNameLabel;
	/**
	 * Displays all the movie information like runtime, release date, rating
	 * etc.
	 */
	@FXML
	private Label movieInfoLabel;
	/**
	 * A list view that displays all of the actors featured in this movie.
	 */
	@FXML
	private ListView<String> movieActorListView;
	/**
	 * A button the user presses to manually modify the data retrieved.
	 */
	@FXML
	private Button manuallyModifyDataButton;
	/**
	 * A button the user presses to automatically retrieve the movie definition.
	 */
	@FXML
	private Button retrieveDataButton;
	/**
	 * A text area displaying the plot of the movie.
	 */
	@FXML
	private TextArea moviePlotTextArea;
	/**
	 * A button the user presses to open the movie in their selected program.
	 */
	@FXML
	private Button openMovieButton;
	/**
	 * A label displaying the writer and director of the movie
	 */
	@FXML
	private Label movieAuthorLabel;
	/**
	 * Displays how
	 */
	@FXML
	private Label automatedDataLabel;

	@FXML
	private Button automatedDataButton;

	@FXML
	private Label fileInfoLabel;

	@FXML
	public void initialize() {
		// File file = new File("./data/testimg.jpg");
		// Image image = new Image(file.toURI().toString());
		// moviePoster.setImage(image);
		this.viewTypeComboBox.getItems().add(new DetailsListViewModifier());

		this.orderByComboBox.getItems().add(new AscendingAlphabeticalListOrderer());
		this.orderByComboBox.getItems().add(new DescendingAlphabeticalListOrderer());

		setupSearchTextField();

		// add event handlers
		this.movieList.getSelectionModel().selectedItemProperty()
				.addListener((o, oldVal, newVal) -> movieListOnChangeItem(newVal));
		this.orderByComboBox.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> refreshMovieList(true));
		
		//XXX this is a test
		this.automatedDataButton.setOnMouseClicked(e -> new MovieSearchScreen().load(MediaManager.getMainFrameStage()));

		// Add data
		refreshMovieList(false);

		// Now we have loaded all data, display
		this.movieList.getSelectionModel().selectFirst();
		this.orderByComboBox.getSelectionModel().selectFirst();
		this.viewTypeComboBox.getSelectionModel().selectFirst();
	}

	/**
	 * 
	 * @param reorder
	 *            Whether or not the movie list should be sorted again.
	 */
	private final void refreshMovieList(boolean reorder) {
		this.movieList.getItems().addAll(MediaRepository.getDisplayedMovieEntries());
		if (reorder) {
			ListOrderer<MovieEntry> currentOrderer = this.orderByComboBox.getSelectionModel().getSelectedItem();
			if (currentOrderer != null) {
				this.movieList.getItems().clear();
				this.movieList.getItems().addAll(currentOrderer.order(MediaRepository.getDisplayedMovieEntries()));
			}
		}
	}

	private void movieListOnChangeItem(MovieEntry newEntry) {
		//TODO do similar error checking code on others
		//TODO finish updating
		if (newEntry == null)
			return;
		//Update regardless of there being a definition
		this.moviePlotTextArea.clear();
		this.movieActorListView.getItems().clear();
		this.movieNameLabel.setText(newEntry.toString());
		StringBuilder fileInfo = new StringBuilder();
		fileInfo.append(newEntry.getFile().getName() + " | ");
		
		final String fileName = newEntry.getFile().getName();
		int extensionStart = fileName.lastIndexOf(".");
		
		String extension = (extensionStart > 0) ? fileName.substring(extensionStart): "movie";
		fileInfo.append(extension.replace(".", "").toUpperCase() + " file | ");
		fileInfo.append(newEntry.getFile().length() / (1024 * 1024) + "mb");
		this.fileInfoLabel.setText(fileInfo.toString());
		
		//Update if there is a definition
		newEntry.getDefinition().ifPresent(def -> {
			File imageFile = new File(
					"./data/img/movie/img_" + newEntry.getDefinition().get().getDatabaseID() + ".jpg");
			Image image = new Image(imageFile.toURI().toString());
			this.moviePoster.setImage(image);
		});
		
		//Update if there is not a definition
		if (!newEntry.getDefinition().isPresent()) {
			// Update a blank
			this.movieInfoLabel.setText("Rating  |  Runtime  |  Genre  |  Release Date");
			this.movieAuthorLabel.setText("Director: | Writer:");
			this.moviePoster.setImage(null);
			this.automatedDataLabel.setText("No data has been selected yet");
		}
	}

	public void addMenuBarItems(MenuBar menuBar) {
		//TODO redo this and remove redundant code
		Menu fileMenu = new Menu("File");
		MenuItem indexFile = new MenuItem("Index files");
		MenuItem indexFolder = new MenuItem("Index a folder");
		MenuItem settings = new MenuItem("Settings");
		MenuItem closeItem = new MenuItem("Close");

		// Add event listeners
		indexFile.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose movie file");

			String[] fileExtensionOptions = new String[Settings.getVideoFileAssociations().length];
			for (int index = 0; index < Settings.getVideoFileAssociations().length; index++) {
				fileExtensionOptions[index] = "*" + Settings.getVideoFileAssociations()[index];
			}
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Video", fileExtensionOptions));
			for (String ext : Settings.getVideoFileAssociations()) {
				fileChooser.getExtensionFilters()
						.add(new FileChooser.ExtensionFilter(ext.toUpperCase() + " files (*" + ext + ")", "*" + ext));
			}

			final List<File> selectedFiles = fileChooser.showOpenMultipleDialog(MediaManager.getMainFrameStage());
			if (selectedFiles != null) {
				AddMovieWindow addMovie = new AddMovieWindow(selectedFiles, FileNameCleanserRepository.getCleansers());
				int added = addMovie.showAndReturnValues(MediaManager.getMainFrameStage());
				if (added > 0) {
					this.refreshMovieList(true);
				}
			}
		});
		indexFolder.setOnAction(event -> {

		});
		settings.setOnAction(event -> SettingsWindow.open(MediaManager.getMainFrameStage()));
		closeItem.setOnAction(event -> Platform.exit());
		fileMenu.getItems().addAll(indexFile, indexFolder, settings, closeItem);

		menuBar.getMenus().add(fileMenu);
	}

	private void setupSearchTextField() {
		this.root.getChildren().remove(this.searchTextField);

		final TextField newSearchTextField = TextFields.createClearableTextField();
		newSearchTextField.setLayoutX(this.searchTextField.getLayoutX());
		newSearchTextField.setLayoutY(this.searchTextField.getLayoutY());
		newSearchTextField.setPrefWidth(this.searchTextField.getPrefWidth());
		newSearchTextField.setPromptText("Search");

		this.searchTextField = newSearchTextField;
		this.root.getChildren().add(this.searchTextField);
	}

}
