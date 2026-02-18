package misora.commands;

import misora.exceptions.InvalidPriorityException;
import misora.tasks.Priority;

/**
 * Factory class responsible for creating {@link AddCommand} objects
 * for different task types (Todo, Deadline, Event).
 *
 * <p>This class centralises the parsing logic for extracting task details
 * and optional priority flags from the user input string.
 *
 * <p>Supported flags:
 * <ul>
 *     <li><code>/priority</code> — specifies task priority (LOW, MEDIUM, HIGH)</li>
 *     <li><code>/by</code> — specifies deadline timing</li>
 *     <li><code>/from</code> — specifies event start time</li>
 *     <li><code>/to</code> — specifies event end time</li>
 * </ul>
 *
 * <p>If no priority is specified, {@link Priority#MEDIUM} is used by default.
 */
public class AddCommandFactory {

    /**
     * Extracts the priority from the input string if the <code>/priority</code> flag exists.
     *
     * <p>If no priority flag is found, the default priority {@link Priority#MEDIUM}
     * is returned.
     *
     * <p>The method mutates the provided {@link StringBuilder} by removing the
     * <code>/priority</code> section from it.
     *
     * @param input The mutable input containing task details and optional flags.
     * @return The extracted {@link Priority}.
     * @throws InvalidPriorityException If the priority value provided is not
     *                                  LOW, MEDIUM, or HIGH.
     */
    private static Priority extractPriority(StringBuilder input) throws InvalidPriorityException {
        Priority priority = Priority.MEDIUM;

        int priorityIndex = input.indexOf("/priority");
        if (priorityIndex != -1) {
            String priorityPart = input.substring(priorityIndex + 9).trim();
            input.delete(priorityIndex, input.length()); // remove /priority section

            try {
                priority = Priority.valueOf(priorityPart.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidPriorityException(
                        "Invalid priority: "
                                + priorityPart
                                + ". Valid priorities are LOW, MEDIUM, HIGH."
                );
            }

        }

        return priority;
    }


    /**
     * Creates an {@link AddCommand} for a Todo task.
     *
     * <p>Expected format:
     * <pre>
     * todo &lt;task description&gt; [/priority PRIORITY]
     * </pre>
     *
     * @param fullCommand The full user input command.
     * @return An {@link AddCommand} configured for a Todo task.
     * @throws InvalidPriorityException If the specified priority is invalid.
     */
    public static AddCommand createTodo(String fullCommand) throws InvalidPriorityException {
        StringBuilder remaining = new StringBuilder(fullCommand.substring(5).trim());

        Priority priority = extractPriority(remaining);

        String taskMsg = remaining.toString().trim();

        return new AddCommand(taskMsg, priority);
    }

    /**
     * Creates an {@link AddCommand} for a Deadline task.
     *
     * <p>Expected format:
     * <pre>
     * deadline &lt;task description&gt; /by &lt;date/time&gt; [/priority PRIORITY]
     * </pre>
     *
     * @param fullCommand The full user input command.
     * @return An {@link AddCommand} configured for a Deadline task.
     * @throws InvalidPriorityException If the specified priority is invalid.
     */
    public static AddCommand createDeadline(String fullCommand) throws InvalidPriorityException {
        StringBuilder remaining = new StringBuilder(fullCommand.substring(9).trim());

        Priority priority = extractPriority(remaining);

        String taskMsg = "";
        String byWhen = "";

        int byIndex = remaining.indexOf("/by");

        if (byIndex != -1) {
            taskMsg = remaining.substring(0, byIndex).trim();
            byWhen = remaining.substring(byIndex + 3).trim();
        } else {
            taskMsg = remaining.toString().trim();
        }

        return new AddCommand(taskMsg, byWhen, priority);
    }

    /**
     * Creates an {@link AddCommand} for an Event task.
     *
     * <p>Expected format:
     * <pre>
     * event &lt;task description&gt; /from &lt;start&gt; /to &lt;end&gt; [/priority PRIORITY]
     * </pre>
     *
     * @param fullCommand The full user input command.
     * @return An {@link AddCommand} configured for an Event task.
     * @throws InvalidPriorityException If the specified priority is invalid.
     */
    public static AddCommand createEvent(String fullCommand) throws InvalidPriorityException {
        StringBuilder remaining = new StringBuilder(fullCommand.substring(6).trim());

        Priority priority = extractPriority(remaining);

        String taskMsg = "";
        String fromWhen = "";
        String toWhen = "";

        int fromIndex = remaining.indexOf("/from");
        int toIndex = remaining.indexOf("/to");

        if (fromIndex != -1 && toIndex != -1) {
            taskMsg = remaining.substring(0, fromIndex).trim();
            fromWhen = remaining.substring(fromIndex + 5, toIndex).trim();
            toWhen = remaining.substring(toIndex + 3).trim();
        } else if (fromIndex != -1) {
            taskMsg = remaining.substring(0, fromIndex).trim();
            fromWhen = remaining.substring(fromIndex + 5).trim();
        } else if (toIndex != -1) {
            taskMsg = remaining.substring(0, toIndex).trim();
            toWhen = remaining.substring(toIndex + 3).trim();
        } else {
            taskMsg = remaining.toString().trim();
        }

        return new AddCommand(taskMsg, fromWhen, toWhen, priority);
    }
}

