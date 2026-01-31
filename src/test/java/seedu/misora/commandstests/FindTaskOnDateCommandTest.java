package seedu.misora.commandstests;

import misora.commands.FindTaskOnDateCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindTaskOnDateCommandTest {

    //Stub Classes

    private static class StubTaskList extends TaskList {
        boolean showTasksOnDateCalled = false;
        LocalDate datePassed = null;

        @Override
        public void showTasksOnDate(LocalDate date) {
            showTasksOnDateCalled = true;
            datePassed = date;
        }
    }

    @Test
    void execute_callsShowTasksOnDate() {
        LocalDate testDate = LocalDate.of(2026, 2, 1);
        FindTaskOnDateCommand command = new FindTaskOnDateCommand(testDate);

        StubTaskList taskList = new StubTaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("dummy.txt");

        command.execute(taskList, ui, storage);

        assertTrue(taskList.showTasksOnDateCalled);
        assertEquals(taskList.datePassed, testDate);
    }
}
