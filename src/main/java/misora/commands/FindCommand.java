package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;

/**
 * Represents a {@code FindCommand} in the Misora application.
 * <p>
 * A {@code FindCommand} searches for tasks that contain a given keyword
 * and displays the matching tasks to the user.
 */
public class FindCommand extends Command {

    /**
     * The keyword used to search for matching tasks.
     */
    private final String searchString;

    /**
     * Creates a {@code FindCommand} with the specified search keyword.
     *
     * @param searchString The keyword used to search for tasks
     */
    public FindCommand(String searchString) {
        this.searchString = searchString;
    }

    /**
     * Executes the find command by retrieving tasks that contain the specified
     * search keyword and displaying them using the {@link Ui}.
     *
     * @param taskList The {@link TaskList} to search for matching tasks
     * @param ui The {@link Ui} used to display matching tasks
     * @param storage The {@link Storage} (not used by this command)
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return ui.showTasksContainingString(taskList.getTasksContainingString(searchString), searchString);
    }
}
