package Commands;

import Components.Storage;
import Components.TaskList;
import Components.Ui;
import Tasks.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FindTaskOnDateCommand extends Command{

    LocalDate date;

    public FindTaskOnDateCommand(LocalDate date) {
        this.date = date;
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {

            taskList.showTasksOnDate(date);
        } catch (DateTimeParseException e) {

            System.out.println(e.getMessage());
        }
    }
}
