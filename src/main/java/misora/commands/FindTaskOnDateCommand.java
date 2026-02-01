package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a {@code FindTaskOnDateCommand} in the Misora application.
 * <p>
 * A {@code FindTaskOnDateCommand} displays all tasks in the {@link TaskList}
 * that occur on a specified {@link LocalDate}.
 */
public class FindTaskOnDateCommand extends Command{

    /**
     * The date used to filter tasks.
     */
    private LocalDate date;

    /**
     * Creates a {@code FindTaskOnDateCommand} with the specified date.
     *
     * @param date The {@link LocalDate} to search tasks for
     */
    public FindTaskOnDateCommand(LocalDate date) {
        this.date = date;
    }

    /**
     * Executes the find command by displaying all tasks that occur on the
     * specified date.
     * <p>
     * If the date format is invalid or an error occurs during execution,
     * an appropriate error message is shown to the user.
     *
     * @param taskList The {@link TaskList} to search for matching tasks
     * @param ui The {@link Ui} used to display error messages
     * @param storage The {@link Storage} (not used by this command)
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
            taskList.showTasksOnDate(date);
    }
}
