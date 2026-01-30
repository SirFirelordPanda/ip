package Exceptions;

public class UnableToWriteToFileException extends MisoraException{
    public UnableToWriteToFileException(){}

    public UnableToWriteToFileException(String errorMsg) {
        super(errorMsg);
    }
}
