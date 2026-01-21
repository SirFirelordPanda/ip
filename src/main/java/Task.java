public class Task {

    private final String taskMsg;
    private boolean isTaskDone;

    public Task(String taskMsg) {
        this.taskMsg = taskMsg;
    }

    public String getTaskMsg() {
        return this.taskMsg;
    }

    public boolean isTaskDone() {
        return isTaskDone;
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
}
