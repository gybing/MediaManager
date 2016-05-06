package com.jakebellotti;

import java.io.File;

import javafx.scene.image.Image;

/**
 * 
 * @author Jake Bellotti
 * @date 5-Apr-2016
 *
 */
public class Images {
	
	private static Image loadingImage = null;

	public static Image getLoadingImage() {
		if(loadingImage == null) {
			if(loadingImage == null) {
				loadingImage = new Image(
					new File(DataConstants.IMAGE_ROOT_FOLDER + "/loading_spinner.gif").toURI().toString());
			}
		}
		return loadingImage;
	}
	
}
