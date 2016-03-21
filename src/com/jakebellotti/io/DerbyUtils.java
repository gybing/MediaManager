package com.jakebellotti.io;

import java.sql.SQLException;

/**
 * 
 * @author Jake Bellotti
 * @date Mar 21, 2016
 */

public class DerbyUtils {

	private static final String TABLE_ALREADY_EXISTS_ERROR_CODE = "X0Y32";
	private static final String ANOTHER_INSTANCE_RUNNING_ERROR_CODE = "XSDB6";

	/**
	 * Checks to see if the given error is because the table already exists.
	 * 
	 * @param e
	 * @return
	 */
	public static boolean tableAlreadyExists(SQLException e) {
		return e.getSQLState().equalsIgnoreCase(DerbyUtils.TABLE_ALREADY_EXISTS_ERROR_CODE);
	}

	/**
	 * Checks to see if the given error is because another instance is already
	 * running in the series of exceptions.
	 * 
	 * @param e
	 * @return
	 */
	public static boolean anotherInstanceRunningInExceptionSeries(SQLException e) {
		if (anotherInstanceRunning(e))
			return true;
		SQLException next;
		while ((next = e.getNextException()) != null) {
			if (anotherInstanceRunning(next))
				return true;
		}
		return false;
	}

	/**
	 * Checks to see if the given error is because another instance is already
	 * running.
	 * 
	 * @param e
	 * @return
	 */
	public static boolean anotherInstanceRunning(SQLException e) {
		return e.getSQLState().equalsIgnoreCase(DerbyUtils.ANOTHER_INSTANCE_RUNNING_ERROR_CODE);
	}

}
