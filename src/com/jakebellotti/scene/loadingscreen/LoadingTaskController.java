package com.jakebellotti.scene.loadingscreen;

import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public abstract class LoadingTaskController {

	private final ArrayList<LoadingTask> tasks;
	private final Stage stage;

	public LoadingTaskController(Stage stage, ArrayList<LoadingTask> tasks) {
		this.stage = stage;
		this.tasks = tasks;
	}

	@FXML
	private Button cancelButton;

	@FXML
	private ProgressBar loadingBar;

	@FXML
	private ListView<String> finishedTaskListView;

	@FXML
	private Label currentTaskDescriptionLabel;

	@FXML
	private Label taskStatusLabel;
	
	private boolean canCancel = true;
	private LoadingTask currentLoadingTask;

	int totalTasks = 0;
	int currentTaskNumber = 1;
	
	public void tasksCompleted(Stage stage) {
		
	}

	@FXML
	public void initialize() {
		this.cancelButton.setOnMouseClicked(this::cancelButtonClicked);
		totalTasks = tasks.size();
	}

	public void performTasks() {
		totalTasks = tasks.size();
		executeTask(1);
	}
	
	private void executeTask(int taskNumber) {
		if((taskNumber -1) == totalTasks) {
			this.taskStatusLabel.setText("Done");
			this.currentTaskDescriptionLabel.setText("");
			this.canCancel = true;
			tasksCompleted(stage);
			return;
		}
		
		LoadingTask currentTask = tasks.get(taskNumber -1);
		this.currentLoadingTask = currentTask;
		this.canCancel = currentLoadingTask.canCancel();
		this.taskStatusLabel.setText("Task " + currentTaskNumber + " of " + totalTasks);
		this.currentTaskDescriptionLabel.setText(currentTask.taskDescription());
		
		Task<Void> task = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				currentTask.doTask();
				return null;
			}};
			
		task.setOnSucceeded(e -> {
			double progressPerTask = new Double(1) / new Double(this.totalTasks);
			double newProgress = (progressPerTask * taskNumber);
			this.loadingBar.setProgress(newProgress);
			finishedTaskListView.getItems().add(currentTask.getFinishText());
			finishedTaskListView.scrollTo(currentTask.getFinishText());
			currentTaskNumber++;
			executeTask(currentTaskNumber);
		});
		
		Thread thread = new Thread(task);
		thread.start();
	}

	private void cancelButtonClicked(MouseEvent e) {
		if (canCancel) {
			currentLoadingTask.onCancel();
		}
			
	}

}
