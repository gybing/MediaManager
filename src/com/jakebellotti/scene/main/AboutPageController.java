package com.jakebellotti.scene.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

/**
 *
 * @author Jake Bellotti
 * @since 14 Jun 2016
 */
public class AboutPageController {
	
    @FXML
    private ScrollPane licenceScrollPane;
    
    @FXML
    private Label licenceLabel;

	@FXML
	public void initialize() {
		licenceLabel.setMaxWidth(licenceScrollPane.getWidth());
		for(int i = 0; i < 1000; i ++) {
			licenceLabel.setText(licenceLabel.getText() + "ayylmao");
		}
		
		final Timeline timeline = new Timeline();
		final KeyValue kv = new KeyValue(licenceScrollPane.vvalueProperty(), 1.0);
		final KeyFrame kf = new KeyFrame(Duration.millis(10000), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

}
