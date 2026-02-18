package seedu.misora.taskstests;

import misora.exceptions.MissingArgument1Exception;
import misora.exceptions.MissingTaskMsgException;
import misora.tasks.Deadline;
import misora.tasks.Priority;
import misora.tasks.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//ChatGPT was used to update the test classes from what it was previously
class DeadlineTest {

    // =========================
    // toString() tests
    // =========================

    @Test
    void toString_localDate_correctFormatWithPriority() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.HIGH);

        assertEquals("[D][ ][H] submit report (by: Feb 01 2026)", deadline.toString());
    }

    @Test
    void toString_localDateTime_correctFormatWithPriority() {
        Deadline deadline = new Deadline("submit report", "2026-02-01T23:59:59", Priority.LOW);

        assertEquals("[D][ ][L] submit report (by: Feb 01 2026 23:59:59)", deadline.toString());
    }

    @Test
    void constructor_invalidDate_correctFormatWithPriority() {
        Deadline deadline = new Deadline("submit report", "not-a-date", Priority.MEDIUM);

        assertEquals("[D][ ][M] submit report (by: not-a-date)", deadline.toString());
    }

    // =========================
    // toSavedString() tests
    // =========================

    @Test
    void toSavedString_localDate_correctFormatWithPriority() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.HIGH);

        assertEquals("D |   | H | submit report | 2026-02-01", deadline.toSavedString());
    }

    @Test
    void toSavedString_localDateTime_correctFormatWithPriority() {
        Deadline deadline = new Deadline("submit report", "2026-02-01T23:59:59", Priority.LOW);

        assertEquals("D |   | L | submit report | 2026-02-01T23:59:59", deadline.toSavedString());
    }

    // =========================
    // isTaskOnDate() tests
    // =========================

    @Test
    void isTaskOnDate_localDate_match_returnsTask() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.MEDIUM);

        Task result = deadline.isTaskOnDate(LocalDate.of(2026, 2, 1));
        assertSame(deadline, result);
    }

    @Test
    void isTaskOnDate_localDateTime_match_returnsTask() {
        Deadline deadline = new Deadline("submit report", "2026-02-01T23:59:59", Priority.MEDIUM);

        Task result = deadline.isTaskOnDate(LocalDate.of(2026, 2, 1));
        assertSame(deadline, result);
    }

    @Test
    void isTaskOnDate_noMatch_returnsNull() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.MEDIUM);

        Task result = deadline.isTaskOnDate(LocalDate.of(2026, 2, 2));
        assertNull(result);
    }

    // =========================
    // doesTaskContainString() tests
    // =========================

    @Test
    void doesTaskContainString_descriptionMatch_returnsTask() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.HIGH);
        Task result = deadline.doesTaskContainString("submit");
        assertSame(deadline, result);
    }

    @Test
    void doesTaskContainString_localDateStringMatch_returnsTask() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.HIGH);
        Task result = deadline.doesTaskContainString("2026-02-01");
        assertSame(deadline, result);
    }

    @Test
    void doesTaskContainString_localDateTimeStringMatch_returnsTask() {
        Deadline deadline = new Deadline("submit report", "2026-02-01T23:59:59", Priority.LOW);
        Task result = deadline.doesTaskContainString("23:59");
        assertSame(deadline, result);
    }

    @Test
    void doesTaskContainString_formattedDisplayMatch_returnsTask() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.MEDIUM);
        Task result = deadline.doesTaskContainString("Feb");
        assertSame(deadline, result);
    }

    @Test
    void doesTaskContainString_formattedSaveMatch_returnsTask() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.MEDIUM);
        Task result = deadline.doesTaskContainString("2026");
        assertSame(deadline, result);
    }

    @Test
    void doesTaskContainString_noMatch_returnsNull() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.HIGH);
        Task result = deadline.doesTaskContainString("meeting");
        assertNull(result);
    }

    // =========================
    // isValidFormat() tests
    // =========================

    @Test
    void isValidFormat_validDeadline_noExceptionThrown() {
        Deadline deadline = new Deadline("submit report", "2026-02-01", Priority.HIGH);
        assertDoesNotThrow(deadline::isValidFormat);
    }

    @Test
    void isValidFormat_missingTaskMsg_throwsCustomMessage() {
        Deadline deadline = new Deadline("", "2026-02-01", Priority.LOW);

        MissingTaskMsgException e = assertThrows(
                MissingTaskMsgException.class,
                deadline::isValidFormat
        );

        assertEquals(
                "WHOOPSIE!! Please enter the description of the task in this format\n" +
                        "'deadline -taskMsg- /by -byWhen-'",
                e.getMessage()
        );
    }

    @Test
    void isValidFormat_missingByWhen_throwsMissingArgument1Exception() {
        Deadline deadline = new Deadline("submit report", "", Priority.MEDIUM);

        MissingArgument1Exception e = assertThrows(
                MissingArgument1Exception.class,
                deadline::isValidFormat
        );

        assertEquals(
                "WHOOPSIE!! Please enter the deadline of the task in this format\n" +
                        "'deadline -taskMsg- /by -byWhen-'",
                e.getMessage()
        );
    }
}
