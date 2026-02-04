package misora.exceptions;

/**
 * Represents an exception thrown when the {@code ToDo} class has n empty byWhen
 * or the {@code Event} class has an empty fromWhen
 */
public class MissingArgument1Exception extends MisoraArgumentException {
    public MissingArgument1Exception() {}

    public MissingArgument1Exception(String errorMsg) {
        super(errorMsg);
    }
}
