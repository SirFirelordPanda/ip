package misora.components;

import misora.tasks.Task;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Represents a ui that handles all user interactions in the Misora application.
 * <p>
 * The {@code Ui} class is responsible for displaying messages to the user,
 * reading input from the user, and formatting output related to tasks
 * and application state.
 */
public class Ui {

    private static final String LOGO = """
               _____  .___  _________________ __________    _____
              /     \\ |   |/   _____/\\_____  \\______    \\  /  _  \\
             /  \\ /  \\|   |\\_____  \\  /   |   \\|       _/ /  /_\\  \\
            /    Y    \\   |/        \\/    |    \\    |   \\/    |    \\
            \\____|__  /___/_______  /\\_______  /____|_  /\\____|__  /
                    \\/            \\/         \\/       \\/         \\/""";

    private static final String BREAKERLINE = "____________________________________________________________";

    private static final String GREETING = "Nyahallo! Im MISORA.\nWhat can I do for you?";

    private static final String EXIT = "Bye bye. Hope to see you again soon!\n";

    /**
     * The {@link Scanner} used to read user input to be passed to the {@link Parser}.
     */
    Scanner userScanner;

    /**
     * Constructs a new {@code Ui} object and initializes a {@link Scanner}
     * for reading user input.
     */
    public Ui() {
        this.userScanner = new Scanner(System.in);
    }

    /**
     * Reads a line of user input from the console.
     *
     * @return The raw input string entered by the user
     */
    public String readCommand() {
        return this.userScanner.nextLine();
    }

    /**
     * Displays the welcome message and logo when the application starts.
     */
    public void showWelcome() {
        System.out.println(LOGO);
        this.showLine();
        System.out.println(GREETING);
        this.showLine();
    }

    /**
     * Displays a horizontal breaker line to separate sections in the console.
     */
    public void showLine() {
        System.out.println(BREAKERLINE);
    }

    /**
     * Displays the exit message when the application terminates.
     */
    public void showExit() {
        System.out.println(EXIT);
    }

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The message to display
     */
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Displays a single task in the task list format.
     *
     * @param task The task to display
     */
    public void showTasklist(String task) {
        System.out.println(task);
    }

    /**
     * Displays a message indicating that the task list has been cleared.
     */
    public void showListClear() {
        System.out.println("List has been cleared");
    }

    /**
     * Displays a message indicating that the task save file is corrupted.
     */
    public void showLoadingError() {
        System.out.println("misora.Misora initialisation failed. Task save file is corrupted");
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task The {@link Task} that was marked
     */
    public void showMarkTask(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.printf("  %s\n", task.toString());
    }

    /**
     * Displays a message indicating that a task has been unmarked (not done).
     *
     * @param task The {@link Task} that was unmarked
     */
    public void showUnmarkTask(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.printf("  %s\n", task.toString());
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param task The {@link Task} that was deleted
     * @param taskList The {@link TaskList} from which the task was removed
     */
    public void showDeleteTask(Task task, TaskList taskList) {
        System.out.println("Noted. I have removed this task:");
        System.out.printf("  %s\n", task.toString());
        System.out.printf("Now you have %d tasks in the list.\n", taskList.size());
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param task The {@link Task} that was added
     * @param taskList The {@link TaskList} to which the task was added
     */
    public void showAddTask(Task task, TaskList taskList) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.printf("Now you have %d tasks in the list.\n",taskList.size());
    }

    /**
     * Displays a {@link LocalDate} to the user.
     *
     * @param date The date to display
     */
    public void showDate(LocalDate date) {
        System.out.println(date);
    }

    /**
     * Closes the {@link Scanner} used for reading user input.
     */
    public void exit() {
        userScanner.close();
    }
}
