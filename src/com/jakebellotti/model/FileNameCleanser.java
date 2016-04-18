package com.jakebellotti.model;

/**
 * Gets a file name and cleanses it of any bad expressions.
 * 
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public abstract class FileNameCleanser {

	/**
	 * Takes one file name String as a parameter, and returns back that same
	 * String, with any bad expressions removed.
	 * 
	 * @param fileName
	 * @return
	 */
	public abstract String cleanseFileName(String fileName);
	/**
	 * Gets the name/identifier of this FileNameCleanser.
	 * @return
	 */
	public abstract String getFileCleanserName();
	
	@Override
	public String toString() {
		return getFileCleanserName();
	}

}
