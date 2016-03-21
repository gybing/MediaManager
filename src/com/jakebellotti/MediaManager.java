package com.jakebellotti;

import java.util.Optional;

import com.jakebellotti.io.DatabaseConnection;
import com.jakebellotti.io.DefinitionRetriever;
import com.jakebellotti.scene.launcher.Launcher;

/**
 * A central location for the parts of this program.
 * @author Jake Bellotti
 * @date Feb 21, 2016
 */

public class MediaManager {
	
	private static DefinitionRetriever definitionRetriever;
	private static DatabaseConnection database = new DatabaseConnection();
	
	public static void main(String[] arguments) {
		database.connect();
		Launcher.main(arguments);
	}

	public static Optional<DefinitionRetriever> getDefinitionRetriever() {
		return Optional.ofNullable(definitionRetriever);
	}

	public static void setDefinitionRetriever(DefinitionRetriever definitionRetriever) {
		if(definitionRetriever != null) {
			MediaManager.definitionRetriever = definitionRetriever;
		}
	}

}
