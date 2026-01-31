package seedu.misora.commandstests;

import misora.commands.ListCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListCommandTest {

    //Stub classes

    private static class StubTaskList extends TaskList {
        boolean listTasksCalled = false;
        Ui uiPassed = null;

        @Override
        public void listTasks(Ui ui) {
            listTasksCalled = true;
            uiPassed = ui;
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

        assertTrue(taskList.listTasksCalled);
        assertSame(taskList.uiPassed, ui);
    }
}
