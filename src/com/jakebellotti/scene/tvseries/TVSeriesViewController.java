package com.jakebellotti.scene.tvseries;

import java.io.File;

import com.jakebellotti.scene.MediaScene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class TVSeriesViewController implements MediaScene {

	@FXML
	private AnchorPane root;

	@FXML
	private ListView<?> movieList;

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
	public void initialize() {
		File file = new File("./data/test/series/arrow_backdrop.jpg");
		Image img = new Image(file.toURI().toString());
		this.backdropImageView.setImage(img);
		this.backdropImageView.fitWidthProperty().bind(this.backdropImageStackPane.widthProperty());
		this.backdropImageView.fitHeightProperty().bind(this.backdropImageStackPane.heightProperty());
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

}
