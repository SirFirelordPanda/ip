package Commands;

import Components.Storage;
import Components.TaskList;
import Components.Ui;

import Exceptions.*;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.ToDo;

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
