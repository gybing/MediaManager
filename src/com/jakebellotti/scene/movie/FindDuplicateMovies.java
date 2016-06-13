package com.jakebellotti.scene.movie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * @author Jake Bellotti
 * @since 8 Jun 2016
 */
public class FindDuplicateMovies {

	@FXML
	private ListView<?> movieTitleListView;

	@FXML
	private ListView<?> duplicateListView;

	@FXML
	private Label selectedFileLocationLabel;

	@FXML
	private Label selectedFileNameLabel;

	@FXML
	private Label selectedFileSizeLabel;

	@FXML
	private Button openMovieButton;

}
