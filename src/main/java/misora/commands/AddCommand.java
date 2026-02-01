package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;q

import misora.exceptions.MisoraException;
import misora.tasks.Deadline;
import misora.tasks.Event;
import misora.tasks.Task;
import misora.tasks.ToDo;

/**
 * Represents an {@code AddCommand} in the Misora application.
 * An {@code AddCommand} creates a new {@link Task} based on the provided
 * parameters and adds it to the {@link TaskList}. The task is also persisted
 * to {@link Storage} and displayed to the user via the {@link Ui}.
 */
public class AddCommand extends Command{

    /**
     * The task to be added.
     */
    private Task task;

    /**
     * Creates an {@code AddCommand} that adds a {@link ToDo} task.
     *
     * @param taskMsg The description of the task
     */
    public AddCommand(String taskMsg){
        this.task = new ToDo(taskMsg);
    }

    /**
     * Creates an {@code AddCommand} that adds a {@link Deadline} task.
     *
     * @param taskMsg The description of the task
     * @param byWhen The deadline of the task
     */
    public AddCommand(String taskMsg, String byWhen) {
        this.task = new Deadline(taskMsg, byWhen);
    }

    /**
     * Creates an {@code AddCommand} that adds an {@link Event} task.
     *
     * @param taskMsg The description of the task
     * @param fromWhen The start date/time of the event
     * @param toWhen The end date/time of the event
     */
    public AddCommand(String taskMsg, String fromWhen, String toWhen) {
        this.task = new Event(taskMsg, fromWhen, toWhen);
    }

    /**
     * Executes the add command by validating the task, adding it to the task list,
     * saving it to storage, and displaying the result to the user.
     * <p>
     * If task validation fails or an error occurs during execution, an error
     * message is displayed to the user via the {@link Ui}.
     *
     * @param taskList The {@link TaskList} to add the task to
     * @param ui The {@link Ui} used to display feedback to the user
     * @param storage The {@link Storage} used to persist the task
     */
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
