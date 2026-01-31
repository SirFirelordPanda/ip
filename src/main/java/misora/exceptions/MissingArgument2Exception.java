package misora.exceptions;

public class MissingArgument2Exception extends MisoraArgumentException{
    public MissingArgument2Exception(){}

    public MissingArgument2Exception(String errorMsg) {
        super(errorMsg);
    }
}
