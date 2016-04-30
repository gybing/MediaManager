package com.jakebellotti.fx.titlebar;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * TODO minimum sizes in resizing, try using on different screen resolutions
 * @author Jake Bellotti
 * @date Apr 25, 2016
 */
public class MenuBarController {

	private double xOffset = 0;
	private double yOffset = 0;

	private double lastStageX = 0;
	private double lastStageY = 0;
	private double lastStageWidth = 0;
	private double lastStageHeight;

	private final Stage stage;
	private final Region root;

	private boolean maximised = false;

	private static Image maximiseImage;
	private static Image maximiseImageHover;
	private static Image restoreImage;
	private static Image restoreImageHover;

	@FXML
	private AnchorPane windowPane;

	@FXML
	private AnchorPane titleBarPane;

	@FXML
	private ImageView closeImageView;

	@FXML
	private ImageView maximiseImageView;

	@FXML
	private ImageView minimiseImageView;

	@FXML
	private Label titleLabel;

	@FXML
	private StackPane contentPane;

	@FXML
	private ImageView resizePullerImageView;

	public MenuBarController(Stage stage, Region root) {
		this.stage = stage;
		this.root = root;
	}

	@FXML
	public void initialize() {

		addEventHandlers();

		Image minimiseImage = new Image(new File("./data/titlebar/minimize.png").toURI().toString());
		Image minimiseImageHover = new Image(new File("./data/titlebar/minimize-hover.png").toURI().toString());
		this.minimiseImageView.setImage(minimiseImage);
		this.minimiseImageView.setOnMouseEntered(event -> minimiseImageView.setImage(minimiseImageHover));
		this.minimiseImageView.setOnMouseExited(event -> minimiseImageView.setImage(minimiseImage));

		restoreImage = new Image(new File("./data/titlebar/restore.png").toURI().toString());
		restoreImageHover = new Image(new File("./data/titlebar/restore-hover.png").toURI().toString());
		maximiseImage = new Image(new File("./data/titlebar/maximize.png").toURI().toString());
		maximiseImageHover = new Image(new File("./data/titlebar/maximize-hover.png").toURI().toString());
		Image maximiseImageHover = new Image(new File("./data/titlebar/maximize-hover.png").toURI().toString());
		this.maximiseImageView.setImage(maximiseImage);
		this.maximiseImageView.setOnMouseEntered(event -> maximiseImageView.setImage(maximiseImageHover));
		this.maximiseImageView.setOnMouseExited(event -> maximiseImageView.setImage(maximiseImage));
		this.maximiseImageView.setOnMouseClicked(event -> {
			this.maximised = !maximised;
			updateMaximised(true);
		});

		Image closeImage = new Image(new File("./data/titlebar/close.png").toURI().toString());
		Image closeImageHover = new Image(new File("./data/titlebar/close-hover.png").toURI().toString());
		this.closeImageView.setImage(closeImage);
		this.closeImageView.setOnMouseEntered(event -> closeImageView.setImage(closeImageHover));
		this.closeImageView.setOnMouseExited(event -> closeImageView.setImage(closeImage));

		Image resizePuller = new Image(new File("./data/titlebar/resizeSE.png").toURI().toString());
		this.resizePullerImageView.setImage(resizePuller);
		this.resizePullerImageView.setCursor(Cursor.NW_RESIZE);

		if (root != null) {
			this.contentPane.getChildren().clear();
			Region r = (Region) root;
			
			r.prefWidthProperty().bind(contentPane.widthProperty());
			r.prefHeightProperty().bind(contentPane.heightProperty());
			this.contentPane.getChildren().add(r);
		}
	}

	private void updateMaximised(boolean resize) {
		if (this.maximised) {
			this.lastStageX = stage.getX();
			this.lastStageY = stage.getY();
			this.lastStageHeight = stage.getHeight();
			this.lastStageWidth = stage.getWidth();

			stage.hide();
			stage.setWidth(MenuBarStage.MAX_WIDTH);
			stage.setHeight(MenuBarStage.MAX_HEIGHT);
			stage.setX(0);
			stage.setY(0);
			stage.show();

			this.maximiseImageView.setOnMouseEntered(event -> this.maximiseImageView.setImage(restoreImageHover));
			this.maximiseImageView.setOnMouseExited(event -> this.maximiseImageView.setImage(restoreImage));
			this.maximiseImageView.setImage(restoreImage);
		} else {
			if (resize) {
				stage.hide();
				stage.setX(lastStageX);
				stage.setY(lastStageY);
				stage.setWidth(lastStageWidth);
				stage.setHeight(this.lastStageHeight);
				stage.show();
			}

			this.maximiseImageView.setOnMouseEntered(event -> this.maximiseImageView.setImage(maximiseImageHover));
			this.maximiseImageView.setOnMouseExited(event -> this.maximiseImageView.setImage(maximiseImage));
			this.maximiseImageView.setImage(maximiseImage);
		}
	}

	private void addEventHandlers() {
		this.minimiseImageView.setOnMouseClicked(event -> stage.setIconified(true));
		this.closeImageView.setOnMouseClicked(event -> stage.close());

		windowPane.setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		this.resizePullerImageView.setOnMouseDragged(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
			double widthDiff = (xOffset - stage.getWidth());
			double heightDiff = (yOffset - stage.getHeight());

			stage.setWidth(stage.getWidth() + widthDiff);
			stage.setHeight(stage.getHeight() + heightDiff);
			
			this.maximised = false;
			updateMaximised(false);
		});

		windowPane.setOnMouseDragged(event -> {
			double newX = event.getScreenX() - xOffset;
			double newY = event.getScreenY() - yOffset;
			stage.setX(newX);
			stage.setY(newY);
		});
	}

}
