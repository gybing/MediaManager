package com.jakebellotti.model.movie;

/**
 * 
 * @author Jake Bellotti
 * date 5-Apr-2016
 *
 */
public class MovieEntryWrapper {
	
	private final MovieEntry movieEntry;
	private boolean showLoadingImage = false;
	
	public MovieEntryWrapper(MovieEntry entry) {
		this.movieEntry = entry;
	}
	
	@Override
	public String toString() {
		return movieEntry.toString();
	}

	public boolean isShowLoadingImage() {
		return showLoadingImage;
	}

	public void setShowLoadingImage(boolean showLoadingImage) {
		this.showLoadingImage = showLoadingImage;
	}

	public MovieEntry getMovieEntry() {
		return movieEntry;
	}

}
