package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;

public class FindCommand extends Command{
    private String searchString;

    public FindCommand(String searchString) {
        this.searchString = searchString;
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
            taskList.showTasksContainingString(searchString);
    }
}
