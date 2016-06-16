package test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jake Bellotti
 * @since Jun 14, 2016
 */
public class LargeImageTestController {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ImageView imageView;

	@FXML
	private Button button;

	@FXML
	public void initialize() {
		// final Image image = new Image(new
		// File("./data/img/tvseries/backdrop/501_backdrop.jpg").toURI().toString(),
		// 1000, 600, false, false);
		// final Image image = new Image(new
		// File("./data/img/tvseries/backdrop/501_backdrop.jpg").toURI().toString(),
		// 888, 500, false, false);
		final Image image = new Image(new File("./data/img/tvseries/backdrop/501_backdrop.jpg").toURI().toString());

		imageView.fitWidthProperty().bind(anchorPane.widthProperty());
		imageView.fitHeightProperty().bind(anchorPane.heightProperty());

		// imageView.fitHeightProperty().addListener((c) -> {
		// final Image image2 = new Image(new
		// File("./data/img/tvseries/backdrop/501_backdrop.jpg").toURI().toString(),
		// imageView.getFitWidth(),
		// imageView.getFitHeight(), false, false);
		// imageView.setImage(image2);
		// });
		//
		// imageView.fitWidthProperty().addListener((c) -> {
		// System.out.println(imageView.getFitWidth() + ", " +
		// imageView.getFitHeight());
		// final Image image2 = new Image(new
		// File("./data/img/tvseries/backdrop/501_backdrop.jpg").toURI().toString(),
		// imageView.getFitWidth(),
		// imageView.getFitHeight(), false, false);
		// imageView.setImage(image2);
		// });
		button.setOnMouseClicked(e -> {
			try {
				BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
				ByteArrayOutputStream s = new ByteArrayOutputStream();
				ImageIO.write(bImage, "jpg", s);
				byte[] res  = s.toByteArray();
				s.close(); //especially if you are using a different output stream.
				
				
				final Image image2 = new Image(new ByteArrayInputStream(res), imageView.getFitWidth(),
						imageView.getFitHeight(), false, false);
				imageView.setImage(image2);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		});

		imageView.setImage(image);
	}

}