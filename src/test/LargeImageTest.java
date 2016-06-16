package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jake Bellotti
 * @since Jun 14, 2016
 */
public class LargeImageTest extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new LargeImageTestController());
		stage.setScene(new Scene(loader.load(LargeImageTest.class.getResource("LargeImageTest.fxml").openStream())));
		stage.show();
	}

}
