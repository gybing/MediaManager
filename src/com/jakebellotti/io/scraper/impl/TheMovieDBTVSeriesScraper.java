package com.jakebellotti.io.scraper.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

import com.jakebellotti.io.Logger;
import com.jakebellotti.io.scraper.DataScraper;
import com.jakebellotti.model.tvseries.TVSeriesDefinition;
import com.jakebellotti.model.tvseries.TVSeriesEntry;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TheMovieDBTVSeriesScraper implements DataScraper<TVSeriesEntry, TVSeriesDefinition> {

	private static final Logger logger = new Logger(TheMovieDBTVSeriesScraper.class);
	public static final String TV_SERIES_SEARCH_URL = "http://www.themoviedb.org/search/tv?query=";

	// TODO finish scraping tv series
	@Override
	public Optional<TVSeriesDefinition> scrapeData(TVSeriesEntry parameter) {
		
		try {
			final URL generatedURL = new URL("http://omdbapi.com");
			try (BufferedReader in = new BufferedReader(new InputStreamReader(generatedURL.openStream()))) {
				String inputLine;
				while (((inputLine = in.readLine()) != null)) {
					System.out.println(inputLine);
				}
			} catch (IOException e) {
				logger.println(e.getMessage());
				return Optional.empty();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}

	private static final String generateURL(final TVSeriesEntry series) {
		return TV_SERIES_SEARCH_URL + series.getName();
	}

}
