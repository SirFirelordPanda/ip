package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;

public class ListCommand extends Command{

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.listTasks(ui);
    }
}
