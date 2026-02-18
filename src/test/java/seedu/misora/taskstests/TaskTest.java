package seedu.misora.taskstests;

import misora.exceptions.MissingTaskMsgException;
import misora.tasks.Task;
import misora.tasks.Priority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//ChatGPT was used to update the test classes from what it was previously
class TaskTest {

    // Helper subclass for testing abstract Task
    public class TestTask extends Task {

        public TestTask(String taskMsg) {
            super(taskMsg);
        }

        public TestTask(String taskMsg, boolean isDone) {
            super(taskMsg, isDone);
        }

        public TestTask(String taskMsg, Priority priority) {
            super(taskMsg, priority);
        }

        public TestTask(String taskMsg, boolean isDone, Priority priority) {
            super(taskMsg, isDone, priority);
        }
    }

    // =========================
    // Constructor tests
    // =========================

    @Test
    void constructor_default_correctMark() {
        Task task = new TestTask("read book");

        assertEquals(' ', task.isTaskDoneMark());
    }

    @Test
    void constructor_doneTask_correctMark() {
        Task task = new TestTask("read book", true);

        assertEquals('X', task.isTaskDoneMark());
    }

    @Test
    void constructor_withPriority_correctPriority() {
        Task task = new TestTask("read book", Priority.HIGH);

        assertEquals(Priority.HIGH, task.getPriority());
    }

    @Test
    void constructor_doneWithPriority_correctPriorityAndMark() {
        Task task = new TestTask("read book", true, Priority.LOW);

        assertEquals(Priority.LOW, task.getPriority());
        assertEquals('X', task.isTaskDoneMark());
    }

    // =========================
    // setTaskDone tests
    // =========================

    @Test
    void setTaskDone_done_markUpdated() {
        Task task = new TestTask("read book");

        task.setTaskDone(true);

        assertEquals('X', task.isTaskDoneMark());
    }

    @Test
    void setTaskDone_notDone_markUpdated() {
        Task task = new TestTask("read book", true);

        task.setTaskDone(false);

        assertEquals(' ', task.isTaskDoneMark());
    }

    // =========================
    // toString() tests
    // =========================

    @Test
    void toString_notDone_correctFormat() {
        Task task = new TestTask("read book", Priority.MEDIUM);

        assertEquals("[ ][M] read book", task.toString());
    }

    @Test
    void toString_done_correctFormat() {
        Task task = new TestTask("read book", true, Priority.HIGH);

        assertEquals("[X][H] read book", task.toString());
    }

    // =========================
    // toSavedString() tests
    // =========================

    @Test
    void toSavedString_notDone_correctFormat() {
        Task task = new TestTask("read book", Priority.MEDIUM);

        assertEquals("  | M | read book", task.toSavedString());
    }

    @Test
    void toSavedString_done_correctFormat() {
        Task task = new TestTask("read book", true, Priority.LOW);

        assertEquals("X | L | read book", task.toSavedString());
    }

    // =========================
    // isValidFormat tests
    // =========================

    @Test
    void isValidFormat_validTask_noExceptionThrown() {
        Task task = new TestTask("read book");

        assertDoesNotThrow(task::isValidFormat);
    }

    @Test
    void isValidFormat_emptyTaskMsg_throwsMissingTaskMsgException() {
        Task task = new TestTask("");

        assertThrows(MissingTaskMsgException.class, task::isValidFormat);
    }

    // =========================
    // doesTaskContainString tests
    // =========================

    @Test
    void doesTaskContainString_descriptionMatch_returnsTask() {
        Task task = new TestTask("read book");

        Task result = task.doesTaskContainString("read");

        assertSame(task, result);
    }

    @Test
    void doesTaskContainString_descriptionDoesNotMatch_returnsNull() {
        Task task = new TestTask("read book");

        Task result = task.doesTaskContainString("submit");

        assertNull(result);
    }
}
