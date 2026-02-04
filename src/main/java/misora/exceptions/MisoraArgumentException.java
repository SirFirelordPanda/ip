package misora.exceptions;

/**
 * Represents an exception thrown when the arguments for the methods are invalid
 */
public class MisoraArgumentException extends MisoraException {
    public MisoraArgumentException(String errorMsg) {
        super(errorMsg);
    }

    public MisoraArgumentException() {
    }
}
