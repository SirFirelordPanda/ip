package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;

import java.io.IOException;

public class DeleteCommand extends Command {

    private final String NUMBERPART;

    public DeleteCommand(String numberPart) {
        this.NUMBERPART = numberPart;
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {

        try {

            int numberDeleted = Integer.parseInt(NUMBERPART);
            Task removedTask = taskList.remove(numberDeleted - 1);
            ui.showDeleteTask(removedTask, taskList);
            storage.updateSavedFileFromTaskList(taskList);
        } catch (NumberFormatException e) {

            ui.showError("Invalid number given");
        } catch (IndexOutOfBoundsException e) {

            ui.showError("Number is not within list size");
        } catch (IOException e) {

            ui.showError("Unable to update the saved file from the task list");
        }
    }
}
