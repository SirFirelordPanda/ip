package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;

public class ExitCommand extends Command{

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
