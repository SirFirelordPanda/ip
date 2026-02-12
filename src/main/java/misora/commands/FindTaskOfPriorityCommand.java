package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Priority;

import java.time.LocalDate;

public class FindTaskOfPriorityCommand extends Command{

    private final Priority priority;

    /**
     * Creates a {@code FindTaskOfPriorityCommand} with the specified date.
     *
     * @param priority The {@link LocalDate} to search tasks for
     */
    public FindTaskOfPriorityCommand(Priority priority) {
        assert priority != null : "priority reference should not be null";
        this.priority = priority;
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

        return ui.showTasksOfPriority(
                taskList.getTasksOfPriority(priority),
                priority
        );
    }
}
