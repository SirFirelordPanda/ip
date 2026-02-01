package seedu.misora.taskstests;

import misora.exceptions.MissingArgument1Exception;
import misora.exceptions.MissingArgument2Exception;
import misora.exceptions.MissingTaskMsgException;
import misora.tasks.Event;
import misora.tasks.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    //toString tests

    @Test
    void toString_localDate_correctFormat() {
        Event event = new Event(
                "project meeting",
                "2026-02-01",
                "2026-02-02"
        );

        assertEquals(
                "[E][ ] project meeting (from: Feb 01 2026 to: Feb 02 2026)",
                event.toString()
        );
    }

    @Test
    void toString_localDateTime_correctFormat() {
        Event event = new Event(
                "project meeting",
                "2026-02-01T10:00:00",
                "2026-02-01T12:00:00"
        );

        assertEquals(
                "[E][ ] project meeting (from: Feb 01 2026 10:00:00 to: Feb 01 2026 12:00:00)",
                event.toString()
        );
    }

    //toSavedString tests

    @Test
    void toSavedString_localDate_correctFormat() {
        Event event = new Event(
                "project meeting",
                "2026-02-01",
                "2026-02-02"
        );

        assertEquals(
                "E |   | project meeting | 2026-02-01 | 2026-02-02",
                event.toSavedString()
        );
    }

    @Test
    void toSavedString_localDateTime_correctFormat() {
        Event event = new Event(
                "project meeting",
                "2026-02-01T10:00:00",
                "2026-02-01T12:00:00"
        );

        assertEquals(
                "E |   | project meeting | 2026-02-01T10:00:00 | 2026-02-01T12:00:00",
                event.toSavedString()
        );
    }

    //isTaskOnDate tests

    @Test
    void isTaskOnDate_matchesFromDate_returnsTask() {
        Event event = new Event(
                "project meeting",
                "2026-02-01",
                "2026-02-02"
        );

        Task result = event.isTaskOnDate(LocalDate.of(2026, 2, 1));

        assertSame(event, result);
    }

    @Test
    void isTaskOnDate_matchesToDate_returnsTask() {
        Event event = new Event(
                "project meeting",
                "2026-02-01",
                "2026-02-02"
        );

        Task result = event.isTaskOnDate(LocalDate.of(2026, 2, 2));

        assertSame(event, result);
    }

    @Test
    void isTaskOnDate_localDateTime_match_returnsTask() {
        Event event = new Event(
                "project meeting",
                "2026-02-01T10:00:00",
                "2026-02-01T12:00:00"
        );

        Task result = event.isTaskOnDate(LocalDate.of(2026, 2, 1));

        assertSame(event, result);
    }

    @Test
    void isTaskOnDate_noMatch_returnsNull() {
        Event event = new Event(
                "project meeting",
                "2026-02-01",
                "2026-02-02"
        );

        Task result = event.isTaskOnDate(LocalDate.of(2026, 2, 3));

        assertNull(result);
    }

    //doesTaskContainString tests

    @Test
    void doesTaskContainString_descriptionMatch_returnsTask() {
        Event event = new Event(
                "team meeting",
                "2026-02-01T10:00",
                "2026-02-01T12:00"
        );

        Task result = event.doesTaskContainString("meeting");

        assertSame(event, result);
    }

    @Test
    void doesTaskContainString_fromWhenStringMatch_returnsTask() {
        Event event = new Event(
                "team meeting",
                "2026-02-01T10:00",
                "2026-02-01T12:00"
        );

        Task result = event.doesTaskContainString("10:00");

        assertSame(event, result);
    }

    @Test
    void doesTaskContainString_toWhenStringMatch_returnsTask() {
        Event event = new Event(
                "team meeting",
                "2026-02-01T10:00",
                "2026-02-01T12:00"
        );

        Task result = event.doesTaskContainString("12:00");

        assertSame(event, result);
    }

    @Test
    void doesTaskContainString_fromWhenFormattedDisplayMatch_returnsTask() {
        Event event = new Event(
                "team meeting",
                "2026-02-01",
                "2026-02-02"
        );

        Task result = event.doesTaskContainString("Feb 01");

        assertSame(event, result);
    }

    @Test
    void doesTaskContainString_toWhenFormattedDisplayMatch_returnsTask() {
        Event event = new Event(
                "team meeting",
                "2026-02-01",
                "2026-02-02"
        );

        Task result = event.doesTaskContainString("Feb 02");

        assertSame(event, result);
    }

    @Test
    void doesTaskContainString_fromWhenFormattedSaveMatch_returnsTask() {
        Event event = new Event(
                "team meeting",
                "2026-02-01",
                "2026-02-02"
        );

        Task result = event.doesTaskContainString("2026-02-01");

        assertSame(event, result);
    }

    @Test
    void doesTaskContainString_toWhenFormattedSaveMatch_returnsTask() {
        Event event = new Event(
                "team meeting",
                "2026-02-01",
                "2026-02-02"
        );

        Task result = event.doesTaskContainString("2026-02-02");

        assertSame(event, result);
    }

    @Test
    void doesTaskContainString_noMatch_returnsNull() {
        Event event = new Event(
                "team meeting",
                "2026-02-01",
                "2026-02-02"
        );

        Task result = event.doesTaskContainString("deadline");

        assertNull(result);
    }

    //isValidFormat tests

    @Test
    void isValidFormat_validEvent_noExceptionThrown() {
        Event event = new Event(
                "project meeting",
                "2026-02-01",
                "2026-02-02"
        );

        assertDoesNotThrow(event::isValidFormat);
    }

    @Test
    void isValidFormat_missingTaskMsg_throwsCustomMessage() {
        Event event = new Event(
                "",
                "2026-02-01",
                "2026-02-02"
        );

        MissingTaskMsgException e = assertThrows(
                MissingTaskMsgException.class,
                event::isValidFormat
        );

        assertEquals(
                "WHOOPSIE!! Please enter the description of the event in this format\n" +
                        "'event -taskMsg- /from -fromWhen- /to -toWhen-'",
                e.getMessage()
        );
    }

    @Test
    void isValidFormat_missingFromWhen_throwsMissingArgument1Exception() {
        Event event = new Event(
                "project meeting",
                "",
                "2026-02-02"
        );

        MissingArgument1Exception e = assertThrows(
                MissingArgument1Exception.class,
                event::isValidFormat
        );

        assertEquals(
                "WHOOPSIE!! Please enter the start time of the event in this format\n" +
                        "'event -taskMsg- /from -fromWhen- /to -toWhen-'",
                e.getMessage()
        );
    }

    @Test
    void isValidFormat_missingToWhen_throwsMissingArgument2Exception() {
        Event event = new Event(
                "project meeting",
                "2026-02-01",
                ""
        );

        MissingArgument2Exception e = assertThrows(
                MissingArgument2Exception.class,
                event::isValidFormat
        );

        assertEquals(
                "WHOOPSIE!! Please enter the end time of the event in this format\n" +
                        "'event -taskMsg- /from -fromWhen- /to -toWhen-'",
                e.getMessage()
        );
    }
}
