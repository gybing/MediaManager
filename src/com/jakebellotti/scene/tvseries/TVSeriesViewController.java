package com.jakebellotti.scene.tvseries;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.jakebellotti.MediaManager;
import com.jakebellotti.Settings;
import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.listorderer.tvseries.TVSeriesAscendingAlphabeticalListOrderer;
import com.jakebellotti.model.tvseries.TVSeriesEntry;
import com.jakebellotti.model.tvseries.TVSeriesEpisode;
import com.jakebellotti.model.tvseries.TVSeriesNode;
import com.jakebellotti.model.tvseries.TVSeriesRootNode;
import com.jakebellotti.model.tvseries.TVSeriesSeason;
import com.jakebellotti.scene.MediaScene;
import com.jakebellotti.scene.main.MainWindowFrame;
import com.jakebellotti.scene.tvseries.datapane.TVSeriesDataPanes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import jblib.javafx.Alerts;

/**
 * 
 * @author Jake Bellotti
 *
 */
public class TVSeriesViewController implements MediaScene {

	private static Image catOnTacoFlyingThroughSpaceGif = null;

	private boolean informationExpanded = true;
	private static final double originalInformationHeight = 212.0;

	private CheckMenuItem showUnindexedEntries = new CheckMenuItem("Show Unindexed Entries");

	@FXML
	private AnchorPane root;

	@FXML
	private TreeView<TVSeriesNode> seriesTreeView;

	@FXML
	private TextField searchTextField;

	@FXML
	private Button resetFiltersButton;

	@FXML
	private Button manageFiltersButton;

	@FXML
	private ComboBox<ListOrderer<TVSeriesEntry>> orderByComboBox;

	@FXML
	private Button upMoviesButton;

	@FXML
	private Button downMoviesButton;

	@FXML
	private Label seriesNameLabel;

	@FXML
	private StackPane backdropImageStackPane;

	@FXML
	private ImageView backdropImageView;

	@FXML
	private ImageView posterImage;

	@FXML
	private AnchorPane posterImageAnchorPane;

	@FXML
	private Slider posterOpacitySlider;

	@FXML
	private AnchorPane informationAnchorPane;

	@FXML
	private Button informationButton;

	@FXML
	private AnchorPane contentAnchorPane;

	@FXML
	public void initialize() {
		showUnindexedEntries.setSelected(true);
		resetData();
		refreshTVSeriesList(MediaManager.getMediaRepository().getDisplayedTVSeriesEntries(), true);
		addEventHandlers();
		this.orderByComboBox.getSelectionModel().selectFirst();
		this.seriesTreeView.getSelectionModel().selectFirst();
	}

	/**
	 * Adds the appropriate event handlers to this Scene's controls.
	 */
	private final void addEventHandlers() {
		this.orderByComboBox.getItems().add(new TVSeriesAscendingAlphabeticalListOrderer());

		this.informationButton.setOnMouseClicked(this::informationButtonClicked);
		this.seriesTreeView.getSelectionModel().selectedItemProperty().addListener(l -> seriesTreeViewItemSelected());
		this.backdropImageView.fitWidthProperty().bind(this.backdropImageStackPane.widthProperty());
		this.backdropImageView.fitHeightProperty().bind(this.backdropImageStackPane.heightProperty());

		this.posterImage.opacityProperty().bind(this.posterOpacitySlider.valueProperty());
		this.posterImageAnchorPane.opacityProperty().bind(this.posterOpacitySlider.valueProperty());
		this.orderByComboBox.getSelectionModel().selectedItemProperty().addListener(l -> orderByComboBoxItemSelected());
	}

	private final void orderByComboBoxItemSelected() {
		final ListOrderer<TVSeriesEntry> orderer = orderByComboBox.getSelectionModel().getSelectedItem();
		if (orderer != null) {
			refreshTVSeriesList(orderer.order(MediaManager.getMediaRepository().getDisplayedTVSeriesEntries()),
					showUnindexedEntries.isSelected());
		}
	}

	private final void seriesTreeViewItemSelected() {
		final TreeItem<TVSeriesNode> selectedNode = this.seriesTreeView.getSelectionModel().getSelectedItem();
		if (selectedNode == null) {
			resetData();
			return;
		}
		this.contentAnchorPane.getChildren().clear();
		this.posterImage.setVisible(true);
		this.posterImageAnchorPane.setVisible(true);

		if (selectedNode.getValue() instanceof TVSeriesEntry) {
			final TVSeriesEntry tvSeriesEntry = (TVSeriesEntry) selectedNode.getValue();
			AnchorPane seriesDataPane = TVSeriesDataPanes.getSeriesDataPane();
			TVSeriesDataPanes.getSeriesController().updateSeries(tvSeriesEntry);
			this.contentAnchorPane.getChildren().add(seriesDataPane);
			seriesDataPane.prefWidthProperty().bind(contentAnchorPane.widthProperty());

			this.seriesNameLabel.setText(tvSeriesEntry.toString());

			posterImage.setImage(tvSeriesEntry.getPoster());
			backdropImageView.setImage(tvSeriesEntry.getBackDrop());
			return;
		}

		if (selectedNode.getValue() instanceof TVSeriesSeason) {
			final TVSeriesEntry parent = (TVSeriesEntry) selectedNode.getParent().getValue();
			final TVSeriesSeason selectedSeason = (TVSeriesSeason) selectedNode.getValue();

			AnchorPane seasonDataPane = TVSeriesDataPanes.getSeasonDataPane();
			TVSeriesDataPanes.getSeasonController().updateSeason(selectedSeason);
			this.contentAnchorPane.getChildren().add(seasonDataPane);
			seasonDataPane.prefWidthProperty().bind(contentAnchorPane.widthProperty());

			this.seriesNameLabel.setText(parent.toString() + " - " + selectedSeason.toString());
			this.posterImage.setImage(selectedSeason.getPoster());
			this.backdropImageView.setImage(parent.getBackDrop());
			return;
		}

		if (selectedNode.getValue() instanceof TVSeriesEpisode) {
			final TVSeriesEpisode selectedEpisode = (TVSeriesEpisode) selectedNode.getValue();
			final TVSeriesSeason season = (TVSeriesSeason) selectedNode.getParent().getValue();
			final TVSeriesEntry tvSeriesEntry = (TVSeriesEntry) selectedNode.getParent().getParent().getValue();

			AnchorPane episodeDataPane = TVSeriesDataPanes.getEpisodeDataPane();
			TVSeriesDataPanes.getEpisodeController().updateEpisode(selectedEpisode);
			this.contentAnchorPane.getChildren().add(episodeDataPane);
			episodeDataPane.prefWidthProperty().bind(contentAnchorPane.widthProperty());

			this.seriesNameLabel
					.setText(tvSeriesEntry.toString() + " - " + season.toString() + " - " + selectedEpisode.toString());
			this.posterImage.setImage(season.getPoster());
			this.backdropImageView.setImage(tvSeriesEntry.getBackDrop());
			return;
		}
		this.posterImage.setVisible(false);
		this.posterImageAnchorPane.setVisible(false);
		resetData();
	}

	private final void resetData() {
		this.seriesNameLabel.setText("");
		this.posterImage.setImage(null);
//		this.backdropImageView.setImage(getCatOnTacoFlyingThroughSpaceGif());
		this.contentAnchorPane.getChildren().clear();
	}

	/**
	 * Refreshes the TV Series list
	 */
	private final void refreshTVSeriesList(final List<TVSeriesEntry> entries, final boolean showUnindexed) {
		final TreeItem<TVSeriesNode> root = new TreeItem<TVSeriesNode>(new TVSeriesRootNode("TV Series"));

		int count = 0;
		for (TVSeriesEntry entry : entries) {
			final TreeItem<TVSeriesNode> node = new TreeItem<>(entry);
			for (TVSeriesSeason season : entry.getSeasons()) {
				final TreeItem<TVSeriesNode> seriesNode = new TreeItem<>(season);
				for (TVSeriesEpisode episode : season.getEpisodes()) {
					final TreeItem<TVSeriesNode> episodeNode = new TreeItem<>(episode);
					seriesNode.getChildren().add(episodeNode);
				}
				node.getChildren().add(seriesNode);
			}

			if (!entry.getDefinition().isPresent() && !showUnindexed) {

			} else {
				root.getChildren().add(node);
				count++;
			}

		}

		((TVSeriesRootNode) root.getValue()).setName("TV Series (" + count + ")");
		this.seriesTreeView.setRoot(root);
		this.seriesTreeView.getRoot().setExpanded(true);
	}

	private final void informationButtonClicked(MouseEvent e) {
		updateInformation();
	}

	private final void updateInformation() {
		if (this.informationExpanded) {
			this.informationAnchorPane.setMinHeight(this.informationButton.getHeight());
			this.informationAnchorPane.setPrefHeight(this.informationButton.getHeight());
			this.informationAnchorPane.setMaxHeight(this.informationButton.getHeight());
			this.informationExpanded = false;
			this.informationButton.setText("Show Information ▲");
		} else {
			this.informationAnchorPane.setMinHeight(originalInformationHeight);
			this.informationAnchorPane.setPrefHeight(originalInformationHeight);
			this.informationAnchorPane.setMaxHeight(originalInformationHeight);

			this.informationButton.setText("Hide Information ▼");
			this.informationExpanded = true;
		}
	}

	@Override
	public void refresh() {
		this.refreshTVSeriesList(MediaManager.getMediaRepository().getDisplayedTVSeriesEntries(), showUnindexedEntries.isSelected());
	}

	@Override
	public void addMenuBarItems(MenuBar menuBar) {
		Menu fileMenu = new Menu("File");
		MenuItem addASeries = new MenuItem("Add A Series");
		MenuItem addSeriesFromFolder = new MenuItem("Add Several Series From Root Folder");
		MenuItem preloadImages = new MenuItem("Preload Images");
		MenuItem close = new MenuItem("Close");

		Menu viewMenu = new Menu("View");
		showUnindexedEntries.setOnAction(this::showUnindexedEntriesAction);

		viewMenu.getItems().addAll(showUnindexedEntries);

		// Assign event handlers
		addASeries.setOnAction(this::AddASeriesAction);
		addSeriesFromFolder.setOnAction(this::AddSeriesFromAFolderAction);
		preloadImages.setOnAction(this::preloadImagesAction);
		close.setOnAction(e -> Platform.exit());

		// Add to the file menu
		fileMenu.getItems().add(addASeries);
		fileMenu.getItems().add(addSeriesFromFolder);
		if (!Settings.isMemorySaverMode())
			fileMenu.getItems().add(preloadImages);
		fileMenu.getItems().add(close);

		// Add to the menubar
		menuBar.getMenus().addAll(fileMenu, viewMenu, MainWindowFrame.getWindowMenu(), MainWindowFrame.getHelpMenu());
	}

	private final void showUnindexedEntriesAction(final ActionEvent e) {
		refreshTVSeriesList(MediaManager.getMediaRepository().getDisplayedTVSeriesEntries(),
				showUnindexedEntries.isSelected());
	}

	private final void AddASeriesAction(final ActionEvent e) {
		final Optional<String> result = Alerts.showInputAlert("Add a TV Series", "Enter TV Series Name",
				"Please enter the name of the TV Series to add:");
		result.ifPresent(str -> {
			final boolean success = MediaManager.getDatabase().insertTVSeries(str);
			if (!success) {
				Alerts.showErrorAlert("", "", "");
				return;
			}
			refreshTVSeriesList(MediaManager.getMediaRepository().getDisplayedTVSeriesEntries(),
					showUnindexedEntries.isSelected());
			this.orderByComboBoxItemSelected();
			// TODO refresh, add result that we just entered
			// TODO run verification over the input string
			// TODO we can currently have duplicate entries
		});
	}

	private final void AddSeriesFromAFolderAction(final ActionEvent e) {

	}

	private final void preloadImagesAction(ActionEvent e) {
		for (TreeItem<TVSeriesNode> item : this.seriesTreeView.getRoot().getChildren()) {
			if (item.getValue() instanceof TVSeriesEntry) {
				final TVSeriesEntry entry = (TVSeriesEntry) item.getValue();
				entry.getBackDrop();
				entry.getPoster();
			}
		}
	}

	public static Image getCatOnTacoFlyingThroughSpaceGif() {
		if (catOnTacoFlyingThroughSpaceGif == null) {
			catOnTacoFlyingThroughSpaceGif = new Image(new File("./data/spacegif.gif").toURI().toString());
		}
		return catOnTacoFlyingThroughSpaceGif;
	}

	public static void setCatOnTacoFlyingThroughSpaceGif(Image catOnTacoFlyingThroughSpaceGif) {
		TVSeriesViewController.catOnTacoFlyingThroughSpaceGif = catOnTacoFlyingThroughSpaceGif;
	}

}
