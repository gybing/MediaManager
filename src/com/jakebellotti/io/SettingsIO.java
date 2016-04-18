package com.jakebellotti.io;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jakebellotti.DataConstants;
import com.jakebellotti.DefaultSettings;
import com.jakebellotti.Settings;

import jblib.io.xml.XMLWriter;

/**
 * Loads the Settings for this application into memory.
 * 
 * @author Jake Bellotti
 * @date Feb 17, 2016
 */

public class SettingsIO {
	
	private static final Logger logger = new Logger(SettingsIO.class);

	/**
	 * Loads the settings from a file, if the file happens to exist (won't exist
	 * if it's the first time the program has launched).
	 */
	public static final void loadSettings() {
		final ArrayList<String> movieFileExtensions = new ArrayList<>();
		boolean memorySaverMode = DefaultSettings.MEMORY_SAVER_MODE;
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(DataConstants.SETTINGS_FILE);
			doc.getDocumentElement().normalize();
			
			//Check memory saver mode
			Element memorySaverModeElement = (Element) doc.getElementsByTagName("memorySaverMode").item(0);
			memorySaverMode = Boolean.parseBoolean(memorySaverModeElement.getTextContent().toLowerCase());
			
			//Load movie file extensions
			Element movieFileAssociationsElement = (Element) doc.getElementsByTagName("movieFileAssociations").item(0);
			NodeList movieFileExtensionsNodeList = movieFileAssociationsElement.getElementsByTagName("extension");
			for(int index = 0; index < movieFileExtensionsNodeList.getLength(); index++) {
				Element currentMovieExtension = (Element) movieFileExtensionsNodeList.item(index);
				movieFileExtensions.add(currentMovieExtension.getTextContent());
			}
			
			//TODO apply settings
			final String[] videoFileAssociationExtensions = movieFileExtensions.stream().toArray(String[]::new);
			
			Settings.setVideoFileAssociations(videoFileAssociationExtensions);
			Settings.setMemorySaverMode(memorySaverMode);
			
			logger.println(movieFileExtensionsNodeList.getLength() + " movie extensions loaded.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the settings to file once the user has passed the Launcher screen.
	 */
	public static final void saveSettings() {
		try(XMLWriter writer = new XMLWriter(DataConstants.SETTINGS_FILE)) {
			writer.writeLine("<settings>");
			
			writer.writeLine(1, "<memorySaverMode>" + Settings.isMemorySaverMode() + "</memorySaverMode>");
			writer.writeLine(1, "<movieFileAssociations>");
			for(String ext: Settings.getVideoFileAssociations()) {
				writer.writeLine(2, "<extension>"+ ext + "</extension>");
			}
			writer.writeLine(1, "</movieFileAssociations>");
			
			writer.write("</settings>");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the default settings, which should be done if the file does not
	 * exist.
	 */
	public static final void saveDefaultSettings() {
		try(XMLWriter writer = new XMLWriter(DataConstants.SETTINGS_FILE)) {
			writer.writeLine("<settings>");
			
			writer.writeLine(1, "<memorySaverMode>" + DefaultSettings.MEMORY_SAVER_MODE + "</memorySaverMode>");
			writer.writeLine(1, "<movieFileAssociations>");
			for(String ext: DefaultSettings.DEFAULT_VIDEO_EXTENSIONS) {
				writer.writeLine(2, "<extension>"+ ext + "</extension>");
			}
			writer.writeLine(1, "</movieFileAssociations>");
			
			writer.write("</settings>");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
