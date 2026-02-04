package misora.components;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import misora.commands.AddCommand;
import misora.commands.Command;
import misora.commands.DeleteCommand;
import misora.commands.ExitCommand;
import misora.commands.FindCommand;
import misora.commands.FindTaskOnDateCommand;
import misora.commands.ListClearCommand;
import misora.commands.ListCommand;
import misora.commands.MarkCommand;
import misora.commands.UnmarkCommand;
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
    public static Command parse(String fullCommand) throws MisoraException {
        if (fullCommand.equalsIgnoreCase("bye")) {

            return new ExitCommand();

        } else if (fullCommand.equalsIgnoreCase("list")) {

            return new ListCommand();

        } else if (fullCommand.equalsIgnoreCase("list clear")) {

            return new ListClearCommand();

        } else if (fullCommand.toLowerCase().startsWith("mark ")) {

            String numberPart = fullCommand.substring(5).trim();
            return new MarkCommand(numberPart);

        } else if (fullCommand.toLowerCase().startsWith("unmark ")) {

            String numberPart = fullCommand.substring(7).trim();
            return new UnmarkCommand(numberPart);

        } else if (fullCommand.toLowerCase().startsWith("todo ")) {

            String taskMsg = fullCommand.substring(5).trim();
            return new AddCommand(taskMsg);

        } else if (fullCommand.toLowerCase().startsWith("deadline ")) {

            String byWhen = "";
            String taskMsg = "";
            int byIndex = fullCommand.indexOf("/by");
            if (byIndex != -1) {
                byWhen = fullCommand.substring(byIndex + 3).trim();
                taskMsg = fullCommand.substring(9, byIndex).trim();
            } else {
                taskMsg = fullCommand.substring(9).trim();
            }
            return new AddCommand(taskMsg, byWhen);

        } else if (fullCommand.toLowerCase().startsWith("event ")) {

            String fromWhen = "";
            String toWhen = "";
            String taskMsg = "";
            int fromIndex = fullCommand.indexOf("/from");
            int toIndex = fullCommand.indexOf("/to");
            if (fromIndex != -1 && toIndex != -1) {
                fromWhen = fullCommand.substring(fromIndex + 5, toIndex).trim();
                toWhen = fullCommand.substring(toIndex + 3).trim();
                taskMsg = fullCommand.substring(6, fromIndex).trim();
            } else if (fromIndex != -1) {
                fromWhen = fullCommand.substring(fromIndex + 5).trim();
                taskMsg = fullCommand.substring(6, fromIndex).trim();
            } else if (toIndex != -1) {
                toWhen = fullCommand.substring(toIndex + 5).trim();
                taskMsg = fullCommand.substring(6, toIndex).trim();
            } else {
                taskMsg = fullCommand.substring(9).trim();
            }
            return new AddCommand(taskMsg, fromWhen, toWhen);

        } else if (fullCommand.toLowerCase().startsWith("delete ")) {

            String numberPart = fullCommand.substring(7).trim();
            return new DeleteCommand(numberPart);

        } else if (fullCommand.toLowerCase().startsWith("tasks on ")) {

            try {
                String dateRaw = fullCommand.substring(9).trim();
                LocalDate date = LocalDate.parse(dateRaw);
                return new FindTaskOnDateCommand(date);
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }

        } else if (fullCommand.toLowerCase().startsWith("find ")) {

            String searchString = fullCommand.substring(5).trim();
            return new FindCommand(searchString);

        }
        throw new UnhandledCommandException();
    }
}
