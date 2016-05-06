package com.jakebellotti.fx.impl;

import java.io.File;

import com.jakebellotti.DataConstants;
import com.jakebellotti.fx.ListViewModifier;
import com.jakebellotti.model.movie.MovieEntryWrapper;

import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import jblib.javafx.JavaFXUtils;

public class TilesLargeListViewModifier extends ListViewModifier<MovieEntryWrapper> {

	@Override
	public void change(ListView<MovieEntryWrapper> listView) {
		JavaFXUtils.setListViewCellFactory(listView, (cell, movieEntry, c) -> {
			HBox box = new HBox();

			movieEntry.getMovieEntry().getDefinition().ifPresent(def -> {
				String[] posterLocation = def.getPoster().split("/");
				String poster = posterLocation[(posterLocation.length - 1)];
				final File posterFile = new File(DataConstants.MOVIE_IMAGE_FOLDER + "/" + poster);
				if (posterFile.exists()) {
					ImageView image = new ImageView();
					image.setImage(new Image(posterFile.toURI().toString()));
					image.setFitHeight(300);
					image.setFitWidth(200);
					box.getChildren().add(image);
				}
			});
			cell.setGraphic(box);
		});
	}

	@Override
	public String getName() {
		return "Tiles (Large)";
	}

}