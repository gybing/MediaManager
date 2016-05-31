package com.jakebellotti.io.scraper;

import java.util.Optional;

/**
 * An interface for what will be used to retrieve the definitions of movies.
 * @author Jake Bellotti
 * @date Feb 21, 2016
 * 
 * @param <P> The parameters it takes
 * @param <R> The return type
 */
public interface DataScraper<P, R> {
	//TODO make this return a result wrapper class so we can know why it didn't return a result if it didn't
	
	/**
	 * 
	 * @param parameter The parameter it takes (e.g. MovieEntry).
	 * @return The definition wrapped in an Optional, or Optional.empty() if it could not be returned.
	 */
	public Optional<R> scrapeData(P parameter);

}
