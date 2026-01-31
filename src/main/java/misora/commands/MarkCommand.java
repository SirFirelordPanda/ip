package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;

public class MarkCommand extends Command{

    private final String NUMBERPART;

    public MarkCommand(String numberPart) {
        this.NUMBERPART = numberPart;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {

        try {

            int numberToMark = Integer.parseInt(NUMBERPART);
            Task taskToMark = taskList.get(numberToMark - 1);
            taskToMark.setTaskDone(true);
            ui.showMarkTask(taskToMark);
        } catch (NumberFormatException e) {

            ui.showError("Invalid number given");
        } catch (IndexOutOfBoundsException e) {

            ui.showError("Number is not within list size");
        }
    }

}
