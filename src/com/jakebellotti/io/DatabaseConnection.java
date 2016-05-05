package com.jakebellotti.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import com.jakebellotti.Constants;
import com.jakebellotti.MediaManager;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.NewMovieDefinition;
import com.jakebellotti.model.movie.NewMovieEntry;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * TODO this could really do with a cleanup TODO change database design to
 * delete all entries associated to a movie entry when deleted XXX Remember to
 * use ' instead of " for inserting as Derby doesn't accept it
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
				return;
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
	 * 
	 * @return A list of every movie definition in the database
	 */
	public final ArrayList<MovieDefinition> getAllMovieDefinitions() {
		final ArrayList<MovieDefinition> toReturn = new ArrayList<>();
		executeQuery("SELECT * FROM tblMovieDefinition").ifPresent(resultSet -> {
			try {
				while (resultSet.next()) {
					final int id = resultSet.getInt("ID");
					final String imdbID = resultSet.getString("imdbID");
					final String title = resultSet.getString("title");
					// TODO check that all of the names when getting from the
					// result set are correct
					int year = -1;
					try {
						year = Integer.parseInt(resultSet.getString("releaseYear"));
					} catch (Exception e) {
					}
					final String rated = resultSet.getString("rating");
					final String released = resultSet.getString("released");
					final String runtime = resultSet.getString("runtime");
					final String genre = resultSet.getString("genre");
					final String director = resultSet.getString("director");
					final String writer = resultSet.getString("writer");
					final String actors = resultSet.getString("actors");
					final String plot = resultSet.getString("plot");
					final String language = resultSet.getString("movieLanguage");
					final String country = resultSet.getString("country");
					final String awards = resultSet.getString("awards");
					final String poster = resultSet.getString("poster");
					int metascore = -1;
					try {
						metascore = Integer.parseInt(resultSet.getString("metascore"));
					} catch (Exception e) {
					}

					double imdbRating = -1;
					try {
						imdbRating = Double.parseDouble(resultSet.getString("imdbRating"));
					} catch (Exception e) {
					}
					final String imdbVotes = resultSet.getString("imdbVotes");
					final MovieDefinition movieDef = new MovieDefinition(id, title, year, rated, released, runtime,
							genre, director, writer, actors, plot, language, country, awards, poster, metascore,
							imdbRating, imdbVotes, imdbID);
					toReturn.add(movieDef);
				}
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					resultSet.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		return toReturn;
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
				resultSet.close();
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
				
				SQLInsertQueryBuilder builder = new SQLInsertQueryBuilder("tblMovieEntry");
				builder.insertString("fileLocation", entry.getFileLocation());
				builder.insertString("extractedMovieName", entry.getMovieName());
				conn.createStatement().execute(builder.generateQuery());

				ResultSet rs = conn.createStatement().executeQuery("VALUES IDENTITY_VAL_LOCAL()");
				if (rs.next()) {
					final String id = rs.getString(1);
					try {
						MovieEntry newEntry = new MovieEntry(Integer.parseInt(id), fileLocation, fileName,
								Constants.NO_MOVIE_DEFINITION);
						MediaManager.getMediaRepository().addMovieEntry(newEntry);
						added++;
					} catch (Exception e) {
						rs.close();
					}
				}
				rs.close();
			} catch (DerbySQLIntegrityConstraintViolationException e) {
			} catch (Exception e) {
				logger.println("error: " + query);
				e.printStackTrace();
			}
		}
		return added;
	}

	public final MovieDefinition addMovieDefinition(NewMovieDefinition definition) {
		int databaseID = -1;
		try {
			// TODO update if exists
			// TODO complete database so that all of the movie data can be
			// inserted
			// TODO make this more secure
			
			SQLInsertQueryBuilder queryBuilder = new SQLInsertQueryBuilder("tblMovieDefinition");
			
			queryBuilder.insertString("imdbID", definition.getImdbID());
			queryBuilder.insertString("title", definition.getTitle());
			queryBuilder.insertString("releaseYear", definition.getYear());
			queryBuilder.insertString("rating", definition.getRated());
			queryBuilder.insertString("runtime", definition.getRuntime());
			queryBuilder.insertString("genre", definition.getGenre());
			queryBuilder.insertString("director", definition.getDirector());
			queryBuilder.insertString("writer", definition.getWriter());
			queryBuilder.insertString("actors", definition.getActors());
			queryBuilder.insertString("movieLanguage", definition.getLanguage());
			queryBuilder.insertString("country", definition.getCountry());
			queryBuilder.insertString("awards", definition.getAwards());
			queryBuilder.insertString("poster", definition.getPoster());
			queryBuilder.insert("metascore", definition.getMetascore());
			queryBuilder.insertString("imdbRating", definition.getImdbRating());
			queryBuilder.insertString("imdbVotes", definition.getImdbVotes());
			queryBuilder.insertString("plot", definition.getPlot());
			conn.createStatement().execute(queryBuilder.generateQuery());

			ResultSet rs = conn.createStatement().executeQuery("VALUES IDENTITY_VAL_LOCAL()");
			if (rs.next()) {
				final String id = rs.getString(1);
				databaseID = Integer.parseInt(id);
				rs.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return MovieDefinition.fromNewDefinition(databaseID, definition);
	}

	public final void assignMovieDefinitionToEntry(final MovieEntry entry, final int newID) {
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE tblMovieEntry ");
		builder.append("SET assignedMovieDefinitionID = " + newID + " ");
		builder.append("WHERE ID = " + entry.getDatabaseID());
		this.query(builder.toString());
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

}
