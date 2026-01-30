package Commands;

import Components.Storage;
import Components.TaskList;
import Components.Ui;

public class ListCommand extends Command{

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {

        taskList.listTasks(ui);
    }
}
