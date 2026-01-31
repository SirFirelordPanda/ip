package misora.tasks;

import misora.exceptions.MissingArgument1Exception;
import misora.exceptions.MissingArgument2Exception;
import misora.exceptions.MissingTaskMsgException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task{

    private Object fromWhen;
    private Object toWhen;

    public Event(String taskMsg, String fromWhenRaw, String toWhenRaw) {
        super(taskMsg);
        this.fromWhen = parseDateTime(fromWhenRaw);
        this.toWhen = parseDateTime(toWhenRaw);
    }

    public Event(String taskMsg, String fromWhenRaw, String toWhenRaw, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
        this.fromWhen = parseDateTime(fromWhenRaw);
        this.toWhen = parseDateTime(toWhenRaw);
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

    private String formatForSave(Object date) {
        if (date instanceof LocalDateTime dt) {
            return dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        }
        return date.toString();
    }

    @Override
    public String toString() {
        String eventTime = String.format(" (from: %s to: %s)", this.formatForDisplay(fromWhen), this.formatForDisplay(toWhen));
        return "[E]" + super.toString() + eventTime;
    }

    @Override
    public String toSavedString() {
        return String.format("E | %s | %s | %s", super.toSavedString(), this.formatForSave(fromWhen), this.formatForSave(toWhen));
    }

    @Override
    public Task isTaskOnDate(LocalDate date) {
        if (fromWhen instanceof LocalDate d) {
            if (date.equals(d)) {
                return this;
            }
        }

        if (toWhen instanceof LocalDate d) {
            if (date.equals(d)) {
                return this;
            }
        }

        if (fromWhen instanceof LocalDateTime dt) {
            if (date.equals(dt.toLocalDate())) {
                return this;
            }
        }

        if (toWhen instanceof LocalDateTime dt) {
            if (date.equals(dt.toLocalDate())) {
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
            throw new MissingTaskMsgException("WHOOPSIE!! Please enter the description of the event in this format\n" +
                    "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
        }
        if (fromWhen.toString().equalsIgnoreCase("")) {
            throw new MissingArgument1Exception("WHOOPSIE!! Please enter the start time of the event in this format\n" +
                    "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
        } else if (toWhen.toString().equalsIgnoreCase("")) {
            throw new MissingArgument2Exception("WHOOPSIE!! Please enter the end time of the event in this format\n" +
                    "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
        }
    }
}
