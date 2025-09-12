package novagpt.exception;

/**
 * Represents an NovaException
 * When thrown, the exception prints out an error message
 * and suggestion on how to format the input
 */
public class NovaException extends Exception {
    public NovaException(String message) {
        super(message);
    }
}
