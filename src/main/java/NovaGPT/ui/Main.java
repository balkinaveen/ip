package novagpt.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for NovaGPT using FXML.
 */
public class Main extends Application {

    private static final double MIN_HEIGHT = 220;
    private static final double MIN_WIDTH = 417;
    private static final String STORAGE_PATH = "./data/NovaGPT.txt";

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
