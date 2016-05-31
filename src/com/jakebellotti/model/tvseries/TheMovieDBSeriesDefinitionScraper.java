package com.jakebellotti.model.tvseries;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.jakebellotti.io.scraper.DataScraper;

/**
 * Brings back the tv series definition for the given search entry. 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TheMovieDBSeriesDefinitionScraper implements DataScraper<TheMovieDBSeriesSearchEntry, TVSeriesDefinition> {
	
	private static final String API_KEY = "16a9e51e9e38bd1f4aa3b18759d4d611";
	private static final String EXAMPLE_REQUEST = "https://api.themoviedb.org/3/movie/550?api_key=16a9e51e9e38bd1f4aa3b18759d4d611";

	@Override
	public Optional<TVSeriesDefinition> scrapeData(TheMovieDBSeriesSearchEntry parameter) {
		//TODO finish scraping the data
		//TODO make it retrieve from the url instead of the testing file
		try {
			final StringBuilder builder = new StringBuilder();
			Scanner scanner = new Scanner(new File("./data/test/themoviedbtest/tvseries/tv series page.html"));
			while (scanner.hasNext()) {
				builder.append(scanner.nextLine());
			}
			scanner.close();

			final String parsing = builder.toString();
			
			Document doc = Jsoup.parse(parsing);
			Elements elements = doc.select("div.item-poster-card");
			
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return Optional.empty();
	}

}
