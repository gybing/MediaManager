package com.jakebellotti.model.tvseries;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import com.jakebellotti.Constants;
import com.jakebellotti.Images;
import com.jakebellotti.MediaManager;
import com.jakebellotti.Settings;

import javafx.scene.image.Image;

/**
 * 
 * @author Jake Bellotti
 * @since 31-May-2016
 *
 */
public class TVSeriesEntry extends TVSeriesNode {

	private final int databaseID;
	private final String name;
	private int seriesDefinitionID;

	public void setSeriesDefinitionID(int seriesDefinitionID) {
		this.seriesDefinitionID = seriesDefinitionID;
	}

	private final ArrayList<TVSeriesSeason> seasons = new ArrayList<>();

	private Image backdropImage = null;
	private Image posterImage = null;

	public TVSeriesEntry(final int databaseID, final String name, final int seriesDefinitionID) {
		this.databaseID = databaseID;
		this.name = name;
		this.seriesDefinitionID = seriesDefinitionID;
	}
	
	public void downloadImages() {
		getDefinition().ifPresent(def -> {
			Images.downloadImage(Constants.THE_MOVIE_DB_ORIGINAL_IMAGE + def.getBackdropURL(), "./data/img/tvseries/backdrop/" + databaseID + "_backdrop.jpg");
			Images.downloadImage(Constants.THE_MOVIE_DB_IMAGE_URL + "w500" + def.getPosterURL(), "./data/img/tvseries/poster/" + databaseID + "_tvseries_poster.jpg");
		});
	}

	/**
	 * 
	 * @return Optional.empty() if there is no definition assigned, or the
	 *         TVSeriesDefinition wrapped in an Optional instance.
	 */
	public Optional<TVSeriesDefinition> getDefinition() {
		return MediaManager.getMediaRepository().getTVSeriesDefinition(seriesDefinitionID);
	}

	public Image getBackDrop() {
		if (backdropImage == null) {
			final File backdropFile = new File("./data/img/tvseries/backdrop/" + getDatabaseID() + "_backdrop.jpg");
			if(!backdropFile.exists())
				return null;
			final Image newImage = new Image(backdropFile.toURI().toString());
			if (Settings.isMemorySaverMode())
				return newImage;
			backdropImage = newImage;
		}
		return backdropImage;
	}

	public Image getPoster() {
		if (posterImage == null) {
			final File posterFile = new File("./data/img/tvseries/poster/" + getDatabaseID() + "_tvseries_poster.jpg");
			if(!posterFile.exists())
				return null;
			final Image newImage = new Image(posterFile.toURI().toString());
			if (Settings.isMemorySaverMode())
				return newImage;
			posterImage = newImage;
		}
		return posterImage;
	}

	public int getDatabaseID() {
		return databaseID;
	}

	public int getSeriesDefinitionID() {
		return seriesDefinitionID;
	}

	public ArrayList<TVSeriesSeason> getSeasons() {
		return seasons;
	}

	public String printInfo() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[" + toString() + "]\n");
		builder.append("Seasons: " + seasons.size() + "\n");
		for (TVSeriesSeason season : seasons) {
			builder.append(season.toString() + "\n");
			for (TVSeriesEpisode e : season.getEpisodes()) {
				builder.append(e.toString() + "\n");
			}
		}
		return builder.toString();
	}

	@Override
	public String getDisplayName() {
		return name;
	}

}
