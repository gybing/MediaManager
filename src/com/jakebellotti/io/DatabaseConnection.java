package com.jakebellotti.io;

import java.io.File;
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
import com.jakebellotti.MediaManager;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.NewMovieDefinition;
import com.jakebellotti.model.movie.NewMovieEntry;
import com.jakebellotti.model.tvseries.TVSeriesDefinition;
import com.jakebellotti.model.tvseries.TVSeriesEntry;
import com.jakebellotti.model.tvseries.TVSeriesEpisode;
import com.jakebellotti.model.tvseries.TVSeriesEpisodeDefinition;
import com.jakebellotti.model.tvseries.TVSeriesSeason;
import com.jakebellotti.scene.movie.add.MovieDirectoryEntry;

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
	
	public boolean insertTVSeries(final String name) {
		final String query = "INSERT INTO tblTVSeriesEntry(seriesName) VALUES(?)";
		try(PreparedStatement s = conn.prepareStatement(query)) {
			s.setString(1, name);
			
			final boolean result = (s.executeUpdate() > 0);
			
			final int lastInsertID = this.getLastInsertID();
			MediaManager.getMediaRepository().addTVSeriesEntry(new TVSeriesEntry(lastInsertID, name, 0));
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public final ArrayList<TVSeriesEntry> getAllTVSeriesEntries() {
		final ArrayList<TVSeriesEntry> toReturn = new ArrayList<>();
		try (Statement s = conn.createStatement()) {
			final ResultSet set = s.executeQuery("SELECT * FROM tblTVSeriesEntry");

			while (set.next()) {
				final int id = set.getInt("ID");
				final String seriesName = set.getString("seriesName");
				final int assignedTVSeriesDefinitionID = set.getInt("assignedTVSeriesDefinitionID");
				final TVSeriesEntry entry = new TVSeriesEntry(id, seriesName, assignedTVSeriesDefinitionID);
				entry.getSeasons().addAll(getTvSeriesSeasons(entry));
				toReturn.add(entry);
			}

			set.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public final ArrayList<TVSeriesDefinition> getAllTVSeriesDefinitions() {
		final ArrayList<TVSeriesDefinition> toReturn = new ArrayList<>();
		try (Statement s = conn.createStatement()) {
			final String query = "SELECT * FROM tblTVSeriesDefinition";
			final ResultSet set = s.executeQuery(query);

			while (set.next()) {
				final int databaseID = set.getInt("ID");
				final String name = set.getString("name");
				final String firstAirDate = set.getString("firstAirDate");
				final String lastAirDate = set.getString("lastAirDate");
				final String homePageURL = set.getString("homePageURL");
				final String posterURL = set.getString("posterURL");
				final String backdropURL = set.getString("backdropURL");
				final int episodeCount = set.getInt("episodeCount");
				final int seasonCount = set.getInt("seasonCount");
				final String overview = set.getString("overview");

				toReturn.add(new TVSeriesDefinition(databaseID, name, firstAirDate, lastAirDate, homePageURL, posterURL,
						backdropURL, episodeCount, seasonCount, overview));
			}
			set.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public final ArrayList<TVSeriesSeason> getTvSeriesSeasons(final TVSeriesEntry entry) {
		final ArrayList<TVSeriesSeason> toReturn = new ArrayList<>();
		final String query = "SELECT * FROM tblTVSeriesSeason WHERE tvSeriesEntryID = " + entry.getDatabaseID();
		try (Statement s = conn.createStatement()) {

			final ResultSet set = s.executeQuery(query);

			while (set.next()) {
				final int id = set.getInt("ID");
				final int seasonNumber = set.getInt("seasonNumber");
				final int episodeCount = set.getInt("episodeCount");
				final int theMovieDBID = set.getInt("themoviedbID");
				final String posterURL = set.getString("posterURL");
				final int tvSeriesEntryID = set.getInt("tvSeriesEntryID");
				final String overview = set.getString("overview");
				final TVSeriesSeason season = new TVSeriesSeason(id, seasonNumber, episodeCount, theMovieDBID,
						posterURL, tvSeriesEntryID, overview);
				season.getEpisodes().addAll(getTvSeriesSeasonEpisodes(season));
				toReturn.add(season);
			}

			set.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	/**
	 * Retrieves an ArrayList of TVSeriesEpisodes for the given TVSeriesSeason.
	 * 
	 * @param entry
	 * @return
	 */
	public final ArrayList<TVSeriesEpisode> getTvSeriesSeasonEpisodes(final TVSeriesSeason entry) {
		final ArrayList<TVSeriesEpisode> toReturn = new ArrayList<>();
		final String query = "SELECT * FROM tblTVSeriesEpisode WHERE tvSeriesSeasonID = " + entry.getDatabaseID();
		try (Statement s = conn.createStatement()) {
			final ResultSet set = s.executeQuery(query);

			while (set.next()) {
				final int id = set.getInt("ID");
				final String fileLocation = set.getString("fileLocation");
				final int episodeNumber = set.getInt("episodeNumber");
				final int tvSeriesSeasonID = set.getInt("tvSeriesSeasonID");
				final int tvSeriesEpisodeDefinitionID = set.getInt("tvSeriesEpisodeDefinitionID");
				toReturn.add(new TVSeriesEpisode(id, fileLocation, episodeNumber, tvSeriesSeasonID,
						tvSeriesEpisodeDefinitionID));
			}
			set.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public final ArrayList<TVSeriesEpisodeDefinition> getAllTVSeriesEpisodeDefinitions() {
		final ArrayList<TVSeriesEpisodeDefinition> toReturn = new ArrayList<>();
		try (Statement s = conn.createStatement()) {
			final String query = "SELECT * FROM tblTVSeriesEpisodeDefinition";
			final ResultSet set = s.executeQuery(query);

			while (set.next()) {
				final int databaseID = set.getInt("ID");
				final String name = set.getString("name");
				final String overview = set.getString("overview");
				final int themoviedbID = set.getInt("themoviedbID");
				final String stillImageURL = set.getString("stillImageURL");
				toReturn.add(new TVSeriesEpisodeDefinition(databaseID, name, overview, themoviedbID, stillImageURL));
			}

			set.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
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
		SQLInsertQueryBuilder builder = new SQLInsertQueryBuilder("tblMovieEntry");
		int added = 0;
		for (NewMovieEntry entry : newEntries) {
			try {
				final String fileLocation = entry.getFileLocation().getAbsolutePath().replace("'", "''");
				final String fileName = entry.getMovieName().replace("'", "''");

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
				logger.println("error: " + builder.generateQuery());
				e.printStackTrace();
			} finally {
				builder = new SQLInsertQueryBuilder("tblMovieEntry");
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
			queryBuilder.insertString("released", definition.getReleased());
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

	public int getLastInsertID() {
		int returnID = -1;
		try (ResultSet rs = conn.createStatement().executeQuery("VALUES IDENTITY_VAL_LOCAL()")) {
			if (rs.next()) {
				final String id = rs.getString(1);
				returnID = Integer.parseInt(id);
				rs.close();
				return returnID;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnID;
	}

	public boolean addMovieDirectoryEntry(final MovieDirectoryEntry entry) {
		final String query = "INSERT INTO tblMovieDirectoryEntry(directoryLocation, removableDirectory, scanOnStartup, scanSubdirectories) VALUES(?, ?, ?, ?)";
		try (PreparedStatement s = conn.prepareStatement(query)) {
			s.setString(1, entry.getDirectory().toString());
			s.setBoolean(2, entry.isRemovableMedia());
			s.setBoolean(3, entry.isScanOnStartup());
			s.setBoolean(4, entry.isScanSubdirectories());
			return s.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Given a list of files, returns a list of movie entries that match the
	 * file names of the given files.
	 * 
	 * @param files
	 * @return
	 */
	public ArrayList<String> getAddedFileNames(final ArrayList<File> files) {
		final ArrayList<String> toReturn = new ArrayList<>();
		try (Statement s = conn.createStatement()) {
			String query = "SELECT fileLocation FROM tblMovieEntry WHERE fileLocation IN (";

			String adding = "";
			for (int i = 0; i < files.size(); i++) {
				adding += "'" + files.get(i).getPath() + "'";
				if (i != files.size() - 1) {
					adding += ",";
				}
			}

			query = query + adding + ")";

			final ResultSet set = s.executeQuery(query);
			while (set.next()) {
				toReturn.add(set.getString("fileLocation"));
			}

			set.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO finish
		return toReturn;
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
