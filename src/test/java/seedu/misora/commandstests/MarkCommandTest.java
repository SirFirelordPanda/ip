package seedu.misora.commandstests;

import misora.commands.MarkCommand;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkCommandTest {

    //Stub classes

    private static class StubTask extends Task {
        boolean markCalled = false;

        StubTask() {
            super("stub task");
        }

        @Override
        public void setTaskDone(boolean taskDone) {
            markCalled = taskDone;
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
        boolean showMarkCalled = false;
        boolean showErrorCalled = false;
        String errorMessage = "";

        @Override
        public String showMarkTask(Task task) {
            showMarkCalled = true;
            return task.toString();
        }

        @Override
        public String showError(String message) {
            showErrorCalled = true;
            errorMessage = message;
            return message;
        }
    }

    private static class StubStorage extends Storage {
        StubStorage() { super("dummy.txt"); }
    }

    //Tests

    @Test
    void execute_validIndex_marksTaskAndShowsUI() {
        MarkCommand command = new MarkCommand("1");

        StubTaskList taskList = new StubTaskList();
        StubUi ui = new StubUi();
        StubStorage storage = new StubStorage();

        command.execute(taskList, ui, storage);

        assertTrue(taskList.getCalled);
        assertEquals(0, taskList.indexRequested); // 1-based → 0-based
        assertTrue(taskList.task.markCalled);
        assertTrue(ui.showMarkCalled);
        assertFalse(ui.showErrorCalled);
    }

    @Test
    void execute_invalidNumber_showsError() {
        MarkCommand command = new MarkCommand("abc");

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
        MarkCommand command = new MarkCommand("1");

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
