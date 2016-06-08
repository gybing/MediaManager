package com.jakebellotti.scene.movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jakebellotti.Images;
import com.jakebellotti.MediaManager;
import com.jakebellotti.model.ReturnResultWrapper;
import com.jakebellotti.model.ReturnStatus;
import com.jakebellotti.model.movie.MovieDefinition;
import com.jakebellotti.model.movie.MovieEntry;
import com.jakebellotti.model.movie.NewMovieDefinition;
import com.jakebellotti.scene.main.MainWindowFrame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * TODO this code is horrible, make it better
 * 
 * @author Jake Bellotti Date 8-May-2016
 *
 */
public class RetrieveMovieDefinitions {

	private final boolean overrideExistingDefinitions;
	private final Stage stage;
	private final Stage thisStage = new Stage();
	private boolean requestCancel = false;
	private int current = 1;

	private ExecutorService executor = Executors.newFixedThreadPool(1);
	private Thread retrievalThread;

	@FXML
	private ProgressBar progressbar;

	@FXML
	private Button requestCancelButton;

	@FXML
	private ListView<String> listView;

	public RetrieveMovieDefinitions(final Stage stage, final boolean override) {
		this.overrideExistingDefinitions = override;
		this.stage = stage;
	}

	@FXML
	public void initialize() {
		this.thisStage.setTitle("Downloading movie definitions");
		this.thisStage.setOnCloseRequest(e -> {
			executor.shutdown();
			requestCancel = true;	
		});
		stage.close();
		
		
		this.requestCancelButton.setText("Begin downloading");
		this.requestCancelButton.setOnMouseClicked(e -> {
			retrievalThread = new Thread(() -> {
				beginRetrieving();
			});
			retrievalThread.start();
		});
	}

	private void beginRetrieving() {
		Platform.runLater(() -> {
			this.requestCancelButton.setText("Request Cancel");
			this.requestCancelButton.setOnMouseClicked(e -> {
				Platform.runLater(() -> {
					this.requestCancel = true;
					listView.getItems().add("Cancel requested...");
					this.listView.scrollTo((this.listView.getItems().size() - 1));
				});
			});
		}); 
		
		ArrayList<MovieEntry> entriesToScrape = new ArrayList<>();

		MediaManager.getMediaRepository().getLoadedMovieEntries().forEach(entry -> {
			if (this.overrideExistingDefinitions) {
				entriesToScrape.add(entry);
			} else {
				if (!entry.getDefinition().isPresent()) {
					entriesToScrape.add(entry);
				}
			}
		});

		for (final MovieEntry entry : entriesToScrape) {
			if (requestCancel) {
				return;
			}
			final CountDownLatch latch = new CountDownLatch(1);
			executor.execute(() -> {
				Platform.runLater(() -> {
					this.listView.getItems().add("Downloading definition for '" + entry.toString() + "'");
					this.listView.scrollTo((this.listView.getItems().size() - 1));
				});

				Optional<MovieDefinition> def = downloadDefinition(entry);

				if (def.isPresent()) {
					Platform.runLater(() -> {
						this.listView.getItems().add("Downloading image...");
						this.listView.scrollTo((this.listView.getItems().size() - 1));
					});
					downloadImage(def.get());
					Platform.runLater(() -> {
						this.listView.getItems().add("Finished downloading data for '" + entry.toString() + "'.");
						this.listView.scrollTo((this.listView.getItems().size() - 1));
					});

					if (this.requestCancel) {
						Platform.runLater(() -> {
							thisStage.close();
						});
					}
				}
				Platform.runLater(() -> {
					double a = (100 / new Double(entriesToScrape.size()));
					double b = (current * a);
					double progress = b / 100;					
					this.progressbar.setProgress(progress);
				});
				current++;
				latch.countDown();
				// TODO if def is not found, update

				// TODO notify the user if the photo exists, or if it couldn't
				// be downloaded
				// TODO when downloading all definitons, allow the user to
				// specify if they want all images redownloaded

			});
			try {
				latch.await();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		executor.execute(() -> {
			Platform.runLater(() -> {
				this.listView.getItems().add("Finished downloading data.");
				// TODO progress, how many entries were retrieved etc
				this.progressbar.setProgress(1);
				this.requestCancelButton.setText("Close");
				this.requestCancelButton.setOnMouseClicked(e -> {
					thisStage.close();
					// TODO maybe refresh only each single entry
					MainWindowFrame.getController().getCurrentSceneController().refresh();
				});
			});
		});
		
		executor.shutdown();
	}

	private Optional<MovieDefinition> downloadDefinition(MovieEntry entry) {
		Optional<ReturnResultWrapper<ReturnStatus, NewMovieDefinition>> definition = MediaManager.getMovieDefinitionRetriever().get().scrapeData(entry);

		if (definition.isPresent()) {
			if(definition.get().getReturnResult().isPresent()) {
				NewMovieDefinition def = definition.get().getReturnResult().get();
				MovieDefinition storedDefinition = MediaManager.getDatabase().addMovieDefinition(def);
				MediaManager.getMediaRepository().assignMovieDefinition(storedDefinition.getDatabaseID(), storedDefinition);
				entry.setMovieDefinitionID(storedDefinition.getDatabaseID());
				MediaManager.getDatabase().assignMovieDefinitionToEntry(entry, storedDefinition.getDatabaseID());
				return Optional.of(storedDefinition);	
			}
		}
		return Optional.empty();
	}

	private void downloadImage(MovieDefinition def) {
		Images.downloadImage(def);
	}

	public void open() {
		thisStage.initModality(Modality.APPLICATION_MODAL);
		thisStage.initOwner(stage);
		thisStage.setScene(getScene(stage));
		thisStage.show();
	}

	private Scene getScene(Stage stage) {
		try {
			final FXMLLoader loader = new FXMLLoader();
			loader.setController(this);
			Parent root = loader
					.load(RetrieveMovieDefinitions.class.getResource("RetrieveMovieDefinitions.fxml").openStream());
			return new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
