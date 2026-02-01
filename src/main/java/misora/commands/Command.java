package misora.commands;

import misora.components.TaskList;
import misora.components.Ui;
import misora.components.Storage;

/**
 * Represents an executable user command in the Misora application.
 * <p>
 * Each concrete {@code Command} encapsulates a specific action that can be
 * performed on the application's task list, user interface, and storage.
 * Subclasses should override {@link #execute(TaskList, Ui, Storage)} to define
 * the command's behavior.
 */
public abstract class Command {

    /**
     * Executes the command using the provided application components.
     *
     * @param taskList The {@link TaskList} containing the current list of tasks
     * @param ui The {@link Ui} responsible for user interaction
     * @param storage The {@link Storage} responsible for persisting data
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
    }

    /**
     * Indicates whether this command causes the application to terminate.
     * <p>
     * Only {@code ExitCommand} should override this method to return {@code true}.
     *
     * @return {@code true} if this command exits the application, {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}
