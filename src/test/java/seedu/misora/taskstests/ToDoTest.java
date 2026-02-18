package seedu.misora.taskstests;

import misora.exceptions.MissingTaskMsgException;
import misora.tasks.Priority;
import misora.tasks.ToDo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//ChatGPT was used to update the test classes from what it was previously
class ToDoTest {

    // =========================
    // Constructor tests
    // =========================

    @Test
    void constructor_default_correctToString() {
        ToDo todo = new ToDo("read book");

        assertEquals("[T][ ][M] read book", todo.toString());
    }

    @Test
    void constructor_notDoneTodo_correctToString() {
        ToDo todo = new ToDo("read book", false);

        assertEquals("[T][ ][M] read book", todo.toString());
    }

    @Test
    void constructor_doneTodo_correctToString() {
        ToDo todo = new ToDo("read book", true);

        assertEquals("[T][X][M] read book", todo.toString());
    }

    @Test
    void constructor_withPriority_correctToString() {
        ToDo todo = new ToDo("read book", Priority.HIGH);

        assertEquals("[T][ ][H] read book", todo.toString());
    }

    @Test
    void constructor_doneWithPriority_correctToString() {
        ToDo todo = new ToDo("read book", true, Priority.LOW);

        assertEquals("[T][X][L] read book", todo.toString());
    }

    // =========================
    // toString() tests
    // =========================

    @Test
    void toString_hasTodoPrefix() {
        ToDo todo = new ToDo("do homework");

        assertTrue(todo.toString().startsWith("[T]"));
    }

    // =========================
    // toSavedString() tests
    // =========================

    @Test
    void toSavedString_notDone_correctFormat() {
        ToDo todo = new ToDo("do homework");

        assertEquals("T |   | M | do homework", todo.toSavedString());
    }

    @Test
    void toSavedString_done_correctFormat() {
        ToDo todo = new ToDo("do homework", true);

        assertEquals("T | X | M | do homework", todo.toSavedString());
    }

    @Test
    void toSavedString_withPriority_correctFormat() {
        ToDo todo = new ToDo("buy milk", true, Priority.HIGH);

        assertEquals("T | X | H | buy milk", todo.toSavedString());
    }

    // =========================
    // isValidFormat tests
    // =========================

    @Test
    void isValidFormat_validTodo_noExceptionThrown() {
        ToDo todo = new ToDo("buy milk");

        assertDoesNotThrow(todo::isValidFormat);
    }

    @Test
    void isValidFormat_emptyTaskMsg_throwsCustomExceptionMessage() {
        ToDo todo = new ToDo("");

        MissingTaskMsgException exception = assertThrows(
                MissingTaskMsgException.class,
                todo::isValidFormat
        );

        assertEquals(
                "WHOOPSIE!! Please enter the description of the task in this format 'todo -taskMsg-'",
                exception.getMessage()
        );
    }
}
