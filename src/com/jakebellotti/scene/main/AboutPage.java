package com.jakebellotti.scene.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jake Bellotti
 * @since 14 Jun 2016
 */
public class AboutPage {
	
	public static final AnchorPane getAnchorPane() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setController(new AboutPageController());
			return loader.load(AboutPage.class.getResource("AboutPage.fxml").openStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
