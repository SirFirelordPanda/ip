package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;

/**
 * Represents a {@code ListClearCommand} in the Misora application.
 * <p>
 * A {@code ListClearCommand} removes all tasks from the {@link TaskList},
 * clears the saved data in {@link Storage}, and notifies the user via
 * the {@link Ui}.
 */
public class ListClearCommand extends Command {

    /**
     * Executes the clear command by removing all tasks from the task list,
     * clearing the saved file, and displaying confirmation to the user.
     *
     * @param taskList The {@link TaskList} to be cleared
     * @param ui The {@link Ui} used to display feedback to the user
     * @param storage The {@link Storage} used to clear persisted data
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.clearTaskList();
        storage.clearSavedFile();
        return ui.showListClear();
    }
}
