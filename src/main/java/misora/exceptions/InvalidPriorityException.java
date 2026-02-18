package misora.exceptions;

/**
 * Represents an exception thrown when the priority given is invalid
 */
public class InvalidPriorityException extends MisoraException {
    public InvalidPriorityException(String message) {
        super(message);
    }
}
