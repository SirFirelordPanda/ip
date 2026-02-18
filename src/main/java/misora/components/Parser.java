package misora.components;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import misora.commands.*;
import misora.exceptions.MisoraException;
import misora.exceptions.UnhandledCommandException;

/**
 * Represents a parser that parses user input into executable {@link Command} objects
 * in the Misora application.
 * <p>
 * The {@code Parser} interprets the raw string entered by the user and determines
 * which {@link Command} subclass should be created to perform the requested action.
 * Commands include adding tasks, marking/unmarking tasks, listing tasks, deleting tasks,
 * finding tasks on a specific date, clearing the task list, and exiting the application.
*/
public class Parser {

    /**
     * Parses a raw user input string and returns the corresponding {@link Command}.
     * <p>
     * The method identifies the type of command based on keywords such as "bye", "list",
     * "todo", "deadline", "event", "mark", "unmark", "delete", "tasks on" and "find".
     * It extracts any necessary arguments (e.g., task description, dates, or task numbers)
     * and constructs the appropriate {@link Command} subclass.
     *
     * @param fullCommand The raw input string entered by the user
     * @return A {@link Command} object corresponding to the user input
     * @throws MisoraException If the input cannot be parsed correctly, or if
     *         a task-related error occurs during command creation
     * @throws UnhandledCommandException If the command keyword does not match
     *         any recognized command
     */
    //used ChatGPT to refactor this method so that it would oblige SLAP
    public static Command parse(String fullCommand) throws MisoraException {
        assert fullCommand != null : "fullCommand reference should not be null";

        String input = fullCommand.trim();
        String lower = input.toLowerCase();

        String[] parts = lower.split(" ", 2);
        String commandWord = parts[0];

        switch (commandWord) {
        case "bye":
            return new ExitCommand();

        case "list":
            if (lower.equals("list clear")) {
                return new ListClearCommand();
            }
            return new ListCommand();

        case "mark":
            return parseMark(input);

        case "unmark":
            return parseUnmark(input);

        case "todo":
            return parseTodo(input);

        case "deadline":
            return parseDeadline(input);

        case "event":
            return parseEvent(input);

        case "delete":
            return parseDelete(input);

        case "date":
            return parseTasksOnDate(input);

        case "find":
            return parseFind(input);

        case "priority":
            return parsePriority(input);

        default:
            throw new UnhandledCommandException();
        }

    }

    private static Command parseMark(String input) {
        String numberPart = input.substring(5).trim();
        return new MarkCommand(numberPart);
    }

    private static Command parseUnmark(String input) {
        String numberPart = input.substring(7).trim();
        return new UnmarkCommand(numberPart);
    }

    private static Command parseDelete(String input) {
        String numberPart = input.substring(7).trim();
        return new DeleteCommand(numberPart);
    }

    private static Command parseFind(String input) {
        String searchString = input.substring(5).trim();
        return new FindCommand(searchString);
    }


    private static Command parseTasksOnDate(String input) throws MisoraException {
        try {
            String dateRaw = input.substring(5).trim();
            LocalDate date = LocalDate.parse(dateRaw);
            return new FindTaskOnDateCommand(date);
        } catch (DateTimeParseException e) {
            throw new MisoraException("Invalid date format. Use yyyy-MM-dd.");
        }
    }

    private static Command parseTodo(String input) throws MisoraException {
        return AddCommandFactory.createTodo(input);
    }

    private static Command parseDeadline(String input) throws MisoraException {
        return AddCommandFactory.createDeadline(input);
    }

    private static Command parseEvent(String input) throws MisoraException {
        return AddCommandFactory.createEvent(input);
    }

    private static Command parsePriority(String input) {
        String priorityString = input.substring(9).trim().toUpperCase();
        return new FindTaskOfPriorityCommand(priorityString);
    }
}
