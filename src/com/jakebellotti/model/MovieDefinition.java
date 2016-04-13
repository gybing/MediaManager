package com.jakebellotti.model;

/**
 * Defines a movie.
 * @author Jake Bellotti
 * @date Dec 13, 2015
 */

public class MovieDefinition {

	private final String title;
	private final int year;
	private final String rated;
	private final String released;
	private final String runtime;
	private final String genre;
	private final String director;
	private final String writer;
	private final String actors;
	private final String plot;
	private final String language;
	private final String country;
	private final String awards;
	private final String poster;
	private final int metascore;
	private final double imdbRating;
	private final String imdbVotes;
	private final String imdbID;

	public MovieDefinition(String title, int year, String rated, String released, String runtime, String genre,
			String director, String writer, String actors, String plot, String language, String country, String awards,
			String poster, int metascore, double imdbRating, String imdbVotes, String imdbID) {
		this.title = title;
		this.year = year;
		this.rated = rated;
		this.released = released;
		this.runtime = runtime;
		this.genre = genre;
		this.director = director;
		this.writer = writer;
		this.actors = actors;
		this.plot = plot;
		this.language = language;
		this.country = country;
		this.awards = awards;
		this.poster = poster;
		this.metascore = metascore;
		this.imdbRating = imdbRating;
		this.imdbVotes = imdbVotes;
		this.imdbID = imdbID;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public String getRated() {
		return rated;
	}

	public String getReleased() {
		return released;
	}

	public String getRuntime() {
		return runtime;
	}

	public String getGenre() {
		return genre;
	}

	public String getDirector() {
		return director;
	}

	public String getWriter() {
		return writer;
	}

	public String getActors() {
		return actors;
	}

	public String getPlot() {
		return plot;
	}

	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}

	public String getAwards() {
		return awards;
	}

	public String getPoster() {
		return poster;
	}

	public int getMetascore() {
		return metascore;
	}

	public double getImdbRating() {
		return imdbRating;
	}

	public String getImdbVotes() {
		return imdbVotes;
	}

	public String getImdbID() {
		return imdbID;
	}

}
