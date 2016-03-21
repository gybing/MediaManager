package com.jakebellotti;

/**
 * 
 * @author Jake Bellotti
 * @date Mar 21, 2016
 */

public class DatabaseTableConstants {
	
	/**
	 * Creates a String used to create the table to store movie entries.
	 * @return
	 */
	public static final String createMovieListEntryTable() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE tblMovieListEntry (\n");
		builder.append("ID 					INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n");
		builder.append("fileLocation		VARCHAR(100)\n");
		builder.append(")");
		return builder.toString();
	}

}
