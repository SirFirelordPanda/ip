package Commands;

import Components.Storage;
import Components.TaskList;
import Components.Ui;

import java.io.FileWriter;
import java.io.IOException;

public class ListClearCommand extends Command{

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.clearTaskList();
        storage.clearSavedFile();
        ui.showListClear();
    }


}
