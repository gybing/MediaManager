package test;

import com.jakebellotti.MediaManager;
import com.jakebellotti.scene.tvseries.TVSeriesView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

/**
 *
 * @author Jake Bellotti
 * @since Jun 14, 2016
 */
public class AntiAliasTVSeriesView extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		MediaManager.getDatabase().connect();
		MediaManager.getMediaRepository().addTVSeriesEntries(MediaManager.getDatabase().getAllTVSeriesEntries());
		stage.setScene(new Scene(TVSeriesView.getAnchorPane(), 1300, 685, false, SceneAntialiasing.BALANCED));
		stage.show();
	}
	
}
