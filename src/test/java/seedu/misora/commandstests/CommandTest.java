package seedu.misora.commandstests;

import misora.commands.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    private static class TestCommand extends Command { }

    @Test
    void execute_doesNothing_noExceptionThrown() {
        Command command = new TestCommand();

        assertDoesNotThrow(() ->
                command.execute(null, null, null)
        );
    }

    @Test
    void isExit_defaultValue_returnsFalse() {
        Command command = new TestCommand();

        assertFalse(command.isExit());
    }
}
