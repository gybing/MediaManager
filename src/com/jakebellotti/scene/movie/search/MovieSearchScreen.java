package com.jakebellotti.scene.movie.search;

import java.io.File;
import java.io.IOException;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import com.jakebellotti.model.movie.MovieEntryWrapper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * http://www.omdbapi.com/?s=Batman&type=movie&page=2
 * 
 * @author Jake Bellotti Date 6-Apr-2016
 *
 */
public class MovieSearchScreen {

	private final Stage stage = new Stage();
	private final FXMLLoader loader = new FXMLLoader();
	private AnchorPane root;
	private final MovieEntryWrapper movieEntry;
	private final Stage currentStage;
	private SearchResultWrapper lastSelected = null;
	private int currentPage = 0;
	private int maxPages = 0;
	
    @FXML
    private AnchorPane rootPane;

	@FXML
	private StackPane resultsStackPane;

	@FXML
	private Button searchButton;

	@FXML
	private TextField searchTextField;

	@FXML
	private Label totalResultsLabel;

	@FXML
	private Label selectedTitleLabel;

	@FXML
	private Label selectedYearLabel;

	@FXML
	private Button viewFullDefinitionButton;

	@FXML
	private Button setAsDefinitionButton;

	@FXML
	private Button cancelButton;

	public MovieSearchScreen(MovieEntryWrapper movieEntry, Stage currentStage) {
		this.movieEntry = movieEntry;
		this.currentStage = currentStage;
	}

	@FXML
	public void initialize() {
		this.searchTextField.setText(this.movieEntry.toString());
		this.setAsDefinitionButton.setDisable(true);
		this.viewFullDefinitionButton.setDisable(true);
		this.selectedTitleLabel.setText("");
		this.selectedYearLabel.setText("");

		final GridView<SearchResultWrapper> gridView = new GridView<>();

		final int HEIGHT = 223;
		final int WIDTH = 150;

		gridView.setCellWidth(WIDTH);
		gridView.setCellHeight(HEIGHT);
		gridView.setHorizontalCellSpacing(5);

		// TODO use jblib to clean this up
		gridView.setCellFactory(new Callback<GridView<SearchResultWrapper>, GridCell<SearchResultWrapper>>() {
			@Override
			public GridCell<SearchResultWrapper> call(GridView<SearchResultWrapper> arg0) {
				return new GridCell<SearchResultWrapper>() {
					public void updateItem(SearchResultWrapper item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							this.setOnMouseClicked((e) -> searchResultWrapperClicked(item));
							this.setGraphic(item.getStackPane());
						} else {
							this.setGraphic(null);
						}
					}
				};
			}
		});

		
		AnchorPane overLay = new AnchorPane();
		
		overLay.setMinWidth(this.rootPane.getMinWidth());
		overLay.setMinHeight(this.rootPane.getPrefHeight());
		overLay.setStyle("-fx-background-color: black");
		this.rootPane.getChildren().add(overLay);
		overLay.toFront();

		//TODO do the search here
//		Resets the overlay
//		TODO just remove lmao
//		overLay.toBack();
//		overLay.setStyle("");
		//TODO start loaidng the images here
		//TODO add the images here
		for (int i = 0; i < 5; i++) {
			SearchResultWrapper result = new SearchResultWrapper("Year One", 2016, "imdb123", "movie", "poster.jpg");
			File file = new File("./data/testimg.jpg");
			Image image = new Image(file.toURI().toString());

			result.getImageView().setFitHeight(HEIGHT);
			result.getImageView().setFitWidth(WIDTH);
			result.getImageView().setImage(image);

			result.getStackPane().setMinWidth(WIDTH + 10);
			result.getStackPane().setMinHeight(HEIGHT + 10);

			result.getStackPane().getChildren().add(result.getImageView());
			result.getImageView().setLayoutX(5);
			result.getImageView().setLayoutY(5);
			gridView.getItems().add(result);
		}

		resultsStackPane.getChildren().addAll(gridView);
	}

	private void searchResultWrapperClicked(SearchResultWrapper item) {
		if (lastSelected != null) {
			lastSelected.getStackPane().setStyle("");
		}
		lastSelected = item;
		item.getStackPane().setStyle("-fx-background-color: blue");
		if (this.setAsDefinitionButton.isDisabled())
			this.setAsDefinitionButton.setDisable(false);
		if (this.viewFullDefinitionButton.isDisabled())
			this.viewFullDefinitionButton.setDisable(false);
		this.selectedTitleLabel.setText("Selected: " + item.getTitle());
		this.selectedYearLabel.setText("Year: " + item.getYear());
		// TODO use a loading gif in place of a normal one until the image is
		// loaded
	}

	public void load() {
		// TODO add a return type to this
		// TODO finish the logic
		// TODO title bar for this
		try {
			loader.setController(this);
			root = loader.load(this.getClass().getResource("MovieSearchScreen.fxml").openStream());
			this.stage.initOwner(currentStage);
			this.stage.initModality(Modality.APPLICATION_MODAL);

			this.stage.setScene(new Scene(root));
			this.stage.sizeToScene();
			this.stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getStage() {
		return stage;
	}

}
