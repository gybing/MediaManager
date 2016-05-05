package com.jakebellotti.scene.movie;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.textfield.TextFields;

import com.jakebellotti.scene.MediaScene;
import com.jakebellotti.scene.movie.add.AddMovieWindow;
import com.jakebellotti.scene.movie.search.MovieSearchScreen;
import com.jakebellotti.scene.settings.SettingsWindow;
import com.jakebellotti.DataConstants;
import com.jakebellotti.MediaManager;
import com.jakebellotti.Settings;
import com.jakebellotti.fx.ListViewModifier;
import com.jakebellotti.fx.impl.DetailsListViewModifier;
import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.filenamecleanser.FileNameCleanserRepository;
import com.jakebellotti.model.listfilter.AscendingAlphabeticalListOrderer;
import com.jakebellotti.model.listfilter.DescendingAlphabeticalListOrderer;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.NewMovieDefinition;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import jblib.javafx.JavaFXUtils;

/**
 * TODO Capitalize the retrieved name when it is extracted from the file name
 * 
 * @author Jake Bellotti
 * @date Mar 20, 2016
 */

public class MovieViewController implements MediaScene {

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
		this.viewTypeComboBox.getItems().add(new DetailsListViewModifier());

		this.orderByComboBox.getItems().add(new AscendingAlphabeticalListOrderer());
		this.orderByComboBox.getItems().add(new DescendingAlphabeticalListOrderer());
		
		JavaFXUtils.setListViewCellFactory(this.movieList, (cell, movieEntry, c) -> {
			HBox box = new HBox();
			Label text = new Label(movieEntry.toString());
			if(! movieEntry.getDefinition().isPresent()) {
				text.setStyle("-fx-text-fill: red");
			}
			
			ContextMenu menu = new ContextMenu();
			MenuItem delete = new MenuItem();
			delete.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
			menu.getItems().add(delete);
			
			cell.setContextMenu(menu);
			
			box.getChildren().add(text);
			cell.setGraphic(box);
		});
		
		JavaFXUtils.setListViewCellFactory(this.movieActorListView, (cell, actor, c) -> {
			HBox box = new HBox();
			Label text = new Label(actor);
			
			ContextMenu menu = new ContextMenu();
			MenuItem viewAllMovies = new MenuItem();
			viewAllMovies.textProperty().bind(Bindings.format("See all movies containing \"%s\"", cell.itemProperty()));
			MenuItem viewActorProfile = new MenuItem();
			viewActorProfile.textProperty().bind(Bindings.format("See actor profile for \"%s\"", cell.itemProperty()));
			
			menu.getItems().addAll(viewAllMovies, viewActorProfile);
			
			cell.setContextMenu(menu);
			
			box.getChildren().add(text);
			cell.setGraphic(box);
		});
		
		

		setupSearchTextField();

		// add event handlers
		this.movieList.getSelectionModel().selectedItemProperty()
				.addListener((o, oldVal, newVal) -> movieListOnChangeItem(newVal));
		this.orderByComboBox.getSelectionModel().selectedItemProperty()
				.addListener((o, oldVal, newVal) -> refreshMovieList(true));
		this.retrieveDataButton.setOnMouseClicked(this::retrieveDataButtonMouseClicked);

		this.automatedDataButton.setOnMouseClicked(e -> new MovieSearchScreen().load(MediaManager.getMainFrameStage()));
		this.openMovieButton.setOnMouseClicked(this::openMovieButtonClicked);

		// Add data
		refreshMovieList(false);

		// Now we have loaded all data, display
		this.orderByComboBox.getSelectionModel().selectFirst();
		this.viewTypeComboBox.getSelectionModel().selectFirst();
		this.movieList.getSelectionModel().selectFirst();
	}
	
	private final void openMovieButtonClicked(MouseEvent event) {
		MovieEntry selected = this.movieList.getSelectionModel().getSelectedItem();
		if(selected != null) {
			try {
				Desktop.getDesktop().open(selected.getFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param reorder
	 *            Whether or not the movie list should be sorted again.
	 */
	private final void refreshMovieList(boolean reorder) {
		this.movieList.getItems().addAll(MediaManager.getMediaRepository().getDisplayedMovieEntries());
		if (reorder) {
			ListOrderer<MovieEntry> currentOrderer = this.orderByComboBox.getSelectionModel().getSelectedItem();
			if (currentOrderer != null) {
				this.movieList.getItems().clear();
				this.movieList.getItems()
						.addAll(currentOrderer.order(MediaManager.getMediaRepository().getDisplayedMovieEntries()));
			}
		}
	}

	private void movieListOnChangeItem(MovieEntry newEntry) {
		updateMovieInterface(newEntry);
	}
	
	private void resetMovieInterface() {
		//TODO finish this
		this.movieActorListView.getItems().clear();
		this.movieInfoLabel.setText("Rating  |  Runtime  |  Genre  |  Release Date");
		this.movieAuthorLabel.setText("Director: | Writer:");
		this.moviePoster.setImage(null);
		this.automatedDataLabel.setText("No data has been selected yet");
		this.moviePlotTextArea.clear();
	}

	private void updateMovieInterface(MovieEntry newEntry) {
		// TODO do similar error checking code on others
		// TODO finish updating
		if (newEntry == null) {
			resetMovieInterface();
			return;
		}

		// Update regardless of there being a definition
		this.moviePlotTextArea.clear();
		this.movieActorListView.getItems().clear();
		this.movieNameLabel.setText(newEntry.toString());
		StringBuilder fileInfo = new StringBuilder();
		fileInfo.append(newEntry.getFile().getName() + " | ");

		final String fileName = newEntry.getFile().getName();
		int extensionStart = fileName.lastIndexOf(".");

		String extension = (extensionStart > 0) ? fileName.substring(extensionStart) : "movie";
		fileInfo.append(extension.replace(".", "").toUpperCase() + " file | ");
		fileInfo.append(newEntry.getFile().length() / (1024 * 1024) + "mb");
		this.fileInfoLabel.setText(fileInfo.toString());

		// Update if there is a definition
		newEntry.getDefinition().ifPresent(def -> {
			try {
				String[] posterLocation = def.getPoster().split("/");
				String poster = posterLocation[(posterLocation.length - 1)];
				final File posterFile = new File(DataConstants.MOVIE_IMAGE_FOLDER + "/" + poster);
				if (!posterFile.exists()) {
					// TODO set movie as a constant, load when needed
					Image image = new Image(
							new File(DataConstants.IMAGE_ROOT_FOLDER + "/movienotfound.gif").toURI().toString());
					this.moviePoster.setImage(image);
				} else {
					Image image = new Image(posterFile.toURI().toString());
					this.moviePoster.setImage(image);
				}
			} catch (Exception e) {
			}

			this.moviePlotTextArea.setText(def.getPlot());
			this.movieInfoLabel.setText(def.getRated() + "  |  " + def.getRuntime() + "  |  " + def.getGenre() + "  |  "
					+ def.getReleased());
			this.movieAuthorLabel.setText("Director(s): " + def.getDirector() + "  |  Writer(s): " + def.getWriter());
			this.automatedDataLabel.setText("This data was automatically selected.");

			for (String actor : def.getActors().split(",")) {
				this.movieActorListView.getItems().add(actor.trim());
			}
		});

		// Update if there is not a definition
		if (!newEntry.getDefinition().isPresent()) {
			resetMovieInterface();
		}
	}

	public void retrieveDataButtonMouseClicked(MouseEvent event) {
		// TODO represent this in a loading bar of some kind so the user doesn't
		// experience freezing and is unaware of what is happening
		// TODO do this on a separate thread to JavaFX
		// TODO update the status indicator bar because this is a low latency
		// operation
		MovieEntry selected = this.movieList.getSelectionModel().getSelectedItem();
		if (selected != null) {
			selected.getDefinition().ifPresent(def -> {
				// TODO notify the user, that the definition exists, get
				// confirmation before retrieving again
				return;
			});
			if (!MediaManager.getMovieDefinitionRetriever().isPresent()) {
				// TODO notify the user that there was an error, use reflection
				// to find all of the movie retrievers and let them set it
				return;
			}
			MediaManager.getMovieDefinitionRetriever().ifPresent(scraper -> {
				Optional<NewMovieDefinition> definition = scraper.scrapeData(selected);

				if (!definition.isPresent()) {
					// TODO return an error message
				}

				definition.ifPresent(def -> {
					// TODO reuse this code
					MovieDefinition storedDefinition = MediaManager.getDatabase().addMovieDefinition(def);
					selected.setMovieDefinitionID(storedDefinition.getDatabaseID());
					MediaManager.getMediaRepository().assignMovieDefinition(storedDefinition.getDatabaseID(),
							storedDefinition);
					MediaManager.getDatabase().assignMovieDefinitionToEntry(selected, storedDefinition.getDatabaseID());
					downloadImage(storedDefinition);

					// TODO find a more efficient way to do this that does not
					// involve updating the whole list
					int selectedIndex = new Integer(this.movieList.getSelectionModel().getSelectedIndex());
					this.refreshMovieList(true);
					this.movieList.getSelectionModel().select(selectedIndex);
					this.updateMovieInterface(this.movieList.getSelectionModel().getSelectedItem());
				});
			});
		}
	}

	public static final void downloadImage(MovieDefinition def) {
		final String[] split = def.getPoster().split("/");

		final File outputFile = new File(DataConstants.MOVIE_IMAGE_FOLDER + "/" + split[split.length - 1]);
		if (outputFile.exists())
			return;
		try {
			URL url = new URL(def.getPoster());
			InputStream in = new BufferedInputStream(url.openStream());
			OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));

			for (int i; (i = in.read()) != -1;) {
				out.write(i);
			}
			in.close();
			out.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addMenuBarItems(MenuBar menuBar) {
		// TODO redo this and remove redundant code
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
		newSearchTextField.setPrefHeight(this.searchTextField.getPrefHeight());
		newSearchTextField.setPromptText("Search");

		this.searchTextField = newSearchTextField;
		this.root.getChildren().add(this.searchTextField);
	}

	@Override
	public void refresh() {
		this.refreshMovieList(true);
	}

}
