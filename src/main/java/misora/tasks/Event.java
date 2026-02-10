package misora.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import misora.exceptions.MissingArgument1Exception;
import misora.exceptions.MissingArgument2Exception;
import misora.exceptions.MissingTaskMsgException;

/**
 * Represents an event task in the Misora application with a start and end time.
 * <p>
 * An {@code Event} has a description (inherited from {@link Task}), a start time
 * ({@code fromWhen}), and an end time ({@code toWhen}). The times can be stored
 * as {@link LocalDateTime}, {@link LocalDate}, or as raw strings if parsing fails.
 * <p>
 * This class overrides {@link Task#toString()}, {@link Task#toSavedString()},
 * {@link Task#isTaskOnDate(LocalDate)}, and {@link Task#isValidFormat()} to provide
 * event-specific behavior.
 */
public class Event extends Task {

    /**
     * The start time of the event.
     */
    private final Object fromWhen;

    /**
     * The end time of the event.
     */
    private final Object toWhen;

    /**
     * Creates a new {@code Event} task with a description, start time, and end time.
     *
     * @param taskMsg The description of the event
     * @param fromWhenRaw The start time of the event (can be parsed as {@link LocalDateTime} or {@link LocalDate})
     * @param toWhenRaw The end time of the event (can be parsed as {@link LocalDateTime} or {@link LocalDate})
     */
    public Event(String taskMsg, String fromWhenRaw, String toWhenRaw) {
        super(taskMsg);
        assert fromWhenRaw != null : "fromWhenRaw cannot be null";
        assert toWhenRaw != null : "toWhenRaw cannot be null";

        this.fromWhen = parseDateTime(fromWhenRaw);
        this.toWhen = parseDateTime(toWhenRaw);

        assert fromWhen != null : "fromWhen must not be null after parsing";
        assert toWhen != null : "toWhen must not be null after parsing";
    }

    /**
     * Creates a new {@code Event} task with a description, start time, end time,
     * and completion status.
     *
     * @param taskMsg The description of the event
     * @param fromWhenRaw The start time of the event
     * @param toWhenRaw The end time of the event
     * @param isTaskDone {@code true} if the task is completed, {@code false} otherwise
     */
    public Event(String taskMsg, String fromWhenRaw, String toWhenRaw, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
        assert fromWhenRaw != null : "fromWhenRaw cannot be null";
        assert toWhenRaw != null : "toWhenRaw cannot be null";

        this.fromWhen = parseDateTime(fromWhenRaw);
        this.toWhen = parseDateTime(toWhenRaw);

        assert fromWhen != null : "fromWhen must not be null after parsing";
        assert toWhen != null : "toWhen must not be null after parsing";
    }

    /**
     * Attempts to parse a raw string into a {@link LocalDateTime} or {@link LocalDate}.
     * If parsing fails, the raw string is returned.
     *
     * @param input The raw date/time input
     * @return A {@link LocalDateTime}, {@link LocalDate}, or raw string if parsing fails
     */
    private Object parseDateTime(String input) {
        assert input != null : "input cannot be null in parseDateTime";
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

    /**
     * Returns a string representation of the event task for display.
     *
     * @return The formatted string representing this event
     */
    @Override
    public String toString() {
        String eventTime = String.format(" (from: %s to: %s)",
                this.formatDateForDisplay(fromWhen), this.formatDateForDisplay(toWhen));
        return "[E]" + super.toString() + eventTime;
    }

    /**
     * Returns a string representation of the event suitable for saving to a file.
     *
     * @return The formatted string for storage
     */
    @Override
    public String toSavedString() {
        return String.format("E | %s | %s | %s", super.toSavedString(),
                this.formatDateForSave(fromWhen), this.formatDateForSave(toWhen));
    }

    /**
     * Checks if the event occurs on the specified {@link LocalDate}.
     *
     * @param date The date to check
     * @return This {@link Task} if the event occurs on the given date, otherwise {@code null}
     */
    @Override
    public Task isTaskOnDate(LocalDate date) {
        assert date != null : "date cannot be null in isTaskOnDate";

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

    /**
     * Checks whether this task contains the given search string.
     * <p>
     * A task is considered a match if the search string appears in:
     * <ul>
     *     <li>the task description (inherited behaviour),</li>
     *     <li>the {@code toWhen} date/time in its raw, display, or save format, or</li>
     *     <li>the {@code fromWhen} date/time in its raw, display, or save format.</li>
     * </ul>
     *
     * @param searchString The string to search for within this task.
     * @return This task if the search string matches any relevant field;
     *         {@code null} otherwise.
     */
    @Override
    public Task doesTaskContainString(String searchString) {
        assert searchString != null : "searchString cannot be null in doesTaskContainString";

        Task itDoes = super.doesTaskContainString(searchString);
        if (itDoes != null) {
            return this;
        } else if (toWhen.toString().contains(searchString)
                || formatDateForDisplay(toWhen).contains(searchString)
                || formatDateForSave(toWhen).contains(searchString)) {
            return this;
        } else if (fromWhen.toString().contains(searchString)
                || formatDateForDisplay(fromWhen).contains(searchString)
                || formatDateForSave(fromWhen).contains(searchString)) {
            return this;
        }
        return null;
    }

    /**
     * Validates the format of the event task.
     * <p>
     * Throws exceptions if the task description, start time, or end time is missing.
     *
     * @throws MissingTaskMsgException if the task description is empty
     * @throws MissingArgument1Exception if the start time is missing
     * @throws MissingArgument2Exception if the end time is missing
     */
    @Override
    public void isValidFormat() throws MissingTaskMsgException, MissingArgument1Exception, MissingArgument2Exception {
        try {
            super.isValidFormat();
        } catch (MissingTaskMsgException e) {
            throw new MissingTaskMsgException("WHOOPSIE!! Please enter the description of the event in this format\n"
                    + "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
        }
        if (fromWhen.toString().equalsIgnoreCase("")) {
            throw new MissingArgument1Exception("WHOOPSIE!! Please enter the start time of the event in this format\n"
                    + "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
        } else if (toWhen.toString().equalsIgnoreCase("")) {
            throw new MissingArgument2Exception("WHOOPSIE!! Please enter the end time of the event in this format\n"
                    + "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
        }
    }
}
