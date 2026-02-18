package seedu.misora.commandstests;

import misora.commands.AddCommand;
import misora.commands.AddCommandFactory;
import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddCommandFactoryTest {

    /**
     * Dummy storage to prevent file writing during tests.
     */
    static class DummyStorage extends Storage {
        public DummyStorage() {
            super("dummy.txt");
        }

        @Override
        public void addTaskToFile(Task task, Ui ui) {
            // Do nothing
        }
    }

    @Test
    void createTodo_withPriority_success() throws Exception {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command =
                AddCommandFactory.createTodo("todo Read book /priority high");

        String result = command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(result.contains("[T]"));
        assertTrue(result.contains("[H]"));
        assertTrue(result.contains("Read book"));
    }

    @Test
    void createTodo_withoutPriority_defaultsToMedium() throws Exception {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command =
                AddCommandFactory.createTodo("todo Read book");

        String result = command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(result.contains("[T]"));
        assertTrue(result.contains("[M]"));
        assertTrue(result.contains("Read book"));
    }

    @Test
    void createTodo_invalidPriority_returnsError() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        Exception exception = assertThrows(
                Exception.class,
                () -> AddCommandFactory.createTodo("todo Read book /priority ultra")
        );

        assertTrue(exception.getMessage().contains("Invalid priority"));
        assertEquals(0, taskList.size());
    }

    // =========================
    // DEADLINE TESTS
    // =========================

    @Test
    void createDeadline_withPriority_success() throws Exception {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command =
                AddCommandFactory.createDeadline(
                        "deadline Submit report /by 2026-02-20 /priority low");

        String result = command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("[L]"));
        assertTrue(result.contains("Submit report"));
    }

    @Test
    void createDeadline_withoutPriority_defaultsToMedium() throws Exception {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command =
                AddCommandFactory.createDeadline(
                        "deadline Submit report /by 2026-02-20");

        String result = command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("[M]"));
    }

    // =========================
    // EVENT TESTS
    // =========================

    @Test
    void createEvent_withAllFields_success() throws Exception {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command =
                AddCommandFactory.createEvent(
                        "event Team meeting /from 2pm /to 4pm /priority high");

        String result = command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(result.contains("[E]"));
        assertTrue(result.contains("[H]"));
        assertTrue(result.contains("Team meeting"));
    }

    @Test
    void createEvent_withoutPriority_defaultsToMedium() throws Exception {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new DummyStorage();

        AddCommand command =
                AddCommandFactory.createEvent(
                        "event Team meeting /from 2pm /to 4pm");

        String result = command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(result.contains("[E]"));
        assertTrue(result.contains("[M]"));
    }
}
