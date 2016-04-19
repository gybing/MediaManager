package com.jakebellotti.scene.movie.search;

import java.io.File;
import java.io.IOException;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Jake Bellotti
 * @date Apr 19, 2016
 */
public class MovieSearchScreen {

	private final FXMLLoader loader = new FXMLLoader();
	private AnchorPane root = null;

	@FXML
	private GridView<ImageView> gridView;

	public void load(Stage currentStage) {
		try {
			final Stage stage = new Stage();
			stage.initOwner(currentStage);
			stage.initModality(Modality.WINDOW_MODAL);

			loader.setController(this);
			root = loader.load(getClass().getResource("MovieSearchScreen.fxml").openStream());

			stage.setScene(new Scene(root));
			stage.sizeToScene();
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {
		//TODO this is test data

		final int HEIGHT = 223;
		final int WIDTH = 150;	
		
		gridView.setCellWidth(WIDTH);
		gridView.setCellHeight(HEIGHT);
		gridView.setHorizontalCellSpacing(5);
		gridView.setCellFactory(new Callback<GridView<ImageView>, GridCell<ImageView>>() {

			@Override
			public GridCell<ImageView> call(GridView<ImageView> arg0) {
				return new GridCell<ImageView>() {
					public void updateItem(ImageView item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							this.setGraphic(item);
						} else {
							this.setGraphic(null);
						}
					}
				};
			}
		});

		for (int i = 0; i < 5; i++) {
			ImageView imageView = new ImageView();
			File file = new File("./data/testimg.jpg");
			Image image = new Image(file.toURI().toString());

			imageView.setFitHeight(HEIGHT);
			imageView.setFitWidth(WIDTH);
			imageView.setImage(image);
			this.gridView.getItems().add(imageView);
		}

	}

	public AnchorPane getRoot() {
		return root;
	}

}
