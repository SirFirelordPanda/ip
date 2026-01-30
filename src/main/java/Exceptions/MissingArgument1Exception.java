package Exceptions;

public class MissingArgument1Exception extends MisoraArgumentException{
    public MissingArgument1Exception(){}

    public MissingArgument1Exception(String errorMsg) {
        super(errorMsg);
    }
}
