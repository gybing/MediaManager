package com.jakebellotti.scene.launcher.loadingtask;

import com.jakebellotti.MediaManager;
import com.jakebellotti.io.Logger;
import com.jakebellotti.scene.loadingscreen.LoadingTask;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class DeserializeIndexedMediaLoadingTask implements LoadingTask {
	
	private static final Logger logger = new Logger(DeserializeIndexedMediaLoadingTask.class);

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
		//XXX This is where you load media entries
		logger.println("Deserializing indexed media");
		MediaManager.getMediaRepository().addMovieEntries(MediaManager.getDatabase().getAllMovieEntries());
		logger.println(MediaManager.getMediaRepository().getLoadedMovieEntries().size() + " movie entries");
	}

}
