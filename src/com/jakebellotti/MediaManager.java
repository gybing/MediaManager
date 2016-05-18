package com.jakebellotti;

import java.io.File;
import java.util.Optional;

import com.jakebellotti.io.DatabaseConnection;
import com.jakebellotti.io.SettingsIO;
import com.jakebellotti.io.scraper.DataScraper;
import com.jakebellotti.io.scraper.impl.OMDBMovieScraper;
import com.jakebellotti.lang.Language;
import com.jakebellotti.lang.LanguagePack;
import com.jakebellotti.model.MediaRepository;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.NewMovieDefinition;
import com.jakebellotti.scene.launcher.Launcher;
import com.jakebellotti.scene.main.MainWindowFrameController;

import javafx.stage.Stage;

/**
 * A central location for the components of this program.
 * 
 * @author Jake Bellotti
 * @date Feb 21, 2016
 */

public class MediaManager {

	private static DataScraper<MovieEntry, NewMovieDefinition> movieDefinitionRetriever = new OMDBMovieScraper();
	private static final DatabaseConnection database = new DatabaseConnection();
	private static final MainWindowFrameController mainController = new MainWindowFrameController();
	private static final LanguagePack language = new LanguagePack(Language.ENGLISH);
	private static final MediaRepository mediaRepository = new MediaRepository();

	private static Stage mainFrameStage = null;

	public static void main(String[] arguments) {
		File file = new File("./data/test/series/arrow_backdrop.jpg");
		System.out.println(file.toURI().toString());
		checkProgramFiles();
		loadProgramFiles();
		Launcher.main(arguments);
	}

	/**
	 * Checks to see if all of the necessary program files and directories exist
	 * before starting the program, and creates them if they don't. With this,
	 * we can be sure that the program has not been affected by tampering, and
	 * the program can run as intended.
	 */
	private static void checkProgramFiles() {
		// XXX We should check here to see if the necessary program files exist
		// and create them if we don't
		if (!DataConstants.DATA_FOLDER.exists()) {
			DataConstants.DATA_FOLDER.mkdir();
		}
		if (!DataConstants.SETTINGS_FILE.exists()) {
			SettingsIO.saveDefaultSettings();
		}
		if (!DataConstants.IMAGE_ROOT_FOLDER.exists()) {
			DataConstants.IMAGE_ROOT_FOLDER.mkdir();
		}
		if (!DataConstants.MOVIE_IMAGE_FOLDER.exists()) {
			DataConstants.MOVIE_IMAGE_FOLDER.mkdir();
		}
		if (!DataConstants.MUSIC_IMAGE_FOLDER.exists()) {
			DataConstants.MUSIC_IMAGE_FOLDER.mkdir();
		}
	}
	
	private static void loadProgramFiles() {
		SettingsIO.loadSettings();
	}

	public static void connectDatabase() {
		database.connect();
	}

	public static Optional<DataScraper<MovieEntry, NewMovieDefinition>> getMovieDefinitionRetriever() {
		return Optional.ofNullable(movieDefinitionRetriever);
	}

	public static void setMovieDefinitionRetriever(DataScraper<MovieEntry, NewMovieDefinition> definitionRetriever) {
		if (definitionRetriever != null) {
			MediaManager.movieDefinitionRetriever = definitionRetriever;
		}
	}

	/**
	 * @return the mainFrameStage
	 */
	public static Stage getMainFrameStage() {
		return mainFrameStage;
	}

	/**
	 * @param mainFrameStage
	 *            the mainFrameStage to set
	 */
	public static void setMainFrameStage(Stage mainFrameStage) {
		MediaManager.mainFrameStage = mainFrameStage;
	}

	public static DatabaseConnection getDatabase() {
		return database;
	}

	public static MainWindowFrameController getMaincontroller() {
		return mainController;
	}

	public static LanguagePack getLanguage() {
		return language;
	}

	public static MediaRepository getMediaRepository() {
		return mediaRepository;
	}

}
