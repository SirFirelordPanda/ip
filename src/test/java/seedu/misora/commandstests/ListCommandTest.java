package seedu.misora.commandstests;

import misora.commands.ListCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListCommandTest {

    //Stub classes

    private static class StubTaskList extends TaskList {
        boolean listTasksCalled = false;

        @Override
        public List<Task> listTasks() {
            listTasksCalled = true;  // mark that the method was called
            return super.listTasks(); // can return empty list
        }
    }


    //Test

    @Test
    void execute_callsListTasks() {
        ListCommand command = new ListCommand();

        StubTaskList taskList = new StubTaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("dummy.txt");

        command.execute(taskList, ui, storage);

        assertTrue(taskList.listTasksCalled, "listTasks() should be called");
    }
}
