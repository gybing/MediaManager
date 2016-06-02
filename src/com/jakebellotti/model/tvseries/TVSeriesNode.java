package com.jakebellotti.model.tvseries;

/**
 * 
 * @author Jake Bellotti
 * @since 30 May, 2016
 *
 */
public abstract class TVSeriesNode {
	
	public abstract String getDisplayName();

	@Override
	public String toString() {
		return getDisplayName();
	}

}
