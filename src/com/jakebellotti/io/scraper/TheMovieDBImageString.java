package com.jakebellotti.io.scraper;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 * https://image.tmdb.org/t/p/w185/mE2U4Z2Bh9XE4mL4vKdSHIDfLPN.jpg
 */
public class TheMovieDBImageString {
	
	private final String url;
	
	public TheMovieDBImageString(final String url) {
		this.url = url;
	}
	
	public void setResolution(int resolution) {
		if(resolution > 1000)
			resolution = 1000;
		if(resolution < 1)
			resolution = 1;
	}

	public String getUrl() {
		return url;
	}

}
