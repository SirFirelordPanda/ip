package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.exceptions.MisoraException;

/**
 * Represents an {@code ExitCommand} in the Misora application.
 * <p>
 * An {@code ExitCommand} terminates the application by performing any
 * necessary cleanup through the {@link Ui} and {@link Storage} components.
 * This command signals to the application that execution should stop.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command by closing the user interface and storage.
     * <p>
     * If an error occurs during shutdown, an appropriate error message
     * is displayed to the user via the {@link Ui}.
     *
     * @param taskList The {@link TaskList} (not used by this command)
     * @param ui The {@link Ui} responsible for handling application exit
     * @param storage The {@link Storage} responsible for final cleanup
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";

        try {
            storage.exit();
            return ui.showExit();
        } catch (MisoraException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Indicates that this command causes the application to terminate.
     *
     * @return {@code true}, as this command exits the application
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
