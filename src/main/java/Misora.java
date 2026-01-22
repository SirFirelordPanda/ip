import java.util.Scanner;
import Exceptions.*;

public class Misora {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] listOfTasks = new Task[100];
        int listLength = 0;

        String logo =   "   _____  .___  _________________ __________    _____\n" +
                        "  /     \\ |   |/   _____/\\_____  \\______    \\  /  _  \\\n" +
                        " /  \\ /  \\|   |\\_____  \\  /   |   \\|       _/ /  /_\\  \\\n" +
                        "/    Y    \\   |/        \\/    |    \\    |   \\/    |    \\\n" +
                        "\\____|__  /___/_______  /\\_______  /____|_  /\\____|__  /\n" +
                        "        \\/            \\/         \\/       \\/         \\/";

        String breakerLine = "____________________________________________________________";

        String greeting = "Nyahallo! Im MISORA.\nWhat can I do for you?";

        String exit = "Bye bye. Hope to see you again soon!\n";

        System.out.println(logo);
        System.out.println(breakerLine);
        System.out.println(greeting);

        while (true) {

            System.out.println(breakerLine);
            String inputLine = scanner.nextLine();

            if (inputLine.equalsIgnoreCase("bye")) {
                break;
            } else if (inputLine.equalsIgnoreCase("list")) {
                System.out.println(breakerLine);
                for (int i = 1; i < listLength + 1; i ++) {
                    System.out.printf("%d.%s\n", i, listOfTasks[i - 1].toString());
                }

            } else if (inputLine.toLowerCase().startsWith("mark ")) {

                try {
                    String numberPart = inputLine.substring(5).trim();
                    int numberMarked = Integer.parseInt(numberPart);
                    listOfTasks[numberMarked - 1].setTaskDone(true);
                    System.out.println(breakerLine);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.printf("  %s\n", listOfTasks[numberMarked - 1].toString());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number given");
                } catch (NullPointerException e) {
                    System.out.println("Number given is too big");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Number given is too small");
                }

            } else if (inputLine.toLowerCase().startsWith("unmark ")) {

                try {
                    String numberPart = inputLine.substring(7).trim();
                    int numberUnmarked = Integer.parseInt(numberPart);
                    listOfTasks[numberUnmarked - 1].setTaskDone(false);
                    System.out.println(breakerLine);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.printf("  %s\n", listOfTasks[numberUnmarked - 1].toString());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number given");
                } catch (NullPointerException e) {
                    System.out.println("Number given is too big");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Number given is too small");
                }

            } else if (inputLine.toLowerCase().startsWith("todo ")) {
                try {
                    System.out.println(breakerLine);
                    String taskMsg = inputLine.substring(5).trim();
                    if (taskMsg.isEmpty()) {
                        throw new MissingTaskMsgException();
                    }
                    if (listLength < 100) {
                        ToDo todo = new ToDo(taskMsg);
                        listOfTasks[listLength] = todo;
                        listLength++;
                        System.out.println("Got it. I've added this task:");
                        System.out.println(todo.toString());
                        System.out.printf("Now you have %d tasks in the list.\n", listLength);
                    } else {
                        throw new TooManyTasksException();
                    }
                } catch (TooManyTasksException e) {
                    System.out.println("WHOOPSIE!! You have too many tasks in the task list");
                } catch (MissingTaskMsgException e) {
                    System.out.println("WHOOPSIE!! Please enter the description of the task in this format 'todo -taskMsg-'");
                }

            } else if (inputLine.toLowerCase().startsWith("deadline ")) {

                try {
                    System.out.println(breakerLine);
                    String byWhen = "";
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
                    if (listLength < 100) {
                        Deadline deadline = new Deadline(taskMsg, byWhen);
                        listOfTasks[listLength] = deadline;
                        listLength++;
                        System.out.println("Got it. I've added this task:");
                        System.out.println(deadline.toString());
                        System.out.printf("Now you have %d tasks in the list.\n", listLength);
                    } else {
                        throw new TooManyTasksException();
                    }
                } catch (TooManyTasksException e) {
                    System.out.println("WHOOPSIE!! You have too many tasks in the task list");
                } catch (MissingTaskMsgException e) {
                    System.out.println("WHOOPSIE!! Please enter the description of the task in this format\n" +
                            "'deadline -taskMsg- /by -byWhen-'");
                } catch (MissingArgument1Exception e) {
                    System.out.println("WHOOPSIE!! Please enter the deadline of the task in this format\n" +
                            "'deadline -taskMsg- /by -byWhen-'");
                }
            } else if (inputLine.toLowerCase().startsWith("event ")) {
                try {
                    System.out.println(breakerLine);
                    String fromWhen = "";
                    String toWhen = "";
                    String taskMsg = "";
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
                    } else if (fromIndex == -1){
                        throw new MissingArgument1Exception();
                    } else {
                        throw new MissingArgument2Exception();
                    }
                    if (listLength < 99) {
                        Event event = new Event(taskMsg, fromWhen, toWhen);
                        listOfTasks[listLength] = event;
                        listLength++;
                        System.out.println("Got it. I've added this task:");
                        System.out.println(event.toString());
                        System.out.printf("Now you have %d tasks in the list.\n", listLength);
                    } else {
                        throw new TooManyTasksException();
                    }
                } catch (TooManyTasksException e) {
                    System.out.println("WHOOPSIE!! You have too many tasks in the task list");
                } catch (MissingTaskMsgException e) {
                    System.out.println("WHOOPSIE!! Please enter the description of the event in this format\n" +
                            "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
                } catch (MissingArgument1Exception e) {
                    System.out.println("WHOOPSIE!! Please enter the start time of the event in this format\n" +
                            "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
                } catch (MissingArgument2Exception e) {
                    System.out.println("WHOOPSIE!! Please enter the end time of the event in this format\n" +
                            "'event -taskMsg- /from -fromWhen- /to -toWhen-'");
                }
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
}
