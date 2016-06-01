package com.jakebellotti.scene.tvseries;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * @author Jake Bellotti
 * @since 01-Jun-2016
 *
 */
public class SeriesDataPane {
	
	private static AnchorPane pane = null;
	
	public static final AnchorPane getPane() {
		if(pane == null)
			loadPane();
		return pane;
	}
	
	private static final void loadPane() {
		try {
			FXMLLoader loader = new FXMLLoader();
			pane = loader.load(SeriesDataPane.class.getResource("SeriesDataPane.fxml").openStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
