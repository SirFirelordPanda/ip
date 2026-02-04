package seedu.misora.commandstests;

import misora.commands.FindTaskOnDateCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindTaskOnDateCommandTest {

    private static class StubTaskList extends TaskList {
        boolean getTasksOnDateCalled = false;
        LocalDate datePassed = null;
        List<Task> tasksToReturn = List.of(); // empty list is fine

        @Override
        public List<Task> getTasksOnDate(LocalDate date) {
            getTasksOnDateCalled = true;
            datePassed = date;
            return tasksToReturn;
        }
    }

    private static class StubUi extends Ui {
        List<Task> receivedTasks = null;
        LocalDate receivedDate = null;

        @Override
        public void showTasksOnDate(List<Task> tasks, LocalDate date) {
            receivedTasks = tasks;
            receivedDate = date;
        }
    }

    @Test
    void execute_callsGetTasksOnDate_andPassesResultToUi() {
        LocalDate testDate = LocalDate.of(2026, 2, 1);
        FindTaskOnDateCommand command = new FindTaskOnDateCommand(testDate);

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        Storage storage = new Storage("dummy.txt");

        command.execute(taskList, ui, storage);

        assertTrue(taskList.getTasksOnDateCalled);
        assertEquals(testDate, taskList.datePassed);

        assertEquals(taskList.tasksToReturn, ui.receivedTasks);
        assertEquals(testDate, ui.receivedDate);
    }
}

