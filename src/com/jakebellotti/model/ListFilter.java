package com.jakebellotti.model;

/**
 * Filters a movie list of any movies fitting it's criteria.
 * 
 * @author Jake Bellotti
 * @date Mar 21, 2016
 */

public interface ListFilter<T extends Object> {

	/**
	 * Whether or not the List Item should be added to the new list.
	 * 
	 * @param t
	 * @return
	 */
	public boolean shouldAdd(T listItem);

}
