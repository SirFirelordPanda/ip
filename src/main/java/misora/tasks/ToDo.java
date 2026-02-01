package misora.tasks;

import misora.exceptions.MissingArgument1Exception;
import misora.exceptions.MissingArgument2Exception;
import misora.exceptions.MissingTaskMsgException;

public class ToDo extends Task{

    public ToDo(String taskMsg) {
        super(taskMsg);
    }

    public ToDo(String taskMsg, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toSavedString() {
        return String.format("T | %s", super.toSavedString());
    }

    @Override
    public void isValidFormat() throws MissingTaskMsgException, MissingArgument1Exception, MissingArgument2Exception {
        try {
            super.isValidFormat();
        } catch (MissingTaskMsgException e) {
            throw new MissingTaskMsgException("WHOOPSIE!! Please enter the description of the task in this format 'todo -taskMsg-'");
        }
    }
}
