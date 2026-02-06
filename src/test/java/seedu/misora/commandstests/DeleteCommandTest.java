package seedu.misora.commandstests;

import misora.commands.DeleteCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;
import misora.tasks.ToDo;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeleteCommandTest {

    //Stub classes

    private static class StubTaskList extends TaskList {
        boolean removeCalled = false;
        int removedIndex = -1;
        Task taskToReturn = new ToDo("read book");

        @Override
        public Task remove(int index) {
            removeCalled = true;
            removedIndex = index;
            return taskToReturn;
        }
    }

    private static class StubUi extends Ui {
        boolean showDeleteCalled = false;
        boolean showErrorCalled = false;
        String errorMessage = "";

        @Override
        public String showDeleteTask(Task task, TaskList taskList) {
            showDeleteCalled = true;
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
        boolean updateCalled = false;
        boolean throwIOException = false;

        StubStorage() {
            super("dummy.txt");
        }

        @Override
        public void updateSavedFileFromTaskList(TaskList taskList) throws IOException {
            updateCalled = true;
            if (throwIOException) {
                throw new IOException();
            }
        }
    }

    //Success case

    @Test
    void execute_validIndex_taskDeletedAndSaved() {
        DeleteCommand command = new DeleteCommand("1");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertTrue(taskList.removeCalled);
        assertEquals(0, taskList.removedIndex);
        assertTrue(ui.showDeleteCalled);
        assertFalse(ui.showErrorCalled);
        assertTrue(storage.updateCalled);
    }

    //Failure cases

    @Test
    void execute_invalidNumber_showsError() {
        DeleteCommand command = new DeleteCommand("abc");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertFalse(taskList.removeCalled);
        assertFalse(ui.showDeleteCalled);
        assertTrue(ui.showErrorCalled);
        assertEquals("Invalid number given", ui.errorMessage);
    }

    @Test
    void execute_indexOutOfBounds_showsError() {
        DeleteCommand command = new DeleteCommand("5");

        StubTaskList taskList = new StubTaskList() {
            @Override
            public Task remove(int index) {
                throw new IndexOutOfBoundsException();
            }
        };

        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertTrue(ui.showErrorCalled);
        assertEquals("Number is not within list size", ui.errorMessage);
        assertFalse(storage.updateCalled);
    }

    @Test
    void execute_storageIOException_showsError() {
        DeleteCommand command = new DeleteCommand("1");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();

        StubStorage storage = new StubStorage();
        storage.throwIOException = true;

        command.execute(taskList, ui, storage);

        assertTrue(taskList.removeCalled);
        assertFalse(ui.showDeleteCalled);
        assertTrue(ui.showErrorCalled);
        assertEquals(
                "Unable to update the saved file from the task list",
                ui.errorMessage
        );
    }
}
