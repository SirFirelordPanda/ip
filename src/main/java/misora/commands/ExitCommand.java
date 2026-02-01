package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.exceptions.MisoraException;

public class ExitCommand extends Command{

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            ui.exit();
            storage.exit();
        } catch (MisoraException e) {
            ui.showError(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
