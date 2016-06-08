package com.jakebellotti.io.scraper.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jakebellotti.io.Logger;
import com.jakebellotti.io.scraper.DataScraper;
import com.jakebellotti.io.scraper.OMDBAPIMovieParams;
import com.jakebellotti.model.ReturnResultWrapper;
import com.jakebellotti.model.ReturnStatus;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.NewMovieDefinition;

/**
 * Retrieves a movie definition from OMDBApi.com
 * 
 * 
 * @author Jake Bellotti
 * @date 3-May-2016
 *
 */
public class OMDBMovieScraper
		implements DataScraper<MovieEntry, ReturnResultWrapper<ReturnStatus, NewMovieDefinition>> {
	// http://www.omdbapi.com/?t=the+shawshank+redemption&y=&plot=short&r=json

	private static final Logger logger = new Logger(OMDBMovieScraper.class);

	@Override
	public Optional<ReturnResultWrapper<ReturnStatus, NewMovieDefinition>> scrapeData(MovieEntry parameter) {
		OMDBAPIMovieParams params = OMDBAPIMovieParams.create(parameter);
		final Optional<URL> generatedURL = params.generateURL();
		final StringBuilder json = new StringBuilder();
		if (!generatedURL.isPresent()) {
			return Optional.empty();
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(generatedURL.get().openStream()))) {
			String inputLine;
			while (((inputLine = in.readLine()) != null))
				json.append(inputLine);
		} catch (IOException e) {
			logger.println(e.getMessage());
			return Optional.ofNullable(new ReturnResultWrapper<ReturnStatus, NewMovieDefinition>(
					() -> "Connection timed out. Check that you have an active internet connection."));
		}
		return this.parseMovieDefinitionFromJSON(json.toString());
	}

	public Optional<ReturnResultWrapper<ReturnStatus, NewMovieDefinition>> parseMovieDefinitionFromJSON(String json) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(json);

			final boolean response = Boolean.parseBoolean((String) object.get("Response"));

			if (!response) {
				return Optional.ofNullable(new ReturnResultWrapper<ReturnStatus, NewMovieDefinition>(
						() -> "No definition was found for this movie. First check that the name is correct, then if no results are still returned, try using a different scraper or editing the data manually."));
			}

			// TODO make this code error free
			final String title = (String) object.get("Title");
			int year = -1;
			try {
				year = Integer.parseInt((String) object.get("Year"));
			} catch (Exception e) {
			}

			final String rated = (String) object.get("Rated");
			final String released = (String) object.get("Released");
			final String runtime = (String) object.get("Runtime");
			final String genre = (String) object.get("Genre");
			final String director = (String) object.get("Director");
			final String writer = (String) object.get("Writer");
			final String actors = (String) object.get("Actors");
			final String plot = (String) object.get("Plot");
			final String language = (String) object.get("Language");
			final String country = (String) object.get("Country");
			final String awards = (String) object.get("Awards");
			final String poster = (String) object.get("Poster");
			int metascore = -1;
			try {
				metascore = Integer.parseInt((String) object.get("Metascore"));
			} catch (Exception e) {
			}
			double imdbRating = -1;
			try {
				imdbRating = Double.parseDouble((String) object.get("imdbRating"));
			} catch (Exception e) {
			}
			final String imdbVotes = (String) object.get("imdbVotes");
			final String imdbID = (String) object.get("imdbID");

			return Optional.ofNullable(new ReturnResultWrapper<ReturnStatus, NewMovieDefinition>(() -> "")
					.setReturnResult(new NewMovieDefinition(title, year, rated, released, runtime, genre, director,
							writer, actors, plot, language, country, awards, poster, metascore, imdbRating, imdbVotes,
							imdbID)));
		} catch (Exception e) {
			e.printStackTrace();
			logger.println(e.getMessage());
			return Optional.empty();
		}
	}

}
