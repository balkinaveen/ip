package novagpt.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * Controller for the main GUI of NovaGPT.
 * <p>
 * Handles user interaction, input processing, and visual dialog box updates.
 */
public class MainWindow extends AnchorPane {
    private static final double EXIT_DELAY_SECONDS = 1.0;
    private static final String USER_IMAGE = "/images/DaChatter.png";
    private static final String NOVAGPT_IMAGE = "/images/DaNova.png";
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;


    private NovaGpt novagpt;

    private Image userImage = new Image(this.getClass().getResourceAsStream(USER_IMAGE));
    private Image novagptImage = new Image(this.getClass().getResourceAsStream(NOVAGPT_IMAGE));


    /**
     * Initializes the main UI components.
     * <p>
     * Binds the scrollPane to the dialogContainer height, ensures vertical scrolling,
     * adds welcome message from NovaGPT, and sets placeholder text in the input field.
     */
    @FXML
    public void initialize() {
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getNovagptDialog(Ui.welcomeMessage(), novagptImage));
        userInput.setPromptText("Command");
    }

    /**
     * Injects the NovaGPT instance to this UI controller.
     *
     * @param n NovaGPT backend instance.
     */
    public void setNovagpt(NovaGpt n) {
        assert n != null : "Injected NovaGPT instance must not be null";
        novagpt = n;
    }

    /**
     * Handles user input when the send button is clicked or Enter is pressed.
     * <p>
     * Echoes the user's command and displays NovaGPT's response in dialog boxes.
     * If input is "bye", shows goodbye message and exits after a short delay.
     */
    @FXML
    private void handleUserInput() {
        String input = getUserInput();
        assert input != null : "User input must not be null";
        if (input.toLowerCase().equals("bye")) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getNovagptDialog(Ui.goodbyeMessage(), novagptImage)
            );
            PauseTransition delay = new PauseTransition(Duration.seconds(EXIT_DELAY_SECONDS));
            delay.setOnFinished(event -> {
                Stage stage = (Stage) userInput.getScene().getWindow();
                stage.close();
            });
            delay.play();
        } else {
            String response = novagpt.response(input);
            assert response != null : "Response must not be null";
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getNovagptDialog(response, novagptImage)
            );
            userInput.clear();
        }
    }

    /**
     * Retrieves the current text from the user input field.
     *
     * @return A String containing the user's input.
     */
    private String getUserInput() {
        return userInput.getText();
    }
}
