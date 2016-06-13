package com.jakebellotti;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import jblib.javafx.Alerts;

/**
 *
 * @author Jake Bellotti
 * @since 13 Jun 2016
 */
public class FileOpener {

	public static final boolean openMedia(final File movieFile) {
		try {
			if (!movieFile.exists()) {
				Alerts.showErrorAlert("Could not open movie", "File not found",
						"Could not open the movie because the file could not be found. Would you like to relocate the movie?");
			} else {
				Desktop.getDesktop().open(movieFile);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
