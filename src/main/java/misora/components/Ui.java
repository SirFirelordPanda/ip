package misora.components;

import java.time.LocalDate;
import java.util.List;
import misora.tasks.Task;

/**
 * Ui handles the textual interface of the MISORA application.
 * <p>
 * It is responsible for generating user-facing messages for various actions,
 * such as welcoming the user, showing task lists, errors, and confirmations
 * for adding, deleting, or updating tasks.
 * </p>
 */
public class Ui {

    private static final String GREETING =
            "Nyahallo! I'm MISORA.\nWhat can I do for you?\n";

    private static final String EXIT =
            "Bye bye. Hope to see you again soon!\n";

    /**
     * Returns the welcome message when the application starts.
     *
     * @return the welcome message string
     */
    public String showWelcome() {
        return GREETING ;
    }

    /**
     * Returns the exit message when the application terminates.
     *
     * @return the exit message string
     */
    public String showExit() {
        return EXIT;
    }

    /**
     * Returns a formatted error message to display to the user.
     *
     * @param errorMessage the error message to show
     * @return the formatted error message
     */
    public String showError(String errorMessage) {
        return errorMessage + "\n";
    }

    /**
     * Returns a formatted list of tasks.
     *
     * @param tasks the list of tasks to display
     * @return a string containing the numbered list of tasks, or a message if empty
     */
    public String showList(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();

        if (tasks.isEmpty()) {
            sb.append("No tasks found\n");
        } else {
            sb.append("Tasks:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(String.format("%d. ", i + 1)).append(tasks.get(i)).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Returns a message indicating that the task list has been cleared.
     *
     * @return the clear list message
     */
    public String showListClear() {
        return "List has been cleared\n";
    }

    /**
     * Returns a message indicating that the application failed to load
     * the task save file.
     *
     * @return the loading error message
     */
    public String showLoadingError() {
        return "Misora initialisation failed. Task save file is corrupted\n";
    }

    /**
     * Returns a message indicating that a task has been marked as done.
     *
     * @param task the task that was marked
     * @return the formatted message confirming the task is done
     */
    public String showMarkTask(Task task) {
        return "Nice! I've marked this task as done:\n"
                + "  " + task + "\n";
    }

    /**
     * Returns a message indicating that a task has been unmarked (not done).
     *
     * @param task the task that was unmarked
     * @return the formatted message confirming the task is not done
     */
    public String showUnmarkTask(Task task) {
        return "OK, I've marked this task as not done yet:\n"
                + "  " + task + "\n";
    }

    /**
     * Returns a message indicating that a task has been deleted from the list.
     *
     * @param task the task that was deleted
     * @param tasks the remaining task list
     * @return the formatted message confirming the deletion and task count
     */
    public String showDeleteTask(Task task, TaskList tasks) {
        return "Noted. I have removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.\n";
    }

    /**
     * Returns a message indicating that a task has been added to the list.
     *
     * @param task the task that was added
     * @param tasks the updated task list
     * @return the formatted message confirming the addition and task count
     */
    public String showAddTask(Task task, TaskList tasks) {
        return "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.\n";
    }

    /**
     * Returns a formatted list of tasks scheduled for a specific date.
     *
     * @param tasks the list of tasks on the date
     * @param date the date for which tasks are displayed
     * @return a string listing tasks on the date, or a message if none exist
     */
    public String showTasksOnDate(List<Task> tasks, LocalDate date) {
        StringBuilder sb = new StringBuilder();

        if (tasks.isEmpty()) {
            sb.append("No tasks found on ").append(date).append("\n");
        } else {
            sb.append("Tasks on ").append(date).append(":\n");
            for (Task t : tasks) {
                sb.append("  ").append(t).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Returns a formatted list of tasks that contain a specific search string.
     *
     * @param tasks the list of tasks containing the string
     * @param string the string to search for
     * @return a string listing tasks that contain the search string, or a message if none exist
     */
    public String showTasksContainingString(List<Task> tasks, String string) {
        StringBuilder sb = new StringBuilder();

        if (tasks.isEmpty()) {
            sb.append("No tasks found containing: ").append(string).append("\n");
        } else {
            sb.append("Tasks containing \"").append(string).append("\":\n");
            for (Task t : tasks) {
                sb.append("  ").append(t).append("\n");
            }
        }

        return sb.toString();
    }
}
