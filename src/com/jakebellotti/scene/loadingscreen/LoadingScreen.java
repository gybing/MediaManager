package com.jakebellotti.scene.loadingscreen;

import java.util.ArrayList;

import javafx.application.Platform;
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
	private Stage stage = null;

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

	public void onFinish(Stage stage) {

	}

	/**
	 * Opens the LoadingScreen on top of the given stage.
	 * 
	 * @param currentStage
	 */
	public void open(Stage currentStage) {
		stage = new Stage();
		try {
			LoadingTaskController controller = new LoadingTaskController(stage, tasks) {

				@Override
				public void tasksCompleted(Stage stage) {
					onFinish(stage);
				}

			};
			loader.setController(controller);
			load();

			stage.initOwner(currentStage);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(loader.getRoot()));

			stage.setOnCloseRequest((event) -> event.consume());
			stage.addEventHandler(WindowEvent.WINDOW_SHOWN, (event) -> {
				Platform.runLater(() -> {
					controller.performTasks();
				});
			});
			stage.showAndWait();
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

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
