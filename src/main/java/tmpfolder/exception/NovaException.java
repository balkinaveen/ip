package novagpt.exception;

/**
 * Represents an exception specific to NovaGPT
 * Encapsulates error messages for invalid user inputs or operations
 */
public class NovaException extends Exception {
    public NovaException(String message) {
        super(message);
    }
}
