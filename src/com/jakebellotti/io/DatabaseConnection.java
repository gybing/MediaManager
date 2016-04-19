package com.jakebellotti.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import com.jakebellotti.Constants;
import com.jakebellotti.model.MediaRepository;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.NewMovieEntry;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Jake Bellotti
 * @date Mar 21, 2016
 */

public class DatabaseConnection {

	private static final String DATABASE_LOC = "./data/db/";
	private static final Logger logger = new Logger(DatabaseConnection.class);
	private Connection conn;

	public void connect() {
		try {
			DriverManager.registerDriver(new EmbeddedDriver());
			conn = DriverManager.getConnection(getConnectionString());
			createRequiredTables();
		} catch (SQLException e) {
			if (DerbyUtils.anotherInstanceRunningInExceptionSeries(e)) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Another instance running");
				errorAlert.setContentText("Another instance of this program is running. Closing program now.");
				errorAlert.showAndWait();
				Platform.exit();
			}
			e.printStackTrace();
		}
	}

	/**
	 * Creates the required tables for this program to run.
	 * 
	 * @return
	 */
	public final void createRequiredTables() {
		// TODO properly create all tables
		// logger.println(createTable(DatabaseTableConstants.createMovieListEntryTable()));
	}

	/**
	 * Loads all of the Movie Entry objects from the database.
	 * 
	 * @return
	 */
	public final ArrayList<MovieEntry> getAllMovieEntries() {
		final ArrayList<MovieEntry> toReturn = new ArrayList<>();
		executeQuery("SELECT * FROM tblMovieEntry").ifPresent(resultSet -> {
			try {
				while (resultSet.next()) {
					int id = resultSet.getInt("ID");
					String fileLocation = resultSet.getString("fileLocation");
					String extractedMovieName = resultSet.getString("extractedMovieName");
					int movieDefID = resultSet.getInt("assignedMovieDefinitionID");
					toReturn.add(new MovieEntry(id, fileLocation, extractedMovieName, movieDefID));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return toReturn;
	}

	public final int addMovieEntries(List<NewMovieEntry> newEntries) {
		int added = 0;
		for (NewMovieEntry entry : newEntries) {
			String query = "";
			try {
				final String fileLocation = entry.getFileLocation().getAbsolutePath().replace("'", "''");
				final String fileName = entry.getMovieName().replace("'", "''");
				query = "INSERT INTO tblMovieEntry(fileLocation, extractedMovieName) VALUES('" + fileLocation + "', '"
						+ fileName + "')";
				conn.createStatement().execute(query);

				ResultSet rs = conn.createStatement().executeQuery("VALUES IDENTITY_VAL_LOCAL()");
				if (rs.next()) {
					final String id = rs.getString(1);
					try {
						MovieEntry newEntry = new MovieEntry(Integer.parseInt(id), fileLocation, fileName, Constants.NO_MOVIE_DEFINITION);
						MediaRepository.addMovieEntry(newEntry);
						added++;
					} catch (Exception e) {
					}
				}
			} catch (DerbySQLIntegrityConstraintViolationException e) {
			} catch (Exception e) {
				logger.println("error: " + query);
				e.printStackTrace();
			}
		}
		return added;
	}

	/**
	 * A method made specifically for creating tables, will check to see if they
	 * exist.
	 * 
	 * @param table
	 * @return
	 */
	public boolean createTable(String tableDeclaration) {
		try {
			conn.createStatement().execute(tableDeclaration);
			return true;
		} catch (SQLException e) {
			if (DerbyUtils.tableAlreadyExists(e))
				return false;
			e.printStackTrace();
			return false;
		}
	}

	public boolean query(String query) {
		try {
			return conn.createStatement().execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Optional<ResultSet> executeQuery(String query) {
		try {
			ResultSet results = conn.createStatement().executeQuery(query);
			return Optional.of(results);
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public String getConnectionString() {
		return "jdbc:derby:" + DATABASE_LOC + ";create=true";
	}

	public String getShutdownString() {
		return "jdbc:derby:;shutdown=true";
	}

	// executeQuery("SELECT * FROM SYS.SYSTABLES").ifPresent(rs->{
	// System.out.println("Got result set.");
	// try {
	// while(rs.next()) {
	//
	// System.out.println(rs.getString(2));
	// }
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	// });;

}
