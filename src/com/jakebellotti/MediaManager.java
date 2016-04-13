package com.jakebellotti;

import java.util.Optional;

import com.jakebellotti.io.DataScraper;
import com.jakebellotti.io.DatabaseConnection;
import com.jakebellotti.scene.launcher.Launcher;

import javafx.stage.Stage;

/**
 * A central location for the parts of this program.
 * @author Jake Bellotti
 * @date Feb 21, 2016
 */

public class MediaManager {
	
	private static DataScraper definitionRetriever;
	private static DatabaseConnection database = new DatabaseConnection();
	private static Stage mainFrameStage = null;
	
	public static void main(String[] arguments) {
		Launcher.main(arguments);
	}

	public static Optional<DataScraper> getDefinitionRetriever() {
		return Optional.ofNullable(definitionRetriever);
	}

	public static void setDefinitionRetriever(DataScraper definitionRetriever) {
		if(definitionRetriever != null) {
			MediaManager.definitionRetriever = definitionRetriever;
		}
	}
	
	public static void connectDatabase() {
		database.connect();
	}

	/**
	 * @return the mainFrameStage
	 */
	public static Stage getMainFrameStage() {
		return mainFrameStage;
	}

	/**
	 * @param mainFrameStage the mainFrameStage to set
	 */
	public static void setMainFrameStage(Stage mainFrameStage) {
		MediaManager.mainFrameStage = mainFrameStage;
	}

}
