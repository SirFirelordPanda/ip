package misora.tasks;

import misora.exceptions.MissingArgument1Exception;
import misora.exceptions.MissingArgument2Exception;
import misora.exceptions.MissingTaskMsgException;

/**
 * Represents a simple ToDo task in the Misora application.
 * <p>
 * A {@code ToDo} has a description (inherited from {@link Task}) and a completion status.
 * Unlike {@link Deadline} or {@link Event}, it does not have associated dates or times.
 * <p>
 * This class overrides {@link Task#toString()}, {@link Task#toSavedString()},
 * and {@link Task#isValidFormat()} to provide ToDo-specific behavior.
 */
public class ToDo extends Task{

    /**
     * Creates a new {@code ToDo} task with the given description.
     *
     * @param taskMsg The description of the ToDo task
     */
    public ToDo(String taskMsg) {
        super(taskMsg);
    }

    /**
     * Creates a new {@code ToDo} task with the given description and completion status.
     *
     * @param taskMsg The description of the ToDo task
     * @param isTaskDone {@code true} if the task is completed, {@code false} otherwise
     */
    public ToDo(String taskMsg, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
    }

    /**
     * Returns a string representation of the ToDo task for display to the user.
     *
     * @return The formatted string representing this ToDo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string representation of the ToDo task suitable for saving to a file.
     *
     * @return The formatted string for storage
     */
    @Override
    public String toSavedString() {
        return String.format("T | %s", super.toSavedString());
    }

    /**
     * Validates the format of the ToDo task.
     * <p>
     * Throws a {@link MissingTaskMsgException} if the description is empty.
     *
     * @throws MissingTaskMsgException if the task description is missing
     * @throws MissingArgument1Exception Not used in this class but included for signature consistency
     * @throws MissingArgument2Exception Not used in this class but included for signature consistency
     */
    @Override
    public void isValidFormat() throws MissingTaskMsgException, MissingArgument1Exception, MissingArgument2Exception {
        try {
            super.isValidFormat();
        } catch (MissingTaskMsgException e) {
            throw new MissingTaskMsgException("WHOOPSIE!! Please enter the description of the task in this format 'todo -taskMsg-'");
        }
    }
}
