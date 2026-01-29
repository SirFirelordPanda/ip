public abstract class Task {

    private final String taskMsg;
    private boolean isTaskDone;

    public Task(String taskMsg) {

        this.taskMsg = taskMsg;
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
}
