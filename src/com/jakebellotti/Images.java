package com.jakebellotti;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.jakebellotti.model.movie.MovieDefinition;

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
	
	public static final void downloadImage(MovieDefinition def) {
		final String[] split = def.getPoster().split("/");

		final File outputFile = new File(DataConstants.MOVIE_IMAGE_FOLDER + "/" + split[split.length - 1]);
		if (outputFile.exists())
			return;
		try {
			URL url = new URL(def.getPoster());
			InputStream in = new BufferedInputStream(url.openStream());
			OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));

			for (int i; (i = in.read()) != -1;) {
				out.write(i);
			}
			in.close();
			out.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
