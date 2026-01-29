import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class Misora {

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

    private static final File TASKFILE = new File("data/misora.txt");

    public static void main(String[] args) {

        if (!TASKFILE.exists()) {
            try {
                System.out.println("File does not exist\nCreating file");
                File parent = TASKFILE.getParentFile();
                if (parent != null) {
                    if (parent.mkdirs()) {
                        System.out.println("Folder created: " + TASKFILE.getParentFile());
                    }
                }

                if (TASKFILE.createNewFile()) {
                    System.out.println("File created: " + TASKFILE.getPath());
                }
            } catch (IOException e) {
                System.out.println("File was unable to be created");
            }
        }

        FileWriter taskWriter = null;
        Scanner taskScanner = null;
        try {
            taskScanner = new Scanner(TASKFILE);
            taskWriter = new FileWriter(TASKFILE, true);
        } catch (java.io.FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("data/misora.txt could not be found\nScanner could not be initiated");
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
            System.out.println("data/misora.txt could not be found\nWriter could not be initiated");
        }

        Scanner scanner = new Scanner(System.in);
        List<Task> listOfTasks = new ArrayList<>();

        try {
            if (taskScanner != null) {
                loadListFromFile(listOfTasks, taskScanner);
            } else {
                throw new IllegalArgumentException("taskScanner is null");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(LOGO);
        System.out.println(BREAKERLINE);
        System.out.println(GREETING);

        while (true) {

            System.out.println(BREAKERLINE);
            String inputLine = scanner.nextLine();

            if (inputLine.equalsIgnoreCase("bye")) {

                break;

            } else if (inputLine.equalsIgnoreCase("list")) {

                list(listOfTasks);

            } else if (inputLine.toLowerCase().startsWith("mark ")) {

                mark(listOfTasks, inputLine);
                updateFileFromList(listOfTasks);

            } else if (inputLine.toLowerCase().startsWith("unmark ")) {

                unmark(listOfTasks, inputLine);
                updateFileFromList(listOfTasks);

            } else if (inputLine.toLowerCase().startsWith("todo ")) {

                System.out.println(BREAKERLINE);
                ToDo.todo(listOfTasks, inputLine, taskWriter);

            } else if (inputLine.toLowerCase().startsWith("deadline ")) {

                System.out.println(BREAKERLINE);
                Deadline.deadline(listOfTasks, inputLine, taskWriter);

            } else if (inputLine.toLowerCase().startsWith("event ")) {

                System.out.println(BREAKERLINE);
                Event.event(listOfTasks, inputLine, taskWriter);

            } else if (inputLine.toLowerCase().startsWith("delete ")){

                delete(listOfTasks, inputLine);
                updateFileFromList(listOfTasks);

            } else {
                System.out.println(BREAKERLINE);
                System.out.println("Uh oh stinky, I was not programmed to handle wtv you just typed in.\n" +
                        "WHOOPSIE!! Maybe you can try again in the future when this is updated");
            }
        }

        scanner.close();
        System.out.println(BREAKERLINE);
        System.out.println(EXIT);
    }

    public static void list(List<Task> listOfTasks) {

        System.out.println(BREAKERLINE);
        for (int i = 0; i < listOfTasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, listOfTasks.get(i));
        }

    }

    public static void mark(List<Task> listOfTasks, String inputLine) {

        try {
            String numberPart = inputLine.substring(5).trim();
            int numberMarked = Integer.parseInt(numberPart);
            listOfTasks.get(numberMarked - 1).setTaskDone(true);
            System.out.println(BREAKERLINE);
            System.out.println("Nice! I've marked this task as done:");
            System.out.printf("  %s\n", listOfTasks.get(numberMarked - 1).toString());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number given");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Number is not within list size");
        }

    }

    public static void unmark(List<Task> listOfTasks, String inputLine) {
        try {
            String numberPart = inputLine.substring(7).trim();
            int numberUnmarked = Integer.parseInt(numberPart);
            listOfTasks.get(numberUnmarked - 1).setTaskDone(false);
            System.out.println(BREAKERLINE);
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.printf("  %s\n", listOfTasks.get(numberUnmarked - 1).toString());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number given");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Number is not within list size");
        }
    }

    public static void delete(List<Task> listOfTasks, String inputLine) {
        try {
            String numberPart = inputLine.substring(7).trim();
            int numberDeleted = Integer.parseInt(numberPart);
            Task removedTask = listOfTasks.remove(numberDeleted - 1);
            System.out.println(BREAKERLINE);
            System.out.println("Noted. I have removed this task:");
            System.out.printf("  %s\n", removedTask.toString());
            System.out.printf("Now you have %d tasks in the list.\n", listOfTasks.size());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number given");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Number is not within list size");
        }
    }

    public static void loadListFromFile(List<Task> listOfTasks, Scanner taskScanner) {
        try {
            while (taskScanner.hasNext()) {
                String task = taskScanner.nextLine();
                String[] parts = task.split("\\s*\\|\\s*");

                switch (parts[0]) {
                    case "T":
                        listOfTasks.add(new ToDo(parts[2], parts[1].equalsIgnoreCase("X")));
                        break;
                    case "D":
                        listOfTasks.add(new Deadline(parts[2], parts[3], parts[1].equalsIgnoreCase("X")));
                        break;
                    case "E":
                        listOfTasks.add(new Event(parts[2], parts[3], parts[4], parts[1].equalsIgnoreCase("X")));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown task type in saved list");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateFileFromList(List<Task> listOfTasks) {
        try {
            FileWriter writer = new FileWriter(TASKFILE); // overwrite
            for (Task task : listOfTasks) {
                writer.write(task.toSavedString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
