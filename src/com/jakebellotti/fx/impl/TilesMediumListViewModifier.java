package com.jakebellotti.fx.impl;

import java.io.File;

import com.jakebellotti.DataConstants;
import com.jakebellotti.fx.ListViewModifier;
import com.jakebellotti.model.movie.MovieEntryWrapper;
import com.jakebellotti.scene.movie.MovieViewController;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import jblib.javafx.JavaFXUtils;

public class TilesMediumListViewModifier extends ListViewModifier<MovieEntryWrapper> {
	
	private final MovieViewController controller;
	
	public TilesMediumListViewModifier(MovieViewController controller) {
		this.controller = controller;
	}

	@Override
	public void change(ListView<MovieEntryWrapper> listView) {
		JavaFXUtils.setListViewCellFactory(listView, (cell, movieEntry, c) -> {
			HBox box = new HBox();
			Label text = new Label(movieEntry.toString());

			movieEntry.getMovieEntry().getDefinition().ifPresent(def -> {
				String[] posterLocation = def.getPoster().split("/");
				String poster = posterLocation[(posterLocation.length - 1)];
				final File posterFile = new File(DataConstants.MOVIE_IMAGE_FOLDER + "/" + poster);
				if (posterFile.exists()) {
					ImageView image = new ImageView();
					image.setImage(new Image(posterFile.toURI().toString()));
					image.setFitHeight(150);
					image.setFitWidth(100);
					box.getChildren().add(image);
				}
			});

			box.getChildren().addAll(text);
			controller.addMovieListViewContextMenu(cell);
			cell.setGraphic(box);
		});
	}

	@Override
	public String getName() {
		return "Tiles (Medium)";
	}

}
