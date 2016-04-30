package test;

import com.jakebellotti.fx.titlebar.MenuBarStage;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author Jake Bellotti
 * @date Apr 25, 2016
 */
public class MenuBarTest extends Application {
	
	public static void main(String[] arguments) {
		Application.launch(arguments);
	}

	@Override
	public void start(Stage newStage) throws Exception {
		//TODO load
		Region root = MainWindowFrame.getLoader().load(MainWindowFrame.class.getResource("MainWindowFrame.fxml").openStream());
		//MenuBarStage stage = new MenuBarStage(newStage, new Button("Click"));
		MenuBarStage stage = new MenuBarStage(newStage, root);
		stage.show();
	}

}
