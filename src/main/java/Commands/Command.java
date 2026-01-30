package Commands;

import Components.TaskList;
import Components.Ui;
import Components.Storage;

public abstract class Command {
    public void execute(TaskList taskList, Ui ui, Storage storage) {

    }

    public boolean isExit() {
        return false;
    }
}
