package misora.exceptions;

public class MissingTaskMsgException extends MisoraArgumentException{

    public MissingTaskMsgException(){}

    public MissingTaskMsgException(String errorMsg) {
        super(errorMsg);
    }
}
