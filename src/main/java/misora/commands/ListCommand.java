package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;


/**
 * Represents a {@code ListCommand} in the Misora application.
 * <p>
 * A {@code ListCommand} displays all tasks currently stored in the
 * {@link TaskList} by delegating the listing operation to the task list
 * and presenting the result via the {@link Ui}
 * by passing in the {@link Ui} to the taskList.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks in the task list.
     *
     * @param taskList The {@link TaskList} containing the tasks to be listed
     * @param ui The {@link Ui} used to display the tasks
     * @param storage The {@link Storage} (not used by this command)
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.listTasks(ui);
    }
}
