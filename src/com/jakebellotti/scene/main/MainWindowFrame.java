package com.jakebellotti.scene.main;

import java.io.IOException;

import com.jakebellotti.MediaManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * TODO about menu
 * @author Jake Bellotti
 * @date Feb 19, 2016
 */

public class MainWindowFrame {
	
	private static final FXMLLoader loader = new FXMLLoader();
	private static MainWindowFrameController controller = new MainWindowFrameController();
	private static Menu windowMenu;
	private static Menu helpMenu;
	
	public static void load(Stage stage) {
		try {
			stage.hide();
			loader.setController(controller);
			final Parent root = loader.load(MainWindowFrame.class.getResource("MainWindowFrame.fxml").openStream());
			//stage.setScene(new Scene(root));
			
			stage.setScene(new Scene(root, 1300, 685, false, SceneAntialiasing.BALANCED));
			stage.setResizable(true);
			
			stage.show();
			stage.sizeToScene();
			stage.setMinHeight(stage.getHeight());
			stage.setMinWidth(stage.getWidth());
			
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
	        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final Menu getHelpMenu() {
		if(helpMenu == null) {
			helpMenu = new Menu("Help");
			MenuItem about = new MenuItem("About");
			about.setOnAction(MainWindowFrame::aboutAction);
			helpMenu.getItems().addAll(about);
		}
		return helpMenu;
	}
	
	/**
	 * TODO add the functionality to this
	 * TODO if we ever have anything going in the background, add error checking code to prevent errors when switching
	 * @return
	 */
	public static final Menu getWindowMenu() {
		if(windowMenu == null) {
			windowMenu = new Menu("Window");
			MenuItem recentMedia = new MenuItem("View Recent Media");
			MenuItem movieView = new MenuItem("Switch To Movie View");
			MenuItem tvSeriesView = new MenuItem("Switch To TV Series View");
			MenuItem musicView = new MenuItem("Switch To Music View");
			
			recentMedia.setOnAction(MainWindowFrame::recentMediaMenuItemAction);
			movieView.setOnAction(MainWindowFrame::movieViewMenuItemAction);
			tvSeriesView.setOnAction(MainWindowFrame::tvSeriesViewMenuItemAction);
			musicView.setOnAction(MainWindowFrame::musicViewMenuItemAction);
			
			windowMenu.getItems().addAll(recentMedia, movieView, tvSeriesView, musicView);
		}
		return windowMenu;
	}
	
	private static final void aboutAction(ActionEvent e) {
		final Stage stage = new Stage();
		stage.setScene(new Scene(AboutPage.getAnchorPane()));
		stage.sizeToScene();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(MediaManager.getMainFrameStage());
		stage.setTitle("Media Manager - About");
		stage.show();
	}
	
	private static final void recentMediaMenuItemAction(ActionEvent e) {
		controller.setAsRecentMedia();
	}
	
	private static final void movieViewMenuItemAction(ActionEvent e) {
		controller.setAsMovieView();
	}
	
	private static final void tvSeriesViewMenuItemAction(ActionEvent e) {
		controller.setAsTVSeriesView();
	}
	
	private static final void musicViewMenuItemAction(ActionEvent e) {
		
	}
	
	public static FXMLLoader getLoader() {
		return loader;
	}
	
	public static MainWindowFrameController getController() {
		return loader.getController();
	}

}
