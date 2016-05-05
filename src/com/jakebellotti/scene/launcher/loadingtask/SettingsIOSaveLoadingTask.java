package com.jakebellotti.scene.launcher.loadingtask;

import com.jakebellotti.io.Logger;
import com.jakebellotti.io.SettingsIO;
import com.jakebellotti.scene.loadingscreen.LoadingTask;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class SettingsIOSaveLoadingTask implements LoadingTask {
	
	private static final Logger logger = new Logger(SettingsIOSaveLoadingTask.class);

	@Override
	public String taskDescription() {
		return "Saving settings...";
	}

	@Override
	public String getFinishText() {
		return "Saved launcher settings.";
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
		logger.println("Saving settings");
		SettingsIO.saveSettings();
	}

}
