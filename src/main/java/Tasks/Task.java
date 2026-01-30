package Tasks;

import Exceptions.MisoraException;
import Exceptions.MissingArgument1Exception;
import Exceptions.MissingArgument2Exception;
import Exceptions.MissingTaskMsgException;

import java.time.LocalDate;

public abstract class Task {

    private final String taskMsg;
    private boolean isTaskDone;

    public Task(String taskMsg) {

        this.taskMsg = taskMsg;
    }

    public Task(String taskMsg, boolean isTaskDone) {

        this.taskMsg = taskMsg;
        this.isTaskDone = isTaskDone;
    }

    public char isTaskDoneMark() {

        return isTaskDone? 'X' : ' ';
    }

    public void setTaskDone(boolean taskDone) {

        isTaskDone = taskDone;
    }

    public String toString() {

        return String.format("[%c] %s", isTaskDoneMark(), taskMsg);
    }

    public String toSavedString() {

        return String.format("%c | %s", isTaskDoneMark(), taskMsg);
    }

    public Task isTaskOnDate(LocalDate date) {

        return null;
    }

    public void isValidFormat() throws MissingTaskMsgException, MissingArgument1Exception, MissingArgument2Exception {
        if (taskMsg.isEmpty()) {
            throw new MissingTaskMsgException();
        }
    }
}
