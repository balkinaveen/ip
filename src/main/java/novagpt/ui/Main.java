package novagpt.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI launcher for NovaGPT using JavaFX and FXML.
 * <p>
 * This class is the entry point for the graphical interface of the NovaGPT chatbot application.
 * It sets up the primary stage, loads the FXML layout, and links the backend logic to the controller.
 * </p>
 */
public class Main extends Application {

    /** Minimum height for the application window. */
    private static final double MIN_HEIGHT = 220;
    /** Minimum width for the application window. */
    private static final double MIN_WIDTH = 417;
    /** Relative path to the storage file for saving tasks. */
    private static final String STORAGE_PATH = "./data/NovaGPT.txt";

    /**
     * Starts the JavaFX application.
     * <p>
     * This method sets up the stage (window), loads the UI from FXML,
     * injects the NovaGpt instance into the controller, and displays the scene.
     * </p>
     *
     * @param stage the primary stage for this JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(MIN_HEIGHT);
            stage.setMinWidth(MIN_WIDTH);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            NovaGpt novagpt = new NovaGpt(STORAGE_PATH);
            fxmlLoader.<MainWindow>getController().setNovagpt(novagpt);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
