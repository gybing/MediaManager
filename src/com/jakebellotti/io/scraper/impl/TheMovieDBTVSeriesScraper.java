package com.jakebellotti.io.scraper.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jakebellotti.io.Logger;
import com.jakebellotti.io.scraper.DataScraper;
import com.jakebellotti.model.tvseries.TVSeriesDefinition;
import com.jakebellotti.model.tvseries.TVSeriesEntry;
import com.jakebellotti.model.tvseries.TheMovieDBSeriesSearchEntry;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TheMovieDBTVSeriesScraper implements DataScraper<TVSeriesEntry, ArrayList<TheMovieDBSeriesSearchEntry>> {

	private static final Logger logger = new Logger(TheMovieDBTVSeriesScraper.class);
	
	public static final String WEBSITE_URL = "http://www.themoviedb.org";
	public static final String TV_SERIES_SEARCH_URL = WEBSITE_URL + "/search/tv?query=";

	// TODO finish scraping tv series
	@Override
	public Optional<ArrayList<TheMovieDBSeriesSearchEntry>> scrapeData(TVSeriesEntry parameter) {
		final ArrayList<TheMovieDBSeriesSearchEntry> toReturn = new ArrayList<>();
		try {
			final StringBuilder builder = new StringBuilder();
			Scanner scanner = new Scanner(new File("./data/test/themoviedbtest/tvseries/search result page.html"));
			while (scanner.hasNext()) {
				builder.append(scanner.nextLine());
			}
			scanner.close();

			final String parsing = builder.toString().replace("item poster card", "item-poster-card").replace("title result", "title-result");
			Document doc = Jsoup.parse(parsing);
			Elements elements = doc.select("div.item-poster-card");
			
			for(Element e : elements) {
				Element info = e.select("div.info").get(0);
				Element imageContent = e.select("div.image_content").get(0);
				Element titleResult = info.select("a.title-result").get(0);
				
				final String title = titleResult.attributes().get("title").trim();
				final String releaseDate = info.select("span.release_date").get(0).text().trim();
				final String genres = info.select("span.genres").get(0).text().trim();
				final String rating = info.select("span.vote_average").get(0).text().trim();
				final String href = titleResult.attributes().get("href").trim();
				final String images = imageContent.select("img").get(0).attributes().get("data-srcset").split("1x,")[0].replace("1x,", "").trim();
				final String overview = info.select("p.overview").get(0).text().trim();
				
				System.out.println("Series: " +title);
				System.out.println("Href: " + WEBSITE_URL + href);
				System.out.println("Release Date: " +releaseDate);
				System.out.println("Rating: " + rating);
				System.out.println("Genres: " + genres);
				System.out.println(images);
				System.out.println(overview);
				
				System.out.println("____");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(toReturn);
	}

	private static final String generateURL(final TVSeriesEntry series) {
		return TV_SERIES_SEARCH_URL + series.getName();
	}

}
