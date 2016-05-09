package com.jakebellotti.scene.movie.search;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * TODO use the poster api to retrieve a poster of the correct size
 * TODO maybe make this more abstract to be used with tv series searching?
 * Represents an image in a GridView, returned from a movie search. Use this wrapper to add functionality to the search.
 * 
 * @author Jake Bellotti Date 6-Apr-2016
 *
 */
public class SearchResultWrapper {
	
	private final StackPane stackPane = new StackPane();
	private final ImageView imageView = new ImageView();
	private final String title;
	private final int year;
	private final String imdbID;
	private final String type;
	private final String poster;

	public SearchResultWrapper(String title, int year, String imdbID, String type, String poster) {
		this.title = title;
		this.year = year;
		this.imdbID = imdbID;
		this.type = type;
		this.poster = poster;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public String getImdbID() {
		return imdbID;
	}

	public String getType() {
		return type;
	}

	public String getPoster() {
		return poster;
	}

	public StackPane getStackPane() {
		return stackPane;
	}

}
