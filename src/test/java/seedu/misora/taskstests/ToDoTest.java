package seedu.misora.taskstests;

import misora.exceptions.MissingTaskMsgException;
import misora.tasks.ToDo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTest {

    //constructor tests

    @Test
    void constructor_default_correctToString() {
        ToDo todo = new ToDo("read book");

        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    void constructor_notDoneTodo_correctToString() {
        ToDo todo = new ToDo("read book", false);

        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    void constructor_doneTodo_correctToString() {
        ToDo todo = new ToDo("read book", true);

        assertEquals("[T][X] read book", todo.toString());
    }

    //toString tests

    @Test
    void toString_hasTodoPrefix() {
        ToDo todo = new ToDo("do homework");

        assertTrue(todo.toString().startsWith("[T]"));
    }

    //toSavedString tests

    @Test
    void toSavedString_notDone_correctFormat() {
        ToDo todo = new ToDo("do homework");

        assertEquals("T |   | do homework", todo.toSavedString());
    }

    @Test
    void toSavedString_done_correctFormat() {
        ToDo todo = new ToDo("do homework", true);

        assertEquals("T | X | do homework", todo.toSavedString());
    }

    //isValidFormat tests

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

