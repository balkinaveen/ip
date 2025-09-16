package novagpt.exception;

/**
 * Represents a custom exception used in the NovaGPT chatbot.
 * <p>
 * This exception is thrown when the user provides invalid inputs or
 * performs operations that violate expected behavior in the application.
 * </p>
 */
public class NovaException extends Exception {
    /**
     * Constructs a {@code NovaException} with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public NovaException(String message) {
        super(message);
    }
}
