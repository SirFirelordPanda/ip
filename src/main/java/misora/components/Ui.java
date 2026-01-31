package misora.components;

import misora.tasks.Task;

import java.time.LocalDate;
import java.util.Scanner;

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

    Scanner userScanner;

    public Ui() {
        this.userScanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(LOGO);
        this.showLine();
        System.out.println(GREETING);
        this.showLine();
    }

    public void showLine() {
        System.out.println(BREAKERLINE);
    }

    public void showExit() {
        System.out.println(EXIT);
    }

    public String readCommand() {
        return this.userScanner.nextLine();
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void showTasklist(String task) {
        System.out.println(task);
    }

    public void showListClear() {
        System.out.println("List has been cleared");
    }

    public void showLoadingError() {
        System.out.println("misora.Misora initialisation failed. Task save file is corrupted");
    }

    public void showMarkTask(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.printf("  %s\n", task.toString());
    }

    public void showUnmarkTask(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.printf("  %s\n", task.toString());
    }

    public void showDeleteTask(Task task, TaskList taskList) {
        System.out.println("Noted. I have removed this task:");
        System.out.printf("  %s\n", task.toString());
        System.out.printf("Now you have %d tasks in the list.\n", taskList.size());
    }

    public void showAddTask(Task task, TaskList taskList) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.printf("Now you have %d tasks in the list.\n",taskList.size());
    }

    public void showDate(LocalDate date) {
        System.out.println(date);
    }

    public void exit() {
        userScanner.close();
    }
}
