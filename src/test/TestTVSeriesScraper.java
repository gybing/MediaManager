package test;

import java.util.ArrayList;

import com.jakebellotti.io.scraper.DataScraper;
import com.jakebellotti.io.scraper.impl.TheMovieDBSeriesSearchScraper;
import com.jakebellotti.model.tvseries.TVSeriesDefinition;
import com.jakebellotti.model.tvseries.TVSeriesEntry;
import com.jakebellotti.model.tvseries.TheMovieDBSeriesDefinitionScraper;
import com.jakebellotti.model.tvseries.TheMovieDBSeriesSearchEntry;

public class TestTVSeriesScraper {

	public static void main(String[] arguments) {
		System.out.println("Testing tv series scraper");
		final DataScraper<TVSeriesEntry, ArrayList<TheMovieDBSeriesSearchEntry>> searcher = new TheMovieDBSeriesSearchScraper();
		final DataScraper<TheMovieDBSeriesSearchEntry, TVSeriesDefinition> scraper = new TheMovieDBSeriesDefinitionScraper();

		searcher.scrapeData(new TVSeriesEntry(-1, "skins", -1)).ifPresent(list -> {
			for(TheMovieDBSeriesSearchEntry entry: list) {
				scraper.scrapeData(entry).ifPresent(definition -> {
					
				});
			}
		});
	}

}
