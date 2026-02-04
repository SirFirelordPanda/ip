package misora.exceptions;

/**
 * Represents an exception thrown when the {@code Event} class has an empty toWhen
 */
public class MissingArgument2Exception extends MisoraArgumentException {
    public MissingArgument2Exception() {}

    public MissingArgument2Exception(String errorMsg) {
        super(errorMsg);
    }
}
