package seedu.misora.commandstests;

import misora.commands.ExitCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExitCommandTest {

    private static class StubUi extends Ui {
        boolean exitCalled = false;

        @Override
        public void exit() {
            exitCalled = true;
        }
    }

    private static class StubStorage extends Storage {
        boolean exitCalled = false;

        public StubStorage() {
            super("stub.txt");
        }

        public StubStorage(String path) {
            super(path);
        }

        public void exit() {
            exitCalled = true;
        }
    }

    @Test
    void isExit_returnsTrue() {
        ExitCommand cmd = new ExitCommand();
        assertTrue(cmd.isExit());
    }

    @Test
    void execute_callsUiAndStorageExit() {
        ExitCommand cmd = new ExitCommand();

        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();
        TaskList taskList = new TaskList();

        cmd.execute(taskList, ui, storage);

        // verify exit methods were called
        assertTrue(ui.exitCalled, "Ui.exit() should be called");
        assertTrue(storage.exitCalled, "Storage.exit() should be called");
    }
}
