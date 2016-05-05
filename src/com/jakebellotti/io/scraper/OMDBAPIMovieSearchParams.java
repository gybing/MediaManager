package com.jakebellotti.io.scraper;

import java.net.URL;
import java.util.Optional;

import com.jakebellotti.model.movie.MovieEntry;

/**
 * Parameters to be used when scraping data from omdbapi.com
 * http://www.omdbapi.com/?s=Batman&page=2
 * @author Jake Bellotti
 * @date Apr 12, 2016
 */
public class OMDBAPIMovieSearchParams {

	private final String title;
	private final MediaType mediaType;
	private final ScraperFileReturnType returnType = ScraperFileReturnType.JSON;
	private final PlotLength plotLength = PlotLength.FULL;
	private final String year;
	private final String page;

	private OMDBAPIMovieSearchParams(MediaType mediaType, String title, String year, String page) {
		this.mediaType = mediaType;
		this.title = title;
		this.year = year;
		this.page = page;
	}
	
	public static final OMDBAPIMovieSearchParams createMovieSearchParams(MovieEntry entry) {
		return null;
	}
	
	public Optional<URL> generateURL() {
		//TODO generate the URL
		return Optional.empty();
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the mediaType
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	/**
	 * @return the returnType
	 */
	public ScraperFileReturnType getReturnType() {
		return returnType;
	}

	/**
	 * @return the plotLength
	 */
	public PlotLength getPlotLength() {
		return plotLength;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

}
