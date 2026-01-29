import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Exceptions.*;
import java.io.File;
import java.io.FileWriter;

public class Misora {

    static String logo =   "   _____  .___  _________________ __________    _____\n" +
            "  /     \\ |   |/   _____/\\_____  \\______    \\  /  _  \\\n" +
            " /  \\ /  \\|   |\\_____  \\  /   |   \\|       _/ /  /_\\  \\\n" +
            "/    Y    \\   |/        \\/    |    \\    |   \\/    |    \\\n" +
            "\\____|__  /___/_______  /\\_______  /____|_  /\\____|__  /\n" +
            "        \\/            \\/         \\/       \\/         \\/";

    static String breakerLine = "____________________________________________________________";

    static String greeting = "Nyahallo! Im MISORA.\nWhat can I do for you?";

    static String exit = "Bye bye. Hope to see you again soon!\n";

    public static void main(String[] args) {
        File taskFile = new File("../../../data/misora.txt");
        if (!taskFile.exists()) {
            try {
                System.out.println("File does not exist\nCreating file");
                File parent = taskFile.getParentFile();
                if (parent != null) {
                    if (parent.mkdirs()) {
                        System.out.println("Folder created: " + taskFile.getParentFile());
                    }
                }

                if (taskFile.createNewFile()) {
                    System.out.println("File created: " + taskFile.getPath());
                }
            } catch (IOException e) {
                System.out.println("File was unable to be created");
            }
        }

        FileWriter taskWriter = null;
        Scanner taskScanner = null;
        try {
            taskScanner = new Scanner(taskFile);
            taskWriter = new FileWriter(taskFile);
        } catch (java.io.FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("data/misora.txt could not be found\nScanner could not be initiated");
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
            System.out.println("data/misora.txt could not be found\nWriter could not be initiated");
        }

        Scanner scanner = new Scanner(System.in);
        List<Task> listOfTasks = new ArrayList<>();



        System.out.println(logo);
        System.out.println(breakerLine);
        System.out.println(greeting);

        while (true) {

            System.out.println(breakerLine);
            String inputLine = scanner.nextLine();

            if (inputLine.equalsIgnoreCase("bye")) {

                break;

            } else if (inputLine.equalsIgnoreCase("list")) {

                list(listOfTasks);

            } else if (inputLine.toLowerCase().startsWith("mark ")) {

                mark(listOfTasks, inputLine);

            } else if (inputLine.toLowerCase().startsWith("unmark ")) {

               unmark(listOfTasks, inputLine);

            } else if (inputLine.toLowerCase().startsWith("todo ")) {

                todo(listOfTasks, inputLine, taskWriter);

            } else if (inputLine.toLowerCase().startsWith("deadline ")) {

                deadline(listOfTasks, inputLine, taskWriter);

            } else if (inputLine.toLowerCase().startsWith("event ")) {

                event(listOfTasks, inputLine, taskWriter);

            } else if (inputLine.toLowerCase().startsWith("delete ")){

                delete(listOfTasks, inputLine);

            } else {
                System.out.println(breakerLine);
                System.out.println("Uh oh stinky, I was not programmed to handle wtv you just typed in.\n" +
                        "WHOOPSIE!! Maybe you can try again in the future when this is updated");
            }
        }

        scanner.close();
        System.out.println(breakerLine);
        System.out.println(exit);
    }

    public static void list(List<Task> listOfTasks) {

        System.out.println(breakerLine);
        for (int i = 0; i < listOfTasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, listOfTasks.get(i));
        }

    }

    public static void mark(List<Task> listOfTasks, String inputLine) {

        try {
            String numberPart = inputLine.substring(5).trim();
            int numberMarked = Integer.parseInt(numberPart);
            listOfTasks.get(numberMarked - 1).setTaskDone(true);
            System.out.println(breakerLine);
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
            System.out.println(breakerLine);
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.printf("  %s\n", listOfTasks.get(numberUnmarked - 1).toString());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number given");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Number is not within list size");
        }
    }

    public static void todo(List<Task> listOfTasks, String inputLine, FileWriter taskWriter){
        try {
            System.out.println(breakerLine);
            String taskMsg = inputLine.substring(5).trim();
            if (taskMsg.isEmpty()) {
                throw new MissingTaskMsgException();
            }

            ToDo todo = new ToDo(taskMsg);
            listOfTasks.add(todo);
            taskWriter.write(todo.toSavedString());
            System.out.println("Got it. I've added this task:");
            System.out.println(todo);
            System.out.printf("Now you have %d tasks in the list.\n",listOfTasks.size());

        } catch (MissingTaskMsgException e) {
            System.out.println("WHOOPSIE!! Please enter the description of the task in this format 'todo -taskMsg-'");
        } catch (java.io.IOException e) {
            System.out.println("Unable to write new todo to file");
        }
    }

    public static void deadline(List<Task> listOfTasks, String inputLine, FileWriter taskWriter) {
        try {
            System.out.println(breakerLine);
            String byWhen;
            String taskMsg;
            int byIndex = inputLine.indexOf("/by");
            if (byIndex != -1) {
                byWhen = inputLine.substring(byIndex + 3).trim();
                taskMsg = inputLine.substring(9, byIndex).trim();
                if (taskMsg.isEmpty()) {
                    throw new MissingTaskMsgException();
                } else if (byWhen.isEmpty()) {
                    throw new MissingArgument1Exception();
                }
            } else {
                throw new MissingArgument1Exception();
            }

            Deadline deadline = new Deadline(taskMsg, byWhen);
            listOfTasks.add(deadline);
            taskWriter.write(deadline.toSavedString());
            System.out.println("Got it. I've added this task:");
            System.out.println(deadline);
            System.out.printf("Now you have %d tasks in the list.\n",listOfTasks.size());

        } catch (MissingTaskMsgException e) {
            System.out.println("WHOOPSIE!! Please enter the description of the task in this format\n" +
                    "'deadline -taskMsg- /by -byWhen-'");
        } catch (MissingArgument1Exception e) {
            System.out.println("WHOOPSIE!! Please enter the deadline of the task in this format\n" +
                    "'deadline -taskMsg- /by -byWhen-'");
        }  catch (java.io.IOException e) {
            System.out.println("Unable to write new todo to file");
        }
    }

    public static void event(List<Task> listOfTasks, String inputLine, FileWriter taskWriter) {
        try {
            System.out.println(breakerLine);
            String fromWhen;
            String toWhen;
            String taskMsg;
            int fromIndex = inputLine.indexOf("/from");
            int toIndex = inputLine.indexOf("/to");
            if (fromIndex != -1 && toIndex != -1) {
                fromWhen = inputLine.substring(fromIndex + 5, toIndex).trim();
                toWhen = inputLine.substring(toIndex + 3).trim();
                taskMsg = inputLine.substring(6, fromIndex).trim();
                if (taskMsg.isEmpty()) {
                    throw new MissingTaskMsgException();
                } else if (fromWhen.isEmpty()) {
                    throw new MissingArgument1Exception();
                } else if (toWhen.isEmpty()) {
                    throw new MissingArgument2Exception();
                }
            } else if (fromIndex == -1) {
                throw new MissingArgument1Exception();
            } else {
                throw new MissingArgument2Exception();
            }

            Event event = new Event(taskMsg, fromWhen, toWhen);
            taskWriter.write(event.toSavedString());
            listOfTasks.add(event);
            System.out.println("Got it. I've added this task:");
            System.out.println(event);
            System.out.printf("Now you have %d tasks in the list.\n", listOfTasks.size());

        } catch (MissingTaskMsgException e) {
            System.out.println("WHOOPSIE!! Please enter the description of the event in this format\n" +
                    "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
        } catch (MissingArgument1Exception e) {
            System.out.println("WHOOPSIE!! Please enter the start time of the event in this format\n" +
                    "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
        } catch (MissingArgument2Exception e) {
            System.out.println("WHOOPSIE!! Please enter the end time of the event in this format\n" +
                    "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
        } catch (java.io.IOException e) {
            System.out.println("Unable to write new todo to file");
        }
    }

    public static void delete(List<Task> listOfTasks, String inputLine) {
        try {
            String numberPart = inputLine.substring(7).trim();
            int numberDeleted = Integer.parseInt(numberPart);
            Task removedTask = listOfTasks.remove(numberDeleted - 1);
            System.out.println(breakerLine);
            System.out.println("Noted. I have removed this task:");
            System.out.printf("  %s\n", removedTask.toString());
            System.out.printf("Now you have %d tasks in the list.\n", listOfTasks.size());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number given");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Number is not within list size");
        }
    }

}
