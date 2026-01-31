package seedu.misora.commandstests;

import misora.commands.UnmarkCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;
import misora.tasks.ToDo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnmarkCommandTest {

    //Stub classes

    private static class StubTask extends Task {
        boolean unmarkCalled = true; // Will set to false when called

        StubTask() {
            super("stub task");
        }

        @Override
        public void setTaskDone(boolean taskDone) {
            unmarkCalled = !taskDone; // true if taskDone == false
            super.setTaskDone(taskDone);
        }
    }

    private static class StubTaskList extends TaskList {
        StubTask task = new StubTask();
        boolean getCalled = false;
        int indexRequested = -1;

        @Override
        public Task get(int index) {
            getCalled = true;
            indexRequested = index;
            return task;
        }
    }

    private static class StubUi extends Ui {
        boolean showUnmarkCalled = false;
        boolean showErrorCalled = false;
        String errorMessage = "";

        @Override
        public void showUnmarkTask(Task task) {
            showUnmarkCalled = true;
        }

        @Override
        public void showError(String message) {
            showErrorCalled = true;
            errorMessage = message;
        }
    }

    private static class StubStorage extends Storage {
        StubStorage() { super("dummy.txt"); }
    }

    //Tests

    @Test
    void execute_validIndex_unmarksTaskAndShowsUI() {
        UnmarkCommand command = new UnmarkCommand("1");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertTrue(taskList.getCalled);
        assertEquals(0, taskList.indexRequested); // 1-based -> 0-based
        assertTrue(taskList.task.unmarkCalled);
        assertTrue(ui.showUnmarkCalled);
        assertFalse(ui.showErrorCalled);
    }

    @Test
    void execute_invalidNumber_showsError() {
        UnmarkCommand command = new UnmarkCommand("abc");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertFalse(taskList.getCalled);
        assertTrue(ui.showErrorCalled);
        assertEquals("Invalid number given", ui.errorMessage);
    }

    @Test
    void execute_indexOutOfBounds_showsError() {
        UnmarkCommand command = new UnmarkCommand("1");

        StubTaskList taskList = new StubTaskList() {
            @Override
            public Task get(int index) {
                throw new IndexOutOfBoundsException();
            }
        };

        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertTrue(ui.showErrorCalled);
        assertEquals("Number is not within list size", ui.errorMessage);
    }
}
