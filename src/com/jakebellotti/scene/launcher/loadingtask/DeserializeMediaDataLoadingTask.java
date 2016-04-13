package com.jakebellotti.scene.launcher.loadingtask;

import com.jakebellotti.scene.loadingscreen.LoadingTask;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class DeserializeMediaDataLoadingTask implements LoadingTask {

	@Override
	public String taskDescription() {
		return "Loading media data...";
	}

	@Override
	public String getFinishText() {
		//loaded x movie entries, tv series entries, music entries
		return "Loaded media data.";
	}

	@Override
	public boolean canCancel() {
		return false;
	}

	@Override
	public void onCancel() {
	}

	@Override
	public void doTask() {
		//TODO load from database
	}

}
