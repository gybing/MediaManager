package com.jakebellotti.fx.impl;

import java.io.File;

import com.jakebellotti.DataConstants;
import com.jakebellotti.fx.ListViewModifier;
import com.jakebellotti.model.movie.MovieEntryWrapper;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import jblib.javafx.JavaFXUtils;

/**
 * 
 * @author Jake Bellotti
 * @date 5-Apr-2016
 *
 */
public class TilesSmallListViewModifier extends ListViewModifier<MovieEntryWrapper> {

	@Override
	public void change(ListView<MovieEntryWrapper> listView) {
		JavaFXUtils.setListViewCellFactory(listView, (cell, movieEntry, c) -> {
			HBox box = new HBox();
			Label text = new Label(movieEntry.toString());
			
			movieEntry.getMovieEntry().getDefinition().ifPresent(def -> {
				String[] posterLocation = def.getPoster().split("/");
				String poster = posterLocation[(posterLocation.length - 1)];
				final File posterFile = new File(DataConstants.MOVIE_IMAGE_FOLDER + "/" + poster);
				if(posterFile.exists()) {
					ImageView image = new ImageView();
					image.setImage(new Image(posterFile.toURI().toString()));
//					image.setFitHeight(150);
//					image.setFitWidth(100);
					image.setFitHeight(75);
					image.setFitWidth(50);
					box.getChildren().add(image);
				}
			});
			
			
			box.getChildren().addAll(text);
			cell.setGraphic(box);
		});
	}

	@Override
	public String getName() {
		return "Tiles (Small)";
	}

}
