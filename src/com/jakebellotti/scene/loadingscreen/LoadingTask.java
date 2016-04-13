package com.jakebellotti.scene.loadingscreen;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public interface LoadingTask {
	
	/**
	 * 
	 * @return The description of what this task is doing.
	 */
	public String taskDescription();
	/**
	 * 
	 * @return The result of completing this task, which will ultimately be printed into the task history ListView.
	 */
	public String getFinishText();
	/**
	 * 
	 * @return Whether or not you can cancel this task.
	 */
	public boolean canCancel();
	/**
	 * What will happen when the user clicks to cancel this task
	 */
	public void onCancel();
	/**
	 * Performs the logic behind this task
	 */
	public void doTask();

}
