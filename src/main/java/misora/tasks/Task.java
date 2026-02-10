package misora.tasks;

import java.time.LocalDate;

import misora.exceptions.MissingArgument1Exception;
import misora.exceptions.MissingArgument2Exception;
import misora.exceptions.MissingTaskMsgException;

/**
 * Represents a generic task in the Misora application.
 * <p>
 * A {@code Task} contains a description, a completion status, and
 * provides methods for formatting its representation for display
 * and storage. Subclasses may extend {@code Task} to include additional
 * attributes such as deadlines or event dates.
 */
public abstract class Task {

    /**
     * The description of the task.
     */
    private final String taskMsg;

    /**
     * Indicates whether the task has been completed.
     */
    private boolean isTaskDone;

    /**
     * Creates a new {@code Task} with the given description.
     * <p>
     * The task is initially not done.
     *
     * @param taskMsg The description of the task
     */
    public Task(String taskMsg) {
        this.taskMsg = taskMsg;
        assert taskMsg != null : "taskMsg cannot be null";

    }

    /**
     * Creates a new {@code Task} with the given description and completion status.
     *
     * @param taskMsg The description of the task
     * @param isTaskDone {@code true} if the task is completed, {@code false} otherwise
     */
    public Task(String taskMsg, boolean isTaskDone) {
        this.taskMsg = taskMsg;
        this.isTaskDone = isTaskDone;
        assert taskMsg != null : "taskMsg cannot be null";
    }

    /**
     * Returns a mark representing the completion status of the task.
     *
     * @return 'X' if the task is done, otherwise a space ' '
     */
    public char isTaskDoneMark() {
        return isTaskDone ? 'X' : ' ';
    }

    /**
     * Sets the completion status of the task.
     *
     * @param taskDone {@code true} to mark the task as done, {@code false} otherwise
     */
    public void setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
    }

    /**
     * Returns a string representation of the task for displaying to the user.
     * <p>
     * Example: "[X] Finish homework"
     *
     * @return The formatted string representing this task
     */
    public String toString() {
        return String.format("[%c] %s", isTaskDoneMark(), taskMsg);
    }

    /**
     * Returns a string representation of the task suitable for saving to a file.
     * <p>
     * Example: "X | Finish homework"
     *
     * @return The formatted string for storage
     */
    public String toSavedString() {
        return String.format("%c | %s", isTaskDoneMark(), taskMsg);
    }

    /**
     * Determines if this task occurs on the specified date.
     * <p>
     * The base {@code Task} class does not associate with a date, so this
     * method returns {@code null}. Subclasses such as {@link Deadline} or
     * {@link Event} may override this method.
     *
     * @param date The date to check
     * @return This task if it occurs on the given date, otherwise {@code null}
     */
    public Task isTaskOnDate(LocalDate date) {
        assert date != null : "date cannot be null in isTaskOnDate";
        return null;
    }

    /**
     * Checks whether this task contains the given search string.
     * <p>
     * A task is considered a match if the search string appears in
     * the task description (inherited behaviour)
     *
     * @param searchString The string to search for within this task.
     * @return This task if the search string matches any relevant field;
     *         {@code null} otherwise.
     */
    public Task doesTaskContainString(String searchString) {
        assert searchString != null : "searchString cannot be null in doesTaskContainString";
        if (this.taskMsg.contains(searchString)) {
            return this;
        }
        return null;
    }

    /**
     * Validates the format of this task.
     * <p>
     * Throws a {@link MissingTaskMsgException} if the task description is empty.
     * Subclasses may override to include additional validation, such as checking
     * for deadlines or event times.
     *
     * @throws MissingTaskMsgException If the task description is missing
     * @throws MissingArgument1Exception Not used in this class but included for signature consistency
     * @throws MissingArgument2Exception Not used in this class but included for signature consistency
     */
    public void isValidFormat() throws MissingTaskMsgException, MissingArgument1Exception, MissingArgument2Exception {
        if (taskMsg.isEmpty()) {
            throw new MissingTaskMsgException();
        }
    }
}
