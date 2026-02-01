package misora.components;

import misora.commands.*;
import misora.exceptions.MisoraException;
import misora.exceptions.UnhandledCommandException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

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

        } else if (fullCommand.toLowerCase().startsWith("delete ")){

            String numberPart = fullCommand.substring(7).trim();
            return new DeleteCommand(numberPart);

        } else if (fullCommand.toLowerCase().startsWith("tasks on ")){

            try {
                String dateRaw = fullCommand.substring(9).trim();
                LocalDate date = LocalDate.parse(dateRaw);
                return new FindTaskOnDateCommand(date);
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }

        } else if (fullCommand.toLowerCase().startsWith("find ")){

            String searchString = fullCommand.substring(5).trim();
            return new FindCommand(searchString);

        }
        throw new UnhandledCommandException();
    }
}
