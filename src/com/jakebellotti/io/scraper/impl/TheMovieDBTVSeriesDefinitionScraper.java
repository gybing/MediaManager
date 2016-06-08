package com.jakebellotti.io.scraper.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jakebellotti.Constants;
import com.jakebellotti.io.Logger;
import com.jakebellotti.io.scraper.DataScraper;
import com.jakebellotti.model.tvseries.TVSeriesDefinition;
import com.jakebellotti.model.tvseries.TVSeriesEntry;
import com.jakebellotti.model.tvseries.TheMovieDBSeriesSearchEntry;


/**
 *
 * @author Jake Bellotti
 * @since Jun 2, 2016
 */
public class TheMovieDBTVSeriesDefinitionScraper implements DataScraper<TVSeriesEntry, TVSeriesDefinition> {
	
	private static final Logger logger = new Logger(TheMovieDBTVSeriesDefinitionScraper.class);

	@Override
	public Optional<TVSeriesDefinition> scrapeData(TVSeriesEntry parameter) {
		final TheMovieDBSeriesSearchScraper search = new TheMovieDBSeriesSearchScraper();
		final Optional<ArrayList<TheMovieDBSeriesSearchEntry>> results = search.scrapeData(parameter);
		if(! results.isPresent())
			return Optional.empty();
		
		if(results.isPresent()) {
			final ArrayList<TheMovieDBSeriesSearchEntry> list = results.get();
			
			if(list.size() > 0) {
				final TheMovieDBSeriesSearchEntry entry = list.get(0);
				final StringBuilder builder = new StringBuilder();
				try (Scanner scanner = new Scanner(new URL(generateURL(entry)).openStream())) {
					while (scanner.hasNext())
						builder.append(scanner.nextLine());
					return parseTVSeriesDefinitionFromJSON(builder.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}			
		};
		return Optional.empty();
	}
	
	private static final String generateURL(final TheMovieDBSeriesSearchEntry parameter) {
		return "https://api.themoviedb.org/3" + parameter.getHref() + "?api_key=" + Constants.THE_MOVIE_DB_API_KEY;
	}
	
	private static Optional<TVSeriesDefinition> parseTVSeriesDefinitionFromJSON(String json) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(json);

			//TODO a bad response will return: {"status_code":34,"status_message":"The resource you requested could not be found."}
			final boolean response = Boolean.parseBoolean((String) object.get("Response"));
			//TODO make this error proof

//			if (!response) {
//				// TODO error occurred.
//				return Optional.empty();
//			}

			final int databaseID = -1;
			final String name = (String) object.get("name");
			final String firstAirDate = (String) object.get("first_air_date");
			final String lastAirDate = (String) object.get("last_air_date");
			final String homePageURL = (String) object.get("homepage");
			final String posterURL = (String) object.get("poster_path");
			final String backdropURL = (String) object.get("backdrop_path");
			final Long episodeCount = (Long) object.get("number_of_episodes");
			final Long seasonCount = (Long) object.get("number_of_seasons");
			final String overview = (String) object.get("overview");

			return Optional.ofNullable(new TVSeriesDefinition(databaseID, name, firstAirDate, lastAirDate, homePageURL, posterURL, backdropURL, Math.toIntExact(episodeCount), Math.toIntExact(seasonCount), overview));
		} catch (Exception e) {
			e.printStackTrace();
			logger.println(e.getMessage());
			return Optional.empty();
		}
	}

}
