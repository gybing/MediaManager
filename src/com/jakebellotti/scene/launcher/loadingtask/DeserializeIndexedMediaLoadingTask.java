package com.jakebellotti.scene.launcher.loadingtask;

import com.jakebellotti.scene.loadingscreen.LoadingTask;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class DeserializeIndexedMediaLoadingTask implements LoadingTask {

	@Override
	public String taskDescription() {
		return "Loading indexed media entries...";
	}

	@Override
	public String getFinishText() {
		//loaded x movie entries, tv series entries, music entries
		return "Loaded indexed media.";
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
