package misora.commands;

import misora.components.TaskList;
import misora.components.Ui;
import misora.components.Storage;

public abstract class Command {
    public void execute(TaskList taskList, Ui ui, Storage storage) {

    }

    public boolean isExit() {
        return false;
    }
}
