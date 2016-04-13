package com.jakebellotti.scene.loadingscreen;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class LoadingScreen {

	private final FXMLLoader loader = new FXMLLoader();
	private Parent root = null;
	private final ArrayList<LoadingTask> tasks = new ArrayList<>();

	/**
	 * The default constructor, for when you want to add tasks after
	 * instantiation
	 */
	public LoadingScreen() {

	}

	public LoadingScreen(final ArrayList<LoadingTask> tasks) {
		for (LoadingTask currentTask : tasks)
			this.tasks.add(currentTask);
	}

	public LoadingScreen(final LoadingTask... tasks) {
		for (LoadingTask currentTask : tasks)
			this.tasks.add(currentTask);
	}

	/**
	 * Opens the LoadingScreen on top of the given stage.
	 * 
	 * @param currentStage
	 */
	public void open(Stage currentStage) {
		try {
			LoadingTaskController controller = new LoadingTaskController(tasks);
			loader.setController(controller);
			load();

			Stage newStage = new Stage();
			newStage.initOwner(currentStage);
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.setScene(new Scene(loader.getRoot()));
			newStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent window) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							controller.performTasks();
						}
					});
				}
			});
			newStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void load() throws Exception {
		if (root == null)
			root = loader.load(LoadingScreen.class.getResource("LoadingScreen.fxml").openStream());
	}

	public void addTask(LoadingTask task) {
		this.tasks.add(task);
	}

}
