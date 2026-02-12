package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.exceptions.MisoraException;
import misora.tasks.*;

/**
 * Represents an {@code AddCommand} in the Misora application.
 * An {@code AddCommand} creates a new {@link Task} based on the provided
 * parameters and adds it to the {@link TaskList}. The task is also persisted
 * to {@link Storage} and displayed to the user via the {@link Ui}.
 */
public class AddCommand extends Command {

    /**
     * The task to be added.
     */
    private final Task task;

    /**
     * Creates an {@code AddCommand} that adds a {@link ToDo} task.
     *
     * @param taskMsg The description of the task
     */
    public AddCommand(String taskMsg, Priority priority) {
        assert taskMsg != null : "Task description reference should not be null";
        this.task = new ToDo(taskMsg, priority);
    }

    /**
     * Creates an {@code AddCommand} that adds a {@link Deadline} task.
     *
     * @param taskMsg The description of the task
     * @param byWhen The deadline of the task
     */
    public AddCommand(String taskMsg, String byWhen, Priority priority) {
        assert taskMsg != null : "Task description reference should not be null";
        assert byWhen != null : "Deadline reference should not be null";
        this.task = new Deadline(taskMsg, byWhen, priority);
    }

    /**
     * Creates an {@code AddCommand} that adds an {@link Event} task.
     *
     * @param taskMsg The description of the task
     * @param fromWhen The start date/time of the event
     * @param toWhen The end date/time of the event
     */
    public AddCommand(String taskMsg, String fromWhen, String toWhen, Priority priority) {
        assert taskMsg != null : "Task description reference should not be null";
        assert fromWhen != null : "Event start time reference should not be null";
        assert toWhen != null : "Event end time reference should not be null";
        this.task = new Event(taskMsg, fromWhen, toWhen, priority);
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
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            assert taskList != null : "TaskList must exist";
            assert ui != null : "Ui must exist";
            assert storage != null : "Storage must exist";
            assert task != null : "Task must be initialized before execution";

            task.isValidFormat();
            taskList.add(task);
            storage.addTaskToFile(task, ui);
            return ui.showAddTask(task, taskList);
        } catch (MisoraException e) {
            return ui.showError(e.getMessage());
        }
    }
}
