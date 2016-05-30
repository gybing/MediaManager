package com.jakebellotti.scene.tvseries;

import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class TVSeriesView {
	
	private static AnchorPane scene;
	private static final FXMLLoader loader = new FXMLLoader();
	private static TVSeriesViewController controller;
	
	public static AnchorPane getAnchorPane() {
		if(scene == null) {
			try {
				controller = new TVSeriesViewController();
				loader.setController(controller);
				
				scene = loader.load(TVSeriesViewController.class.getResource("TvSeriesView.fxml").openStream());
				return scene;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return scene;
	}
	
	public static TVSeriesViewController getController() {
		getAnchorPane();
		return controller;
	}

}
