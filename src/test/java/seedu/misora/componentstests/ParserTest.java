package seedu.misora.componentstests;

import misora.components.Parser;
import misora.commands.*;
import misora.exceptions.MisoraException;
import misora.exceptions.UnhandledCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse_bye_returnsExitCommand() throws MisoraException {
        assertInstanceOf(ExitCommand.class, Parser.parse("bye"));
    }

    @Test
    void parse_list_returnsListCommand() throws MisoraException {
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
    }

    @Test
    void parse_listClear_returnsListClearCommand() throws MisoraException {
        assertInstanceOf(ListClearCommand.class, Parser.parse("list clear"));
    }

    @Test
    void parse_mark_returnsMarkCommand() throws MisoraException {
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
    }

    @Test
    void parse_unmark_returnsUnmarkCommand() throws MisoraException {
        assertInstanceOf(UnmarkCommand.class, Parser.parse("unmark 2"));
    }

    @Test
    void parse_todo_returnsAddCommand() throws MisoraException {
        assertInstanceOf(AddCommand.class, Parser.parse("todo read book"));
    }

    @Test
    void parse_deadline_returnsAddCommand() throws MisoraException {
        assertInstanceOf(AddCommand.class, Parser.parse("deadline submit report /by 2026-02-01"));
    }

    @Test
    void parse_event_returnsAddCommand() throws MisoraException {
        assertInstanceOf(AddCommand.class, Parser.parse("event meeting /from 2026-02-01 /to 2026-02-02"));
    }

    @Test
    void parse_delete_returnsDeleteCommand() throws MisoraException {
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 3"));
    }

    @Test
    void parse_tasksOn_returnsFindTaskOnDateCommand() throws MisoraException {
        assertInstanceOf(FindTaskOnDateCommand.class, Parser.parse("tasks on 2026-02-01"));
    }

    @Test
    void parse_invalidCommand_throwsUnhandledCommandException() {
        assertThrows(UnhandledCommandException.class, () -> Parser.parse("meow"));
    }
}
