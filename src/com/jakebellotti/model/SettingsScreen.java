package com.jakebellotti.model;

import javafx.fxml.FXMLLoader;

import javafx.scene.layout.AnchorPane;

/**
 * Represents a screen in the Settings window. Abstraction is used here to
 * easier identify when properties have been changed since opening the screen,
 * and lets us determine whether or not there should be a prompt to save the
 * settings when the user clicks away.
 * Width: 600px
 * Height: 520px
 * @author Jake Bellotti
 * @date Apr 14, 2016
 */
public abstract class SettingsScreen {
	
	protected final FXMLLoader loader = new FXMLLoader();
	protected AnchorPane scene = null;
	
	private boolean settingsModified = false;
	/**
	 * 
	 * @return Whether or not the settings were changed since this screen has been opened.
	 */
	public boolean settingsWereModified() {
		return settingsModified;
	}
	/**
	 * 
	 * @param flag
	 */
	public void setSettingsModified(boolean flag) {
		this.settingsModified = flag;
	}
	
	public void saveSettings() {
		saveSettingsLogic();
		setSettingsModified(false);
	}
	/**
	 * Performs the logic of saving the settings (should only be done when saving settings).
	 */
	public abstract void saveSettingsLogic();
	/**
	 * 
	 * @return The name of this screen.
	 */
	public abstract String getName();
	
	public abstract AnchorPane getScene();
	
	/**
	 * Returns the name of the screen.
	 */
	@Override
	public String toString() {
		return getName();
	}

}
