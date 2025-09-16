package novagpt.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's avatar
 * and a Label containing the corresponding dialog text.
 *
 * <p>
 * Used for both the user and NovaGPT chatbot in the GUI.
 * </p>
 */
public class DialogBox extends HBox {
    private static final double DISPLAY_PIC_RADIUS = 17.5;
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the specified text and image.
     * Loads the layout from the corresponding FXML file.
     *
     * @param text Text to display in the dialog.
     * @param img  Image to use for the display picture.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayPicture.setImage(img);
        dialog.setText(text);
        Circle clip = new Circle(DISPLAY_PIC_RADIUS, DISPLAY_PIC_RADIUS, DISPLAY_PIC_RADIUS);
        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box such that the image appears on the left and the text on the right.
     * This is used to distinguish NovaGPT’s replies from the user’s messages.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.BOTTOM_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Returns a DialogBox configured for the user, with text on the left and image on the right.
     *
     * @param text Text to display.
     * @param img  User’s display image.
     * @return DialogBox representing the user’s input.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a DialogBox configured for NovaGPT, with text on the right and image on the left.
     *
     * @param text Text to display.
     * @param img  NovaGPT’s display image.
     * @return DialogBox representing NovaGPT’s reply.
     */
    public static DialogBox getNovagptDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
