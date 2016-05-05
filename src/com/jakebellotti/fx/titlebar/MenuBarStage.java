package com.jakebellotti.fx.titlebar;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Jake Bellotti
 * @date Apr 25, 2016
 */
public class MenuBarStage {

	private final Stage stage;
	private final FXMLLoader loader = new FXMLLoader();
	private boolean loaded = false;
	private final MenuBarController controller;
	
	public static double MAX_HEIGHT = 0;
	public static double MAX_WIDTH = 0;

	public MenuBarStage(Stage stage, Region root) {
		this.stage = stage;
		this.controller = new MenuBarController(stage, root);
	}

	private void checkLoad() {
		if (!this.loaded) {
			try {
				loader.setController(controller);
				Parent root = loader.load(MenuBarStage.class.getResource("MenuBar.fxml").openStream());
				stage.setScene(new Scene(root));
				loaded = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void show() {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); 
		MAX_HEIGHT = screenBounds.getHeight(); 
		MAX_WIDTH = screenBounds.getWidth();
		
		checkLoad();
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.sizeToScene();
		stage.show();
		this.controller.centreOnScreen();
	}

}
