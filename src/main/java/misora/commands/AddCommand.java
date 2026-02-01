package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;q

import misora.exceptions.MisoraException;
import misora.tasks.Deadline;
import misora.tasks.Event;
import misora.tasks.Task;
import misora.tasks.ToDo;

public class AddCommand extends Command{

    private Task task;

    public AddCommand(String taskMsg){
        this.task = new ToDo(taskMsg);
    }

    public AddCommand(String taskMsg, String byWhen) {
        this.task = new Deadline(taskMsg, byWhen);
    }

    public AddCommand(String taskMsg, String fromWhen, String toWhen) {
        this.task = new Event(taskMsg, fromWhen, toWhen);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            task.isValidFormat();
            taskList.add(task);
            storage.addTaskToFile(task, ui);
            ui.showAddTask(task, taskList);
        } catch (MisoraException e) {
            ui.showError(e.getMessage());
        }
    }
}
