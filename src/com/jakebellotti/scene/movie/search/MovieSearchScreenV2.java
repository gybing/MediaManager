package com.jakebellotti.scene.movie.search;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * 
 * @author Jake Bellotti
 * Date 6-Apr-2016
 *
 */
public class MovieSearchScreenV2 {
	
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
    private Label selectedTypeLabel;

    @FXML
    private Button viewFullDefinitionButton;

    @FXML
    private Button setAsDefinitionButton;

    @FXML
    private Button cancelButton;

}
