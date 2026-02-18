package misora.commands;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Priority;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddCommandTest {

    /**
     * Dummy storage that does nothing (prevents file I/O in tests).
     */
    static class DummyStorage extends Storage {
        public DummyStorage() {
            super("dummy.txt");
        }

        @Override
        public void addTaskToFile(misora.tasks.Task task, Ui ui) {
            // Do nothing (avoid writing to file during test)
        }
    }

    @Test
    void execute_addTodoTask_success() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command = new AddCommand("Read book", Priority.MEDIUM);

        String result = command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(result.contains("[T][ ][M] Read book"));
    }

    @Test
    void execute_addDeadlineTask_success() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command =
                new AddCommand("Submit assignment", "2026-02-20", Priority.HIGH);

        String result = command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(result.contains("[D][ ][H] Submit assignment (by: Feb 20 2026)"));
    }

    @Test
    void execute_addEventTask_success() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command =
                new AddCommand("Team meeting", "2pm", "4pm", Priority.LOW);

        String result = command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(result.contains("[E][ ][L] Team meeting (from: 2pm to: 4pm)"));
    }

    @Test
    void execute_invalidTaskFormat_returnsErrorMessage() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command = new AddCommand("", Priority.MEDIUM);

        String result = command.execute(taskList, ui, storage);

        assertEquals(0, taskList.size());
        assertTrue(result.toLowerCase().contains("whoopsie!!"));
    }
}
