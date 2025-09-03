package NovaGPT.ui;

public class Ui {

    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    /**
     * Prints a message with a line on top and at the bottom.
     *
     * @param message the text to output to the user
     */
    public static void print(String message) {
        System.out.print(HORIZONTAL_LINE + "\n" + message + "\n" + HORIZONTAL_LINE + "\n");
    }
}
