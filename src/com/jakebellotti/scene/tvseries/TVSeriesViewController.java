package com.jakebellotti.scene.tvseries;

import java.io.File;

import com.jakebellotti.model.tvseries.TVSeriesEntry;
import com.jakebellotti.model.tvseries.TVSeriesNode;
import com.jakebellotti.model.tvseries.TVSeriesSeason;
import com.jakebellotti.scene.MediaScene;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

public class TVSeriesViewController implements MediaScene {

	private boolean informationExpanded = true;
	private double originalInformationHeight;

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
	private ComboBox<?> orderByComboBox;

	@FXML
	private Button upMoviesButton;

	@FXML
	private Button downMoviesButton;

	@FXML
	private Label movieNameLabel;

	@FXML
	private StackPane backdropImageStackPane;

	@FXML
	private ImageView backdropImageView;

	@FXML
	private ImageView posterImage;

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
		File file = new File("./data/test/series/arrow_backdrop.jpg");
		Image img = new Image(file.toURI().toString());
		this.backdropImageView.setImage(img);
		this.backdropImageView.fitWidthProperty().bind(this.backdropImageStackPane.widthProperty());
		this.backdropImageView.fitHeightProperty().bind(this.backdropImageStackPane.heightProperty());

		File poster = new File("./data/test/series/arrow_poster.jpg");
		Image posterImage = new Image(poster.toURI().toString());
		this.posterImage.setImage(posterImage);

		final TreeItem<TVSeriesNode> root = new TreeItem<TVSeriesNode>(new TVSeriesNode("TV Series"));

		//TODO load the actual tv series in the load indexed data launcher task
		
		final TreeItem<TVSeriesNode> arrow = new TreeItem<>(new TVSeriesEntry(-1, "Arrow", -1));
		arrow.getChildren().add(new TreeItem<>(new TVSeriesSeason("Season 1")));
		arrow.getChildren().add(new TreeItem<>(new TVSeriesSeason("Season 2")));
		root.getChildren().add(arrow);

		final TreeItem<TVSeriesNode> myNameIsEarl = new TreeItem<>(new TVSeriesEntry(-1, "My Name Is Earl", -1));
		myNameIsEarl.getChildren().add(new TreeItem<>(new TVSeriesSeason("Season 1")));
		myNameIsEarl.getChildren().add(new TreeItem<>(new TVSeriesSeason("Season 2")));
		root.getChildren().add(myNameIsEarl);

		this.seriesTreeView.setRoot(root);

		this.seriesTreeView.getRoot().setExpanded(true);

		this.informationButton.setOnMouseClicked(this::informationButtonClicked);
		
	}

	private final void informationButtonClicked(MouseEvent e) {
		updateInformation();
	}
	
	private final void updateInformation() {
		if (this.informationExpanded) {
			this.originalInformationHeight = new Double(this.informationAnchorPane.getHeight());
			this.informationAnchorPane.setMinHeight(this.informationButton.getHeight());
			this.informationAnchorPane.setPrefHeight(this.informationButton.getHeight());
			this.informationAnchorPane.setMaxHeight(this.informationButton.getHeight());
			this.informationExpanded = false;
			this.informationButton.setText("Show Information ▲");
		} else {
			this.informationAnchorPane.setMinHeight(this.originalInformationHeight);
			this.informationAnchorPane.setPrefHeight(this.originalInformationHeight);
			this.informationAnchorPane.setMaxHeight(this.originalInformationHeight);
			
			this.informationButton.setText("Hide Information ▼");
			this.informationExpanded = true;
		}
	}

	@Override
	public void refresh() {

	}

	@Override
	public void addMenuBarItems(MenuBar menuBar) {
		Menu fileMenu = new Menu("File");
		MenuItem close = new MenuItem("Close");
		
		close.setOnAction(e -> Platform.exit());
		fileMenu.getItems().add(close);
		
		menuBar.getMenus().addAll(fileMenu, MainWindowFrame.getWindowMenu(), MainWindowFrame.getHelpMenu());
	}

}
