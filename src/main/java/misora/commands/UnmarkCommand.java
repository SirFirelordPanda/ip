package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;

/**
 * Represents an {@code UnmarkCommand} in the Misora application.
 * <p>
 * An {@code UnmarkCommand} marks a specified task in the {@link TaskList}
 * as not completed based on a user-provided task number.
 */
public class UnmarkCommand extends Command {

    /**
     * The string representation of the task number to be unmarked.
     */
    private final String numberPart;

    /**
     * Creates an {@code UnmarkCommand} with the specified task number.
     *
     * @param numberPart The task number provided by the user
     */
    public UnmarkCommand(String numberPart) {
        this.numberPart = numberPart;
    }

    /**
     * Executes the unmark command by marking the specified task as not completed.
     * <p>
     * The task number is parsed and validated during execution. If the input
     * is invalid or out of bounds, an appropriate error message is displayed
     * to the user.
     *
     * @param taskList The {@link TaskList} containing the task to be unmarked
     * @param ui The {@link Ui} used to display feedback to the user
     * @param storage The {@link Storage} (not used by this command)
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            int numberToUnmark = Integer.parseInt(numberPart);
            Task taskToUnmark = taskList.get(numberToUnmark - 1);
            taskToUnmark.setTaskDone(false);
            ui.showUnmarkTask(taskToUnmark);
        } catch (NumberFormatException e) {
            ui.showError("Invalid number given");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Number is not within list size");
        }
    }
}
