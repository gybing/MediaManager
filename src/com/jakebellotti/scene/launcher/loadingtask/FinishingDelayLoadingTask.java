package com.jakebellotti.scene.launcher.loadingtask;

import com.jakebellotti.scene.loadingscreen.LoadingTask;

/**
 * Creates a pointless 500ms delay after a LoadingScreen, so the LoadingScreen
 * does not flash on and off quickly.
 * 
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class FinishingDelayLoadingTask implements LoadingTask {

	@Override
	public String taskDescription() {
		return "Finishing up...";
	}

	@Override
	public String getFinishText() {
		return "Finished";
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
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
