package com.jakebellotti.model;

import java.util.List;

/**
 * Organises a movie list.
 * 
 * @author Jake Bellotti
 * @date Mar 21, 2016
 */

public interface ListOrderer<T extends Object> {
	/**
	 * 
	 * @return The name/description of this orderer.
	 */
	public String toString();

	/**
	 * Orders the list.
	 * 
	 * @return The new, ordered list.
	 */
	public List<T> order();

}
