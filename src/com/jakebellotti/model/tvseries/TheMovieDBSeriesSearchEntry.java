package com.jakebellotti.model.tvseries;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TheMovieDBSeriesSearchEntry {

	private final String title;
	private final String releaseDate;
	private final String genres;
	private final String rating;
	private final String href;
	private final String image;
	private final String overview;

	public TheMovieDBSeriesSearchEntry(String title, String releaseDate, String genres, String rating, String href,
			String image, String overview) {
		this.title = title;
		this.releaseDate = releaseDate;
		this.genres = genres;
		this.rating = rating;
		this.image = image;
		this.overview = overview;
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getGenres() {
		return genres;
	}

	public String getRating() {
		return rating;
	}

	public String getHref() {
		return href;
	}

	public String getImage() {
		return image;
	}

	public String getOverview() {
		return overview;
	}

}
