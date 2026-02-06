package seedu.misora.commandstests;

import misora.commands.FindCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FindCommandTest {

    private static class StubTaskList extends TaskList {
        String receivedSearchString = null;
        List<Task> tasksToReturn = List.of();

        @Override
        public List<Task> getTasksContainingString(String searchString) {
            receivedSearchString = searchString;
            return tasksToReturn;
        }
    }

    private static class StubUi extends Ui {
        List<Task> receivedTasks = null;
        String receivedSearchString = null;

        @Override
        public String showTasksContainingString(List<Task> tasks, String searchString) {
            receivedTasks = tasks;
            receivedSearchString = searchString;
            return "";
        }
    }

    private static class StubStorage extends Storage {
        public StubStorage() {
            super("stub.txt");
        }
    }

    @Test
    void execute_validSearchString_callsTaskListAndUi() {
        FindCommand cmd = new FindCommand("report");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        cmd.execute(taskList, ui, storage);

        assertEquals("report", taskList.receivedSearchString);
        assertEquals(taskList.tasksToReturn, ui.receivedTasks);
        assertEquals("report", ui.receivedSearchString);
    }
}

