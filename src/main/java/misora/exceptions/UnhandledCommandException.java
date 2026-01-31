package misora.exceptions;

public class UnhandledCommandException extends MisoraException{
    public UnhandledCommandException() {
        super("Uh oh stinky, I was not programmed to handle wtv you just typed in.\n" +
                "WHOOPSIE!! Maybe you can try again in the future when this is updated");
    }
}
