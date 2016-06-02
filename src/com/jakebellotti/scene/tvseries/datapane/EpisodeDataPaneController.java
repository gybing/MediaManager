package com.jakebellotti.scene.tvseries.datapane;

import java.util.Optional;

import com.jakebellotti.Constants;
import com.jakebellotti.model.tvseries.TVSeriesEpisode;
import com.jakebellotti.model.tvseries.TVSeriesEpisodeDefinition;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author Jake Bellotti
 * @since 1 Jun 2016
 */
public class EpisodeDataPaneController {
	
    @FXML
    private Label themoviedbURLLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private TextArea plotTextArea;
    
    @FXML
    public void initialize() {
    	updateEpisode(null);
    }
	
	public void updateEpisode(final TVSeriesEpisode episode) {
		if(episode == null) {
			themoviedbURLLabel.setText("");
			nameLabel.setText("");
			plotTextArea.setText("");
			return;
		}
		final Optional<TVSeriesEpisodeDefinition> definition = episode.getDefinition();
		if(! definition.isPresent()) {
			updateEpisode(null);
			return;
		}
		definition.ifPresent(def -> {
			this.themoviedbURLLabel.setText(Constants.THE_MOVIE_DB_TV_LINK + def.getThemoviedbID());
			this.nameLabel.setText(def.getName());
			plotTextArea.setText(def.getOverview());
		});
	}

}
