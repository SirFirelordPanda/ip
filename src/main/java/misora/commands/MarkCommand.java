package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;

/**
 * Represents a {@code MarkCommand} in the Misora application.
 * <p>
 * A {@code MarkCommand} marks a specified task in the {@link TaskList}
 * as completed based on a user-provided task number.
 */
public class MarkCommand extends Command{

    /**
     * The string representation of the task number to be marked as completed.
     */
    private final String NUMBERPART;

    /**
     * Creates a {@code MarkCommand} with the specified task number.
     *
     * @param numberPart The task number provided by the user
     */
    public MarkCommand(String numberPart) {
        this.NUMBERPART = numberPart;
    }

    /**
     * Executes the mark command by marking the specified task as completed.
     * <p>
     * The task number is parsed and validated during execution. If the input
     * is invalid or out of bounds, an appropriate error message is displayed
     * to the user.
     *
     * @param taskList The {@link TaskList} containing the task to be marked
     * @param ui The {@link Ui} used to display feedback to the user
     * @param storage The {@link Storage} (not used by this command)
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            int numberToMark = Integer.parseInt(NUMBERPART);
            Task taskToMark = taskList.get(numberToMark - 1);
            taskToMark.setTaskDone(true);
            ui.showMarkTask(taskToMark);
        } catch (NumberFormatException e) {
            ui.showError("Invalid number given");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Number is not within list size");
        }
    }
}
