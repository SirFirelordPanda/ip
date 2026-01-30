package Commands;

import Components.Storage;
import Components.TaskList;
import Components.Ui;

public class ExitCommand extends Command{

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
