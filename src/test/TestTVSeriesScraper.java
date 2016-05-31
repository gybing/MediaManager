package test;

import com.jakebellotti.io.scraper.DataScraper;
import com.jakebellotti.io.scraper.impl.TheMovieDBTVSeriesScraper;
import com.jakebellotti.model.tvseries.TVSeriesDefinition;
import com.jakebellotti.model.tvseries.TVSeriesEntry;

public class TestTVSeriesScraper {
	
	public static void main(String[] arguments) {
		System.out.println("Testing tv series scraper");
		final DataScraper<TVSeriesEntry, TVSeriesDefinition> scraper = new TheMovieDBTVSeriesScraper();
		
		scraper.scrapeData(new TVSeriesEntry(-1, "skins", -1));
	}

}
