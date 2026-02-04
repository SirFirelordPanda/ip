package misora.exceptions;

/**
 * Represents an exception thrown when the {@code Storage} class is unable to write to the file properly
 */
public class UnableToWriteToFileException extends MisoraException {
    public UnableToWriteToFileException() {}

    public UnableToWriteToFileException(String errorMsg) {
        super(errorMsg);
    }
}
