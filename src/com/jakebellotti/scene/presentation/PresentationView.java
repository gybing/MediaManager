package com.jakebellotti.scene.presentation;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * @author Jake Bellotti
 * @date Feb 24, 2016
 */

public class PresentationView {
	
	private static Parent scene;
	private static final FXMLLoader loader = new FXMLLoader();
	
	public static AnchorPane getAnchorPane() {
		if(scene == null) {
			try {
				AnchorPane pane = loader.load(PresentationView.class.getResource("PresentationView.fxml").openStream());
				return pane;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Parent getScene() {
		if(scene == null) {
			try {
				scene = loader.load(PresentationView.class.getResource("PresentationView.fxml").openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return scene;
	}

}
