package com.jakebellotti;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Jake Bellotti
 * @date Feb 14, 2016
 */

public class MainFrame extends Application {
	
	private static final FXMLLoader loader = new FXMLLoader();
	private static Stage mainFrameStage = null;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		mainFrameStage = stage;
		final Parent root = loader.load(MainFrame.class.getResource("MainWindowFrame.fxml").openStream());
		mainFrameStage.setScene(new Scene(root));
		mainFrameStage.show();
	}

}
