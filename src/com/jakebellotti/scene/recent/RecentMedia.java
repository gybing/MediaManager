package com.jakebellotti.scene.recent;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jake Bellotti
 * @since 13 Jun 2016
 */
public class RecentMedia {
	
	private static RecentMediaController controller;

	public static AnchorPane getAnchorPane() {
		try {
			controller = new RecentMediaController();
			FXMLLoader loader = new FXMLLoader();
			loader.setController(controller);
			return loader.load(RecentMedia.class.getResource("RecentMedia.fxml").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static RecentMediaController getController() {
		return controller;
	}
	
}
