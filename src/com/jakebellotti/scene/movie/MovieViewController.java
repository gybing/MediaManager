package com.jakebellotti.scene.movie;

import java.io.File;

import org.controlsfx.control.textfield.TextFields;

import com.jakebellotti.model.MovieEntry;
import com.jakebellotti.model.MovieDefinition;
import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.MediaRepository;

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
	private ComboBox<String> viewTypeComboBox;
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
		File file = new File("./data/testimg.jpg");
		Image image = new Image(file.toURI().toString());
		moviePoster.setImage(image);
		
		MediaRepository.assignMovieDefinition(1, new MovieDefinition("Road To Perdition", 1998, "", "", "", "","", "", "", "", "", "", "","", 100, 6.7, "", ""));
		
		this.movieList.getItems().add(new MovieEntry(1, file.getAbsolutePath(), 1));
		this.moviePlotTextArea.setText("1931. Mike Sullivan and Connor Rooney are two henchmen of elderly Chicago-based Irish-American mobster John Rooney, Connor's father. In many respects, John treats Mike more as his son, who he raised as his own after Mike was orphaned, than the volatile Connor, who nonetheless sees himself as the heir apparent to the family business. One evening, Mike's eldest son, twelve year old Michael Sullivan Jr., who has no idea what his father does for a living, witnesses Connor and his father gun down an associate and his men, the situation gone wrong initiated from an action by Connor. Caught witnessing the incident, Michael is sworn to secrecy about what he saw. Regardless, Connor, not wanting any loose ends, makes an attempt to kill Mike, his wife and their two sons. Mike and the surviving members of his family know that they need to go on the run as Connor, who has gone into hiding, will be protected through mob loyalty, especially by John, who cannot turn on his own flesh and blood. Still, Mike has to figure out a way for retribution for what Connor did, while still protecting him and his family, not only from Connor, but from John and his fellow associates. Through it all, Mike wants those in his family that had no say in what he chose as a living, to have some redemption for their eternal souls.");
		this.movieActorListView.getItems().add("Tyler Hoechlin");
		this.movieActorListView.getItems().add("Rob Maxey");
		this.viewTypeComboBox.getItems().add("List");
		
		
		setupSearchTextField();
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
