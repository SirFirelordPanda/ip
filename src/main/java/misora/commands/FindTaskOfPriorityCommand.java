package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Priority;

/**
 * Represents a {@code FindTaskOnPriorityCommand} in the Misora application.
 * <p>
 * A {@code FindTaskOnPriorityCommand} displays all tasks in the {@link TaskList}
 * that have the specified {@link Priority}.
 */
public class FindTaskOfPriorityCommand extends Command {

    private final String priority;

    /**
     * Creates a {@code FindTaskOfPriorityCommand} with the specified priority.
     *
     * @param priorityRaw The {@link Priority} to search tasks for
     */
    public FindTaskOfPriorityCommand(String priorityRaw) {
        assert priorityRaw != null : "priority reference should not be null";
        this.priority = priorityRaw;
    }

    /**
     * Executes the find command by displaying all tasks that have the specified priority
     *
     * @param taskList The {@link TaskList} to search for matching tasks
     * @param ui The {@link Ui} used to display error messages
     * @param storage The {@link Storage} (not used by this command)
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        assert taskList != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";

        try {
            Priority p = Priority.valueOf(priority);
            return ui.showTasksOfPriority(
                    taskList.getTasksOfPriority(p),
                    p
            );
        } catch (Exception e) {
            return ui.showError("Invalid priority, valid priorities are: HIGH, MEDIUM, LOW");
        }
    }
}
