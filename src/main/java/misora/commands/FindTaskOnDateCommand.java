package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FindTaskOnDateCommand extends Command{

    private LocalDate date;

    public FindTaskOnDateCommand(LocalDate date) {
        this.date = date;
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {

            taskList.showTasksOnDate(date);
        } catch (DateTimeParseException e) {

            ui.showError(e.getMessage());
        }
    }
}
