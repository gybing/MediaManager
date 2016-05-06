package com.jakebellotti.fx.impl;

import java.io.File;

import com.jakebellotti.DataConstants;
import com.jakebellotti.Images;
import com.jakebellotti.fx.ListViewModifier;
import com.jakebellotti.model.movie.MovieEntryWrapper;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import jblib.javafx.JavaFXUtils;

/**
 * 
 * @author Jake Bellotti
 * @date Apr 19, 2016
 *
 */
public class DetailsListViewModifier extends ListViewModifier<MovieEntryWrapper> {

	@Override
	public void change(ListView<MovieEntryWrapper> listView) {
		JavaFXUtils.setListViewCellFactory(listView, (cell, movieEntry, c) -> {
			HBox box = new HBox();
			Label text = new Label(movieEntry.toString());
			if (!movieEntry.getMovieEntry().getDefinition().isPresent()) {
				text.setStyle("-fx-text-fill: red");
			}

			ContextMenu menu = new ContextMenu();
			MenuItem delete = new MenuItem();
			delete.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
			menu.getItems().add(delete);
			// TODO finish feature

			cell.setContextMenu(menu);

			if (movieEntry.isShowLoadingImage()) {
				ImageView loadingImage = new ImageView();
				loadingImage.setImage(Images.getLoadingImage());
				loadingImage.setFitWidth(cell.getHeight());
				loadingImage.setFitHeight(cell.getHeight());
				box.getChildren().add(loadingImage);
			}
			
			if(!movieEntry.getMovieEntry().fileExists()) {
				//TODO have a closer look at this
				final File fileNotFound = new File(DataConstants.IMAGE_ROOT_FOLDER + "/filenotfound.png");
				ImageView fileNotFoundImageView = new ImageView();
				
				fileNotFoundImageView.setImage(new Image(fileNotFound.toURI().toString()));
				fileNotFoundImageView.setFitHeight(23);
				fileNotFoundImageView.setFitWidth(23);
				box.getChildren().add(fileNotFoundImageView);
			}

			box.getChildren().add(text);
			cell.setGraphic(box);
		});
		
	}

	@Override
	public String getName() {
		return "Details";
	}

}
