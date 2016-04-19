package com.jakebellotti.model;

import java.util.List;

/**
 * Organises a movie list.
 * 
 * @author Jake Bellotti
 * @date Mar 21, 2016
 */

public abstract class ListOrderer<T extends Object> {
	/**
	 * 
	 * @return The name/description of this orderer.
	 */
	public String toString() {
		return getName();
	}
	
	public abstract String getName();

	/**
	 * Orders the list.
	 * 
	 * @return The new, ordered list.
	 */
	public abstract List<T> order(List<T> list);

}
