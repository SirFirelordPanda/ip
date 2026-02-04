package misora.exceptions;

/**
 * Represents an exception thrown when the {@code ExitCommand} is unable to close the storage properly
 */
public class UnableToCloseStorageException extends MisoraException {
    public UnableToCloseStorageException() {}

    public UnableToCloseStorageException(String errorMsg) {
        super(errorMsg);
    }
}
