package com.jakebellotti.scene.presentation;

import java.io.File;

import org.controlsfx.control.textfield.TextFields;

import com.jakebellotti.model.MovieFile;
import com.jakebellotti.model.MovieListOrderer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * 
 * @author Jake Bellotti
 * @date Mar 20, 2016
 */

public class PresentationViewController {

	@FXML
	private AnchorPane root;

	@FXML
	private ListView<MovieFile> movieList;
	/**
	 * Allows the user to select how they want to view the movie list, for
	 * example text with a thumbnail or just text.
	 */
	@FXML
	private ComboBox<?> viewTypeComboBox;
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
	private ComboBox<MovieListOrderer> orderByComboBox;
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
		File file = new File("./data/testimg.jpg");
		Image image = new Image(file.toURI().toString());
		moviePoster.setImage(image);
		setupSearchTextField();
	}

	private void setupSearchTextField() {
		this.root.getChildren().remove(this.searchTextField);

		final TextField newSearchTextField = TextFields.createClearableTextField();
		newSearchTextField.setLayoutX(this.searchTextField.getLayoutX());
		newSearchTextField.setLayoutY(this.searchTextField.getLayoutY());
		newSearchTextField.setPrefWidth(this.searchTextField.getPrefWidth());

		this.searchTextField = newSearchTextField;
		this.root.getChildren().add(this.searchTextField);
	}

}
