package misora.exceptions;

/**
 * Represents an exception thrown when any child of the {@code Task} class has an empty taskMsg
 */
public class MissingTaskMsgException extends MisoraArgumentException {

    public MissingTaskMsgException() {}

    public MissingTaskMsgException(String errorMsg) {
        super(errorMsg);
    }
}
