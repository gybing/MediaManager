package com.jakebellotti.scene.tvseries;

import java.util.ArrayList;

import com.jakebellotti.model.tvseries.TVSeriesEntry;
import com.jakebellotti.task.Task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Jake Bellotti
 * @since Jun 3, 2016
 */
public class DownloadTVSeries {
	
	private final ArrayList<Task> tasks = new ArrayList<>();
	
	@FXML
	public void initialize() {
		
	}
	
	public static final void open(final TVSeriesEntry entry) {
		try {
			final FXMLLoader loader = new FXMLLoader();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
