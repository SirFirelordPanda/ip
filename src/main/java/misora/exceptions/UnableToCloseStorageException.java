package misora.exceptions;

public class UnableToCloseStorageException extends MisoraException{
    public UnableToCloseStorageException(){}

    public UnableToCloseStorageException(String errorMsg) {
        super(errorMsg);
    }
}