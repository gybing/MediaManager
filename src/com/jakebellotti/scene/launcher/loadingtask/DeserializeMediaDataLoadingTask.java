package com.jakebellotti.scene.launcher.loadingtask;

import com.jakebellotti.MediaManager;
import com.jakebellotti.io.Logger;
import com.jakebellotti.scene.loadingscreen.LoadingTask;

/**
 * @author Jake Bellotti
 * @date Apr 13, 2016
 */
public class DeserializeMediaDataLoadingTask implements LoadingTask {

	private static final Logger logger = new Logger(DeserializeMediaDataLoadingTask.class);
	
	@Override
	public String taskDescription() {
		return "Loading media definitions...";
	}

	@Override
	public String getFinishText() {
		//loaded x movie entries, tv series entries, music entries
		return "Loaded media definitions.";
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
		//XXX this is where you load media definitions
		logger.println("Deserializing media definitions");
		MediaManager.getMediaRepository().addMovieDefinitions(MediaManager.getDatabase().getAllMovieDefinitions());
		logger.println(MediaManager.getMediaRepository().getLoadedMovieDefinitions().size()+" movie definitions");
		MediaManager.getMediaRepository().addTVSeriesDefinitions(MediaManager.getDatabase().getAllTVSeriesDefinitions());
		logger.println(MediaManager.getMediaRepository().getTvSeriesDefinitions().size()+" tv series definitions");
		MediaManager.getMediaRepository().addTVSeriesEpisodeDefinitions(MediaManager.getDatabase().getAllTVSeriesEpisodeDefinitions());
		logger.println(MediaManager.getMediaRepository().getTvSeriesEpisodeDefinitions().size()+" tv series episode definitions");
		
	}

}
