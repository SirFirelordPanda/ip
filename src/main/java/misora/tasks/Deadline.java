package misora.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import misora.exceptions.MissingArgument1Exception;
import misora.exceptions.MissingArgument2Exception;
import misora.exceptions.MissingTaskMsgException;

/**
 * Represents a task with a specific deadline in the Misora application.
 * <p>
 * A {@code Deadline} has a description (inherited from {@link Task})
 * and a due date or time ({@code byWhen}). The due date can be represented
 * as a {@link LocalDateTime}, {@link LocalDate}, or a raw string if parsing fails.
 * <p>
 * This class overrides {@link Task#toString()}, {@link Task#toSavedString()},
 * {@link Task#isTaskOnDate(LocalDate)}, and {@link Task#isValidFormat()} to
 * provide deadline-specific behavior.
 */
public class Deadline extends Task {

    private final Object byWhen;

    /**
     * Creates a new {@code Deadline} task with a description and a raw deadline string.
     *
     * @param taskMsg The description of the task
     * @param byWhenRaw The deadline of the task (can be parsed as {@link LocalDateTime} or {@link LocalDate})
     */
    public Deadline(String taskMsg, String byWhenRaw) {
        super(taskMsg);
        assert byWhenRaw != null : "byWhenRaw cannot be null";
        this.byWhen = parseDateTime(byWhenRaw);
        assert byWhen != null : "byWhen must not be null after parsing";
    }

    /**
     * Creates a new {@code Deadline} task with a description, a raw deadline string,
     * and a flag indicating whether the task is already marked as done.
     *
     * @param taskMsg The description of the task
     * @param byWhenRaw The deadline of the task
     * @param isTaskDone {@code true} if the task is completed, {@code false} otherwise
     */
    public Deadline(String taskMsg, String byWhenRaw, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
        assert byWhenRaw != null : "byWhenRaw cannot be null";
        this.byWhen = parseDateTime(byWhenRaw);
        assert byWhen != null : "byWhen must not be null after parsing";
    }

    /**
     * Attempts to parse a raw string into a {@link LocalDateTime} or {@link LocalDate}.
     * If parsing fails, the raw string is returned.
     *
     * @param input The raw deadline input
     * @return A {@link LocalDateTime}, {@link LocalDate}, or the raw string if parsing fails
     */
    private Object parseDateTime(String input) {
        assert input != null : "input string for parseDateTime cannot be null";

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
     * Formats the deadline for display to the user.
     *
     * @param date The deadline object to format
     * @return A human-readable string representation of the deadline
     */
    private String formatForDisplay(Object date) {
        assert date != null : "date cannot be null in formatForDisplay";

        if (date instanceof LocalDateTime dt) {
            return dt.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss"));
        }
        if (date instanceof LocalDate d) {
            return d.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        }
        return date.toString();
    }

    /**
     * Formats the deadline for saving to the storage file.
     *
     * @param date The deadline object to format
     * @return A string suitable for file storage
     */
    private String formatForSave(Object date) {
        assert date != null : "date cannot be null in formatForSave";

        if (date instanceof LocalDateTime dt) {
            return dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        }
        return date.toString();
    }

    /**
     * Returns a string representation of the deadline task for display purposes.
     *
     * @return The formatted string representation of this task
     */
    @Override
    public String toString() {
        assert super.toString() != null : "super.toString() cannot be null";
        String deadline = String.format(" (by: %s)", this.formatForDisplay(byWhen));
        return "[D]" + super.toString() + deadline;
    }

    /**
     * Returns a string representation of the task suitable for saving to a file.
     *
     * @return The formatted string for storage
     */
    @Override
    public String toSavedString() {
        assert super.toSavedString() != null : "super.toSavedString() cannot be null";
        return String.format("D | %s | %s", super.toSavedString(), this.formatForSave(byWhen));
    }

    /**
     * Checks if this task occurs on the specified {@link LocalDate}.
     *
     * @param date The date to check
     * @return This {@link Task} if it occurs on the given date, otherwise {@code null}
     */
    @Override
    public Task isTaskOnDate(LocalDate date) {
        assert date != null : "date argument in isTaskOnDate cannot be null";

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

    /**
     * Checks whether this task contains the given search string.
     * <p>
     * A task is considered a match if the search string appears in:
     * <ul>
     *     <li>the task description (inherited behaviour)</li>
     *     <li>the {@code byWhen} date/time in its raw, display, or save format </li>
     * </ul>
     *
     * @param searchString The string to search for within this task.
     * @return This task if the search string matches any relevant field;
     *         {@code null} otherwise.
     */
    @Override
    public Task doesTaskContainString(String searchString) {
        assert searchString != null : "searchString cannot be null";

        Task itDoes = super.doesTaskContainString(searchString);
        if (itDoes != null) {
            return this;
        } else if (byWhen.toString().contains(searchString)
                || formatForDisplay(byWhen).contains(searchString)
                || formatForSave(byWhen).contains(searchString)) {
            return this;
        }
        return null;
    }

    /**
     * Validates the format of the deadline task.
     * <p>
     * Throws exceptions if the description or deadline is missing.
     *
     * @throws MissingTaskMsgException if the task description is empty
     * @throws MissingArgument1Exception if the deadline argument is missing
     * @throws MissingArgument2Exception Not used in this class, but part of signature
     */
    @Override
    public void isValidFormat() throws MissingTaskMsgException, MissingArgument1Exception, MissingArgument2Exception {
        try {
            super.isValidFormat();
        } catch (MissingTaskMsgException e) {
            throw new MissingTaskMsgException("WHOOPSIE!! Please enter the description of the task in this format\n"
                    + "'deadline -taskMsg- /by -byWhen-'");
        }
        if (byWhen.toString().equalsIgnoreCase("")) {
            throw new MissingArgument1Exception("WHOOPSIE!! Please enter the deadline of the task in this format\n"
                    + "'deadline -taskMsg- /by -byWhen-'");
        }
    }
}
