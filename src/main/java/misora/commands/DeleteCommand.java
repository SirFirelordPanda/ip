package misora.commands;

import java.io.IOException;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;

/**
 * Represents a {@code DeleteCommand} in the Misora application.
 * <p>
 * A {@code DeleteCommand} removes a task from the {@link TaskList} based on
 * a user-specified task number. The updated task list is then saved to
 * {@link Storage}, and feedback is shown to the user via the {@link Ui}.
 */
public class DeleteCommand extends Command {

    /**
     * The string representation of the task number to be deleted.
     */
    private final String numberPart;

    /**
     * Creates a {@code DeleteCommand} with the specified task number.
     *
     * @param numberPart The task number provided by the user
     */
    public DeleteCommand(String numberPart) {
        assert numberPart != null : "numberPart reference should not be null";
        this.numberPart = numberPart;
    }

    /**
     * Executes the delete command by removing the specified task from the task list.
     * <p>
     * The task number is parsed and validated during execution. If the input is
     * invalid, out of bounds, or if an error occurs while updating storage, an
     * appropriate error message is displayed to the user via the {@link Ui}.
     *
     * @param taskList The {@link TaskList} from which the task will be removed
     * @param ui The {@link Ui} used to display feedback to the user
     * @param storage The {@link Storage} used to persist the updated task list
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        assert taskList != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";

        try {
            int numberDeleted = Integer.parseInt(numberPart);
            Task removedTask = taskList.remove(numberDeleted - 1);

            assert removedTask != null : "Removed task should not be null";

            storage.updateSavedFileFromTaskList(taskList);
            return ui.showDeleteTask(removedTask, taskList);
        } catch (NumberFormatException e) {
            return ui.showError("Invalid number given");
        } catch (IndexOutOfBoundsException e) {
            return ui.showError("Number is not within list size");
        } catch (IOException e) {
            return ui.showError("Unable to update the saved file from the task list");
        }
    }
}
