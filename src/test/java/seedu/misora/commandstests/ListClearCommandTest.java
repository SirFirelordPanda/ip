package seedu.misora.commandstests;

import misora.commands.ListClearCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ListClearCommandTest {

    //Stub classes

    private static class StubTaskList extends TaskList {
        boolean clearTaskListCalled = false;

        @Override
        public void clearTaskList() {
            clearTaskListCalled = true;
        }
    }

    private static class StubStorage extends Storage {
        boolean clearSavedFileCalled = false;

        StubStorage() {
            super("dummy.txt");
        }

        @Override
        public void clearSavedFile() {
            clearSavedFileCalled = true;
        }
    }

    private static class StubUi extends Ui {
        boolean showListClearCalled = false;

        @Override
        public String showListClear() {
            showListClearCalled = true;
            return "";
        }
    }

    //Test

    @Test
    void execute_callsAllCollaborators() {
        ListClearCommand command = new ListClearCommand();

        StubTaskList taskList = new StubTaskList();
        StubStorage storage = new StubStorage();
        StubUi ui = new StubUi();

        command.execute(taskList, ui, storage);

        assertTrue(taskList.clearTaskListCalled);
        assertTrue(storage.clearSavedFileCalled);
        assertTrue(ui.showListClearCalled);
    }
}

