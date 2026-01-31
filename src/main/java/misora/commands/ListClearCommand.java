package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;

public class ListClearCommand extends Command{

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.clearTaskList();
        storage.clearSavedFile();
        ui.showListClear();
    }


}
