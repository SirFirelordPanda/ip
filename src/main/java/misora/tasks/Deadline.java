package misora.tasks;

import misora.exceptions.MissingArgument1Exception;
import misora.exceptions.MissingArgument2Exception;
import misora.exceptions.MissingTaskMsgException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task{

    public Object byWhen;

    public Deadline(String taskMsg, String byWhenRaw) {
        super(taskMsg);
        this.byWhen = parseDateTime(byWhenRaw);
    }

    public Deadline(String taskMsg, String byWhenRaw, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
        this.byWhen = parseDateTime(byWhenRaw);
    }

    private Object parseDateTime(String input) {
        try {
            return LocalDateTime.parse(input);
        } catch (DateTimeParseException e1) {
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e2) {
                return input;
            }
        }
    }

    private String formatForDisplay(Object date) {
        if (date instanceof LocalDateTime dt) {
            return dt.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss"));
        }
        if (date instanceof LocalDate d) {
            return d.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        }
        return date.toString();
    }

    @Override
    public String toString() {

        String deadline = String.format(" (by: %s)", this.formatForDisplay(byWhen));
        return "[D]" + super.toString() + deadline;
    }

    @Override
    public String toSavedString() {

        return String.format("D | %s | %s", super.toSavedString(), this.byWhen);
    }

    @Override
    public Task isTaskOnDate(LocalDate date) {
        if (byWhen instanceof LocalDate) {
            if (date.equals(byWhen)) {
                return this;
            }

        } else if (byWhen instanceof LocalDateTime dateTime) {
            if (date.equals(dateTime.toLocalDate())) {
                return this;
            }

        }
        return null;
    }

    @Override
    public void isValidFormat() throws MissingTaskMsgException, MissingArgument1Exception, MissingArgument2Exception {
        try {
            super.isValidFormat();
        } catch (MissingTaskMsgException e) {
            throw new MissingTaskMsgException("WHOOPSIE!! Please enter the description of the task in this format\n" +
                    "'deadline -taskMsg- /by -byWhen-'");
        }
        if (byWhen.toString().equalsIgnoreCase("")) {
            throw new MissingArgument1Exception("WHOOPSIE!! Please enter the deadline of the task in this format\n" +
                    "'deadline -taskMsg- /by -byWhen-'");
        }
    }
}
