package novagpt.ui;

import javafx.application.Application;

/**
 * Launcher for the NovaGPT application.
 * <p>
 * This class serves as the entry point to launch the JavaFX application,
 * especially useful in environments where JavaFX classpath issues prevent direct
 * execution of the {@code Main} class.
 * </p>
 */
public class Launcher {
    /**
     * Main entry point of the application.
     *
     * @param args Command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
