package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;

public class UnmarkCommand extends Command{

    private final String NUMBERPART;

    public UnmarkCommand(String numberPart) {
        this.NUMBERPART = numberPart;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            int numberToUnmark = Integer.parseInt(NUMBERPART);
            Task taskToUnmark = taskList.get(numberToUnmark - 1);
            taskToUnmark.setTaskDone(false);
            ui.showUnmarkTask(taskToUnmark);
        } catch (NumberFormatException e) {
            ui.showError("Invalid number given");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Number is not within list size");
        }
    }
}
