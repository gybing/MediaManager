package com.jakebellotti.scene.launcher.loadingtask;

import com.jakebellotti.GroovyLoader;
import com.jakebellotti.scene.loadingscreen.LoadingTask;

/**
 *
 * @author Jake Bellotti
 * @since Jun 21, 2016
 */
public class GroovyScriptsLoadingTask implements LoadingTask {

	@Override
	public String taskDescription() {
		return "Loading Groovy scripts...";
	}

	@Override
	public String getFinishText() {
		return "Finished loading scripts.";
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
		GroovyLoader.load();
	}

}
