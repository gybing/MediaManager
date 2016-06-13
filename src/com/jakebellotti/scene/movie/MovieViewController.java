package com.jakebellotti.scene.movie;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.textfield.TextFields;

import com.jakebellotti.scene.MediaScene;
import com.jakebellotti.scene.main.MainWindowFrame;
import com.jakebellotti.scene.movie.add.AddDirectoryController;
import com.jakebellotti.scene.movie.add.AddMovieWindow;
import com.jakebellotti.scene.movie.search.MovieSearchScreen;
import com.jakebellotti.scene.settings.SettingsWindow;
import com.jakebellotti.DataConstants;
import com.jakebellotti.FileOpener;
import com.jakebellotti.Images;
import com.jakebellotti.MediaManager;
import com.jakebellotti.Settings;
import com.jakebellotti.fx.ListViewModifier;
import com.jakebellotti.fx.impl.*;
import com.jakebellotti.io.Logger;
import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.ReturnResultWrapper;
import com.jakebellotti.model.ReturnStatus;
import com.jakebellotti.model.filenamecleanser.FileNameCleanserRepository;
import com.jakebellotti.model.listorderer.movie.MovieAscendingAlphabeticalListOrderer;
import com.jakebellotti.model.listorderer.movie.MovieAverageScoreListOrderer;
import com.jakebellotti.model.listorderer.movie.MovieDescendingAlphabeticalListOrderer;
import com.jakebellotti.model.listorderer.movie.MovieIMDBScoreListOrderer;
import com.jakebellotti.model.listorderer.movie.MovieMetascoreListOrderer;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.MovieEntryWrapper;
import com.jakebellotti.model.movie.NewMovieDefinition;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import jblib.javafx.Alerts;
import jblib.javafx.JavaFXUtils;

/**
 * TODO image should be saved as <movieDefinitionID>_low.jpg and
 * <movieDefinitionID>_high.jpg
 * 
 * @author Jake Bellotti
 * @date Mar 20, 2016
 */

public class MovieViewController implements MediaScene {

	private static final Logger logger = new Logger(MovieViewController.class);

	@FXML
	private AnchorPane root;

	@FXML
	private Label imdbScoreLabel;

	@FXML
	private Label metascoreLabel;

	@FXML
	private ProgressBar imdbscoreProgressBar;

	@FXML
	private ProgressBar metascoreProgressbar;

	@FXML
	private ListView<MovieEntryWrapper> movieList;
	/**
	 * Allows the user to select how they want to view the movie list, for
	 * example text with a thumbnail or just text.
	 */
	@FXML
	private ComboBox<ListViewModifier<MovieEntryWrapper>> viewTypeComboBox;
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
	private ComboBox<ListOrderer<MovieEntryWrapper>> orderByComboBox;
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
		resetMovieInterface();
		this.viewTypeComboBox.getItems().add(new DetailsListViewModifier(this));
		this.viewTypeComboBox.getItems().add(new TilesSmallListViewModifier(this));
		this.viewTypeComboBox.getItems().add(new TilesMediumListViewModifier(this));

		this.orderByComboBox.getItems().add(new MovieAscendingAlphabeticalListOrderer());
		this.orderByComboBox.getItems().add(new MovieDescendingAlphabeticalListOrderer());
		this.orderByComboBox.getItems().add(new MovieIMDBScoreListOrderer());
		this.orderByComboBox.getItems().add(new MovieMetascoreListOrderer());
		this.orderByComboBox.getItems().add(new MovieAverageScoreListOrderer());

		this.imdbScoreLabel.setStyle("-fx-text-fill: black;");
		this.metascoreLabel.setStyle("-fx-text-fill: black;");

		setupMovieActorListView();
		setupSearchTextField();

		// add event handlers
		// TODO add event handler to either open up the hd poster or download it
		// if it doesn't exist, or just open up the regular size
		this.moviePoster.setCursor(Cursor.HAND);

		this.movieList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> movieListOnChangeItem(newVal));
		this.orderByComboBox.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> refreshMovieList(true));
		this.retrieveDataButton.setOnMouseClicked(this::retrieveDataButtonMouseClicked);

		this.automatedDataButton.setOnMouseClicked(this::automatedDataButtonMouseClicked);
		this.openMovieButton.setOnMouseClicked(this::openMovieButtonClicked);
		this.viewTypeComboBox.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> viewTypeComboBoxSelected(oldVal, newVal));

		this.upMoviesButton.setOnMouseClicked(this::upMoviesButtonClicked);
		this.downMoviesButton.setOnMouseClicked(this::downMoviesButtonClicked);

		// Add data
		refreshMovieList(false);

		// Now we have loaded all data, display
		this.orderByComboBox.getSelectionModel().selectFirst();
		this.viewTypeComboBox.getSelectionModel().selectFirst();
		this.movieList.getSelectionModel().selectFirst();
	}

	/**
	 * Adds the context menu to the movie entry list view.
	 * 
	 * @param entry
	 */
	public void addMovieListViewContextMenu(final ListCell<MovieEntryWrapper> cell) {
		final ContextMenu contextMenu = new ContextMenu();

		final MenuItem delete = new MenuItem();
		delete.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
		delete.setOnAction(this::movieListViewContextMenuDeleteAction);

		final MenuItem rename = new MenuItem();
		rename.textProperty().bind(Bindings.format("Rename \"%s\"", cell.itemProperty()));
		rename.setOnAction(this::movieListViewContextMenuRenameAction);
		
		//TODO maybe add the option to rename the file to what we got from it now

		contextMenu.getItems().addAll(rename, delete);
		cell.setContextMenu(contextMenu);
	}

	private final void movieListViewContextMenuRenameAction(ActionEvent e) {
		final MovieEntryWrapper selected = this.movieList.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Optional<String> result = Alerts.showInputAlert(selected.toString(), "Renaming movie", "", "Enter the new movie name: ");
			result.ifPresent(str -> {
				final boolean successfullyUpdated = MediaManager.getDatabase().updateMovieEntryName(selected.getMovieEntry(), str);
				if (successfullyUpdated) {
					selected.getMovieEntry().setExtractedMovieName(str);
					final int selectedIndex = this.movieList.getSelectionModel().getSelectedIndex();
					if (selectedIndex != -1) {
						this.movieList.getItems().set(selectedIndex, null);
						this.movieList.getItems().set(selectedIndex, selected);
					}
				}
			});
		}
	}

	/**
	 * Activated when the user presses 'delete' on the movie list view context
	 * menu.
	 * 
	 * @param e
	 */
	private final void movieListViewContextMenuDeleteAction(ActionEvent e) {
		// TODO maybe ask the user if they want to add this as an exception, to
		// be filtered from the file chooser next time they are to attempt to
		// add another movie
		final MovieEntryWrapper selected = this.movieList.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Alerts.showConfirmationAlert("Delete movie", "You are about to delete this movie",
					"Are you sure you want to delete " + selected.getMovieEntry().toString() + "?").ifPresent(button -> {
						if (button == ButtonType.OK || button == ButtonType.YES) {
							if (MediaManager.getDatabase().deleteMovieEntry(selected.getMovieEntry())) {
								movieList.getItems().remove(selected);
								MediaManager.getMediaRepository().getLoadedMovieEntries().remove(selected.getMovieEntry());
							} else {
								Alerts.showErrorAlert("Error", "Could not delete movie entry",
										"The movie entry was unable to be deleted, restart the program and try again.");
							}
						}
					});
		}
	}

	/**
	 * Sets up the movie actor list view, adds the necessary context menu items
	 * and rendering.
	 */
	private final void setupMovieActorListView() {
		JavaFXUtils.setListViewCellFactory(this.movieActorListView, (cell, actor, c) -> {
			HBox box = new HBox();
			Label text = new Label(actor);

			ContextMenu menu = new ContextMenu();
			MenuItem viewAllMovies = new MenuItem();
			viewAllMovies.textProperty().bind(Bindings.format("See all movies containing \"%s\"", cell.itemProperty()));
			viewAllMovies.setOnAction(this::movieActorListViewViewMoviesAction);

			MenuItem viewActorProfile = new MenuItem();
			viewActorProfile.textProperty().bind(Bindings.format("See actor profile for \"%s\"", cell.itemProperty()));
			viewActorProfile.setOnAction(this::movieActorListViewViewActorProfileAction);

			menu.getItems().addAll(viewAllMovies, viewActorProfile);

			cell.setContextMenu(menu);

			box.getChildren().add(text);
			cell.setGraphic(box);
		});
	}

	/**
	 * Activated when the user clicks 'See all movies containing' on the movie
	 * actor list view.
	 * 
	 * @param e
	 */
	private final void movieActorListViewViewMoviesAction(ActionEvent e) {
		// TODO finish feature
	}

	/**
	 * Activated when the user clicks 'See actor profile'.
	 * 
	 * @param e
	 */
	private final void movieActorListViewViewActorProfileAction(ActionEvent e) {
		// TODO finish feature
	}

	/**
	 * Selects the entry above the currently selected one in the movie list
	 * view.
	 * 
	 * @param event
	 */
	private final void upMoviesButtonClicked(MouseEvent event) {
		int selectedIndex = this.movieList.getSelectionModel().getSelectedIndex();
		if ((selectedIndex - 1) > -1) {
			this.movieList.getSelectionModel().select(selectedIndex - 1);
			this.movieList.scrollTo(selectedIndex - 1);
		}
		this.movieList.requestFocus();
	}

	/**
	 * Selects the entry below the currently selected one in the movie list
	 * view.
	 * 
	 * @param event
	 */
	private final void downMoviesButtonClicked(MouseEvent event) {
		int selectedIndex = this.movieList.getSelectionModel().getSelectedIndex();
		if (selectedIndex != (this.movieList.getItems().size() - 1)) {
			this.movieList.getSelectionModel().select(selectedIndex + 1);
			this.movieList.scrollTo(selectedIndex + 1);
		}
		this.movieList.requestFocus();
	}

	/**
	 * Activated when the user selects the automated data button.
	 * 
	 * @param event
	 */
	private final void automatedDataButtonMouseClicked(MouseEvent event) {
		// TODO search for movies
		MovieEntryWrapper selected = this.movieList.getSelectionModel().getSelectedItem();
		if (selected != null) {
			final MovieSearchScreen movieSearch = new MovieSearchScreen(selected, MediaManager.getMainFrameStage());
			movieSearch.load();
		}
	}

	/**
	 * Activated when an item is selected in the view type combo box, orders the
	 * movie list view accordingly.
	 * 
	 * @param oldVal
	 * @param newVal
	 */
	private final void viewTypeComboBoxSelected(ListViewModifier<MovieEntryWrapper> oldVal, ListViewModifier<MovieEntryWrapper> newVal) {
		newVal.change(this.movieList);
	}

	/**
	 * Activated when the open movie button is clicked.
	 * 
	 * @param event
	 */
	private final void openMovieButtonClicked(MouseEvent event) {
		// TODO be able to manage applications
		// TODO be able to relocate the movie
		MovieEntryWrapper selected = this.movieList.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (FileOpener.openMedia(selected.getMovieEntry().getFile())) {
				MediaManager.getDatabase().insertRecentMovieEntry(selected.getMovieEntry());
			}
		}
	}

	/**
	 * 
	 * @param reorder
	 *            Whether or not the movie list should be sorted again.
	 */
	private final void refreshMovieList(boolean reorder) {
		if (!reorder) {
			int selectedIndex = this.movieList.getSelectionModel().getSelectedIndex();
			this.movieList.getItems().clear();
			this.movieList.getItems().addAll(MediaManager.getMediaRepository().getDisplayedMovieEntries());
			this.movieList.getSelectionModel().select(selectedIndex);
			return;
		}

		if (reorder) {
			int selectedIndex = this.movieList.getSelectionModel().getSelectedIndex();
			ListOrderer<MovieEntryWrapper> currentOrderer = this.orderByComboBox.getSelectionModel().getSelectedItem();
			if (currentOrderer != null) {
				this.movieList.getItems().clear();
				this.movieList.getItems().addAll(currentOrderer.order(MediaManager.getMediaRepository().getDisplayedMovieEntries()));
				this.movieList.getSelectionModel().select(selectedIndex);
			}
		}
	}

	/**
	 * Activated when a movie is selected in the movie list view.
	 * 
	 * @param newEntry
	 */
	private void movieListOnChangeItem(MovieEntryWrapper newEntry) {
		updateMovieInterface(newEntry);
	}

	/**
	 * Resets the movie interface to it's original state, clears all data off.
	 */
	private void resetMovieInterface() {
		// TODO finish this
		this.movieNameLabel.setText("");
		this.movieActorListView.getItems().clear();
		this.movieInfoLabel.setText("");
		this.movieAuthorLabel.setText("");
		this.moviePoster.setImage(null);
		this.automatedDataLabel.setText("No data has been selected yet");
		automatedDataButton.setText("");
		this.moviePlotTextArea.clear();
		this.fileInfoLabel.setText("");
		this.imdbscoreProgressBar.setProgress(0);
		this.imdbScoreLabel.setText("");
		this.metascoreProgressbar.setProgress(0);
		this.metascoreLabel.setText("");
		this.automatedDataButton.setDisable(true);
	}

	private final void setFileInfo(MovieEntryWrapper newEntry) {
		StringBuilder fileInfo = new StringBuilder();
		fileInfo.append(newEntry.getMovieEntry().getFile().getName() + " | ");

		final String fileName = newEntry.getMovieEntry().getFile().getName();
		int extensionStart = fileName.lastIndexOf(".");

		String extension = (extensionStart > 0) ? fileName.substring(extensionStart) : "movie";
		fileInfo.append(extension.replace(".", "").toUpperCase() + " file | ");
		fileInfo.append(newEntry.getMovieEntry().getFile().length() / (1024 * 1024) + "mb");
		this.fileInfoLabel.setText(fileInfo.toString());
	}

	private void updateMovieInterface(MovieEntryWrapper newEntry) {
		// TODO do similar error checking code on others
		// TODO finish updating
		if (newEntry == null) {
			resetMovieInterface();
			return;
		}
		this.moviePoster.setImage(this.getPosterImage(newEntry.getMovieEntry()));

		// Update regardless of there being a definition
		this.moviePlotTextArea.clear();
		this.movieActorListView.getItems().clear();
		this.movieNameLabel.setText(newEntry.toString());
		setFileInfo(newEntry);
		
		retrieveDataButton.setDisable(newEntry.isShowLoadingImage());

		// Update if there is a definition
		newEntry.getMovieEntry().getDefinition().ifPresent(def -> {
			if (this.automatedDataButton.isDisabled()) {
				this.automatedDataButton.setDisable(false);
			}
			this.moviePlotTextArea.setText(def.getPlot());
			this.movieInfoLabel.setText(def.getRated() + "  |  " + def.getRuntime() + "  |  " + def.getGenre() + "  |  " + def.getReleased());
			this.movieAuthorLabel.setText("Director(s): " + def.getDirector() + "  |  Writer(s): " + def.getWriter());
			this.automatedDataLabel.setText("This data was automatically selected.");

			double imdbScore = def.getImdbRating();

			this.imdbScoreLabel.setText("IMDB score: N/A");
			if (imdbScore > 0) {
				imdbScore = (imdbScore / 10);
				this.imdbScoreLabel.setText("IMDB Score: " + def.getImdbRating());
			}

			double metascore = def.getMetascore();
			this.metascoreLabel.setText("Metascore: N/A");

			if (metascore > 0) {
				metascore = (metascore / 100);
				this.metascoreLabel.setText("Metascore: " + def.getMetascore());
			}

			this.imdbscoreProgressBar.setProgress(imdbScore > 0 ? imdbScore : 0);
			this.metascoreProgressbar.setProgress(metascore > 0 ? metascore : 0);

			for (String actor : def.getActors().split(",")) {
				this.movieActorListView.getItems().add(actor.trim());
			}
		});

		// Update if there is not a definition
		if (!newEntry.getMovieEntry().getDefinition().isPresent()) {
			resetMovieInterface();
			setFileInfo(newEntry);
		}
	}

	/**
	 * Gets the poster image for the given movie entry.
	 * 
	 * @param entry
	 * @return
	 */
	private final Image getPosterImage(MovieEntry entry) {
		if (entry == null) {
			return null;
		}
		if (!entry.getDefinition().isPresent()) {
			File movieNotFound = new File(DataConstants.IMAGE_ROOT_FOLDER + "/movienotfound.gif");
			return new Image(movieNotFound.toURI().toString());
		} else {
			String[] posterLocation = entry.getDefinition().get().getPoster().split("/");
			String poster = posterLocation[(posterLocation.length - 1)];
			final File posterFile = new File(DataConstants.MOVIE_IMAGE_FOLDER + "/" + poster);
			if (!posterFile.exists()) {
				File movieNotFound = new File(DataConstants.IMAGE_ROOT_FOLDER + "/movienotfound.gif");
				return new Image(movieNotFound.toURI().toString());
			}
			return new Image(posterFile.toURI().toString());
		}
	}

	/**
	 * Activated when the user clicks on the 'Retrieve Data' button for the
	 * selected movie entry.
	 * 
	 * @param event
	 */
	public void retrieveDataButtonMouseClicked(MouseEvent event) {
		MovieEntryWrapper selected = this.movieList.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (selected.getMovieEntry().getDefinition().isPresent()) {
				Optional<ButtonType> alert = Alerts.showConfirmationAlert("Definition exists", "Do you want to override the existing definition?",
						"Data for the selected movie already exists, and you have chosen to scrape the data for this movie. Do you want to override the existing data?");
				if (alert.isPresent()) {
					ButtonType type = alert.get();
					if (type == ButtonType.CANCEL || type == ButtonType.CLOSE) {
						return;
					}
				}
			}

			if (!MediaManager.getMovieDefinitionRetriever().isPresent()) {
				Alerts.showInformationAlert("No movie scraper set", "No movie scraper set",
						"No movie scraper is set. This means there is no way of retrieving the data. Go to settings and set a movie scraper.");
				return;
			}
			MediaManager.getMovieDefinitionRetriever().ifPresent(scraper -> {
				Platform.runLater(() -> {
					// TODO clean up this code...
					final int selectedIndex = new Integer(this.movieList.getSelectionModel().getSelectedIndex());
					selected.setShowLoadingImage(true);
					this.movieList.getItems().set(selectedIndex, null);
					this.movieList.getItems().set(selectedIndex, selected);
					
					this.retrieveDataButton.setDisable(true);

					Thread thread = new Thread(() -> {
						Optional<ReturnResultWrapper<ReturnStatus, NewMovieDefinition>> wrapper = scraper.scrapeData(selected.getMovieEntry());

						if (wrapper.isPresent()) {
							ReturnResultWrapper<ReturnStatus, NewMovieDefinition> w = wrapper.get();

							if (!w.getReturnResult().isPresent()) {
								Platform.runLater(() -> {
									selected.setShowLoadingImage(false);
									this.movieList.getItems().set(selectedIndex, null);
									this.movieList.getItems().set(selectedIndex, selected);
									this.retrieveDataButton.setDisable(false);
									Alerts.showErrorAlert("Error", "Definition could not be retrieved", w.getStatus().getDescription());
								});
								return;
							}

							w.getReturnResult().ifPresent(def -> {
								MovieDefinition storedDefinition = MediaManager.getDatabase().addMovieDefinition(def);
								selected.getMovieEntry().setMovieDefinitionID(storedDefinition.getDatabaseID());
								MediaManager.getMediaRepository().assignMovieDefinition(storedDefinition.getDatabaseID(), storedDefinition);
								MediaManager.getDatabase().assignMovieDefinitionToEntry(selected.getMovieEntry(), storedDefinition.getDatabaseID());
								Images.downloadImage(storedDefinition);

								selected.setShowLoadingImage(false);
								Platform.runLater(() -> {
									this.movieList.getItems().set(selectedIndex, null);
									this.movieList.getItems().set(selectedIndex, selected);
									this.retrieveDataButton.setDisable(false);
									this.updateMovieInterface(this.movieList.getSelectionModel().getSelectedItem());
								});
							});
						}
					});
					thread.start();
				});
			});
		}
	}

	/**
	 * Adds the meny bar items to the given menu bar.
	 */
	public void addMenuBarItems(MenuBar menuBar) {
		// TODO redo this and remove redundant code
		Menu fileMenu = new Menu("File");
		MenuItem indexFile = new MenuItem("Index files");
		MenuItem indexFolder = new MenuItem("Index a folder");
		MenuItem retrieveDefinitions = new MenuItem("Retrieve Definitions");
		MenuItem findDuplicates = new MenuItem("Find Duplicates");
		MenuItem settings = new MenuItem("Settings");
		MenuItem closeItem = new MenuItem("Close");

		indexFile.setOnAction(this::indexFileEvent);
		indexFolder.setOnAction(this::indexAFolderEvent);
		retrieveDefinitions.setOnAction(this::retrieveDefinitionsEvent);
		findDuplicates.setOnAction(this::findDuplicatesEvent);

		settings.setOnAction(event -> SettingsWindow.open(MediaManager.getMainFrameStage()));
		closeItem.setOnAction(event -> Platform.exit());
		fileMenu.getItems().addAll(indexFile, indexFolder, retrieveDefinitions, findDuplicates, settings, closeItem);
		menuBar.getMenus().addAll(fileMenu, MainWindowFrame.getWindowMenu(), MainWindowFrame.getHelpMenu());
	}

	/**
	 * Finds the duplicate movie entries (with the same definition).
	 * 
	 * @param e
	 */
	private final void findDuplicatesEvent(ActionEvent e) {

	}

	/**
	 * Opens a file chooser and lets the user select several files in which to
	 * add to the database.
	 * 
	 * @param e
	 */
	private final void indexFileEvent(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose movie file");

		String[] fileExtensionOptions = new String[Settings.getVideoFileAssociations().length];
		for (int index = 0; index < Settings.getVideoFileAssociations().length; index++) {
			fileExtensionOptions[index] = "*" + Settings.getVideoFileAssociations()[index];
		}
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Video", fileExtensionOptions));
		for (String ext : Settings.getVideoFileAssociations()) {
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(ext.toUpperCase() + " files (*" + ext + ")", "*" + ext));
		}

		final List<File> selectedFiles = fileChooser.showOpenMultipleDialog(MediaManager.getMainFrameStage());
		if (selectedFiles != null) {
			AddMovieWindow addMovie = new AddMovieWindow(selectedFiles, FileNameCleanserRepository.getCleansers());
			int added = addMovie.showAndReturnValues(MediaManager.getMainFrameStage());
			if (added > 0) {
				this.refreshMovieList(true);
			}
		}
	}

	/**
	 * Opens up the definition retriever and attempts to parse all
	 * 
	 * @param e
	 */
	private final void retrieveDefinitionsEvent(ActionEvent e) {
		RetrieveMovieDefinitionsOptions.open(MediaManager.getMainFrameStage());
	}

	private final void indexAFolderEvent(ActionEvent e) {
		AddDirectoryController.getDirectory().ifPresent(directory -> {
			final boolean added = MediaManager.getDatabase().addMovieDirectoryEntry(directory);
			if (!added) {
				Alerts.showInformationAlert("Directory already indexed", "Directory was not added",
						"The selected directory was not added because it already exists. If you wish to edit this directory, you must do so via the settings.");
				return;
			}
			// TODO check if database constraints handle this anyway
			// TODO we could reuse this code in some instances, make that
			// possible
			ArrayList<File> gatheredFiles = new ArrayList<>();
			gatheredFiles = scanDirectory(gatheredFiles, directory.getDirectory(), directory.isScanSubdirectories());
			final ArrayList<String> toRemoveFileNames = MediaManager.getDatabase().getAddedFileNames(gatheredFiles);

			final Iterator<File> iter = gatheredFiles.iterator();
			while (iter.hasNext()) {
				final File currentFile = iter.next();
				for (String s : toRemoveFileNames) {
					if (currentFile.getPath().toLowerCase().equalsIgnoreCase(s.toLowerCase())) {
						iter.remove();
						break;
					}
				}
			}

			AddMovieWindow addMovie = new AddMovieWindow(gatheredFiles, FileNameCleanserRepository.getCleansers());
			int amountAdded = addMovie.showAndReturnValues(MediaManager.getMainFrameStage());
			if (amountAdded > 0) {
				this.refreshMovieList(true);
			}
		});
	}

	private final ArrayList<File> scanDirectory(final ArrayList<File> toReturn, final File start, final boolean recursive) {
		for (final File f : start.listFiles()) {
			final int periodIndex = f.getName().lastIndexOf(".");
			if (periodIndex != -1) {
				final String extension = f.getName().substring(periodIndex).trim();
				for (String ext : Settings.getVideoFileAssociations()) {
					if (extension.equalsIgnoreCase(ext)) {
						toReturn.add(f);
						continue;
					}
				}
			}
		}
		if (recursive) {
			// TODO add recursive additions
		}
		return toReturn;
	}

	/**
	 * Changes the native JavaFX text field to the special one in the ControlsFX
	 * library.
	 */
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
		this.searchTextField.textProperty().addListener(e -> {
			MediaManager.getMediaRepository().setMovieSearchText(this.searchTextField.getText().trim());
			this.refreshMovieList(true);
		});
	}

	@Override
	public void refresh() {
		this.refreshMovieList(true);
	}

}
