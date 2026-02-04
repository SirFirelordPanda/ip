package misora.exceptions;

/**
 * Represents an exception thrown when the command is not within the parser's codebase
 */
public class UnhandledCommandException extends MisoraException {

    /**
     * Constructs a new {@code UnhandledCommandException} with a default
     * error message indicating that the entered command is unsupported.
     */
    public UnhandledCommandException() {
        super("Uh oh stinky, I was not programmed to handle wtv you just typed in.\n"
                + "WHOOPSIE!! Maybe you can try again in the future when this is updated");
    }
}
