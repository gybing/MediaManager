package com.jakebellotti.io.scraper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import com.jakebellotti.Constants;
import com.jakebellotti.io.Logger;
import com.jakebellotti.model.movie.MovieEntry;

/**
 * 
 * @author Jake Bellotti
 * @date 4-May-2016
 *
 */
public class OMDBAPIMovieParams {
	
	private final String title;
	private final ScraperFileReturnType returnType = ScraperFileReturnType.JSON;
	private final PlotLength plotLength = PlotLength.FULL;
	
	private static final Logger logger = new Logger(OMDBAPIMovieParams.class);
	
	private OMDBAPIMovieParams(String title) {
		this.title = title;
	}
	
	public static final OMDBAPIMovieParams create(MovieEntry entry) {
		return new OMDBAPIMovieParams(entry.getExtractedMovieName());
	}
	
	public Optional<URL> generateURL() {
		try {
			StringBuilder words = new StringBuilder();
			for(String s: title.split(" ")) {
				words.append(s + "+");
			}
			final String urlString = "http://"+ Constants.OMDBAPI_WEBSITE + "/?t=" + words.toString()+ "&plot=" +plotLength.toString() + "&r=" + returnType.toString();
			URL returnURL = new URL(urlString);
			return Optional.of(returnURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
