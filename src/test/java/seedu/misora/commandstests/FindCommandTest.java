package seedu.misora.commandstests;

import misora.commands.FindCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FindCommandTest {

    private static class StubTaskList extends TaskList {
        String receivedSearchString = null;

        public StubTaskList() {
            super();
        }

        @Override
        public void showTasksContainingString(String searchString) {
            receivedSearchString = searchString;
        }
    }

    private static class StubUi extends Ui {
    }

    private static class StubStorage extends Storage {
        public StubStorage() {
            super("stub.txt");
        }
    }

    @Test
    void execute_validSearchString_callsShowTasksContainingString() {
        FindCommand cmd = new FindCommand("report");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        cmd.execute(taskList, ui, storage);

        assertEquals("report", taskList.receivedSearchString,
                "TaskList.showTasksContainingString() should be called with search string");
    }
}
