package seedu.misora.commandstests;

import misora.commands.AddCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddCommandTest {

    //Stub classes

    private static class StubTaskList extends TaskList {
        boolean addCalled = false;

        @Override
        public void add(Task task) {
            addCalled = true;
        }
    }

    private static class StubUi extends Ui {
        boolean showAddCalled = false;
        boolean showErrorCalled = false;
        String errorMessage = "";

        @Override
        public String showAddTask(Task task, TaskList taskList) {
            showAddCalled = true;
            return "";
        }

        @Override
        public String showError(String message) {
            showErrorCalled = true;
            errorMessage = message;
            return "";
        }
    }

    private static class StubStorage extends Storage {
        boolean saveCalled = false;

        StubStorage() {
            super("dummy.txt");
        }

        @Override
        public void addTaskToFile(Task task, Ui ui) {
            saveCalled = true;
        }
    }

    //Success cases

    @Test
    void execute_validTodo_taskAddedAndSaved() {
        AddCommand command = new AddCommand("read book");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertTrue(taskList.addCalled);
        assertTrue(storage.saveCalled);
        assertTrue(ui.showAddCalled);
        assertFalse(ui.showErrorCalled);
    }

    @Test
    void execute_validDeadline_taskAddedAndSaved() {
        AddCommand command = new AddCommand(
                "submit report",
                "2026-02-01"
        );

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertTrue(taskList.addCalled);
        assertTrue(storage.saveCalled);
        assertTrue(ui.showAddCalled);
    }

    @Test
    void execute_validEvent_taskAddedAndSaved() {
        AddCommand command = new AddCommand(
                "project meeting",
                "2026-02-01",
                "2026-02-02"
        );

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertTrue(taskList.addCalled);
        assertTrue(storage.saveCalled);
        assertTrue(ui.showAddCalled);
    }

    //Failure cases

    @Test
    void execute_invalidTodo_errorShown_taskNotAdded() {
        AddCommand command = new AddCommand("");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertFalse(taskList.addCalled);
        assertFalse(storage.saveCalled);
        assertFalse(ui.showAddCalled);
        assertTrue(ui.showErrorCalled);
        assertFalse(ui.errorMessage.isEmpty());
    }
}
