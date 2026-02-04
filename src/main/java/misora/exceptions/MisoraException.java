package misora.exceptions;

/**
 * Represents all exceptions thrown due to the workings of Misora
 */
public class MisoraException extends Exception {
    public MisoraException(String errorMsg) {
        super(errorMsg);
    }

    public MisoraException() {
    }
}
