import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Exceptions.*;

public class Misora {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> listOfTasks = new ArrayList<>();

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
                for (int i = 1, listLength = listOfTasks.size(); i < listLength + 1; i ++) {
                    System.out.printf("%d.%s\n", i, listOfTasks.get(i - 1).toString());
                }

            } else if (inputLine.toLowerCase().startsWith("mark ")) {

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

            } else if (inputLine.toLowerCase().startsWith("unmark ")) {

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

            } else if (inputLine.toLowerCase().startsWith("todo ")) {
                try {
                    System.out.println(breakerLine);
                    String taskMsg = inputLine.substring(5).trim();
                    if (taskMsg.isEmpty()) {
                        throw new MissingTaskMsgException();
                    }

                    ToDo todo = new ToDo(taskMsg);
                    listOfTasks.add(todo);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(todo);
                    System.out.printf("Now you have %d tasks in the list.\n",listOfTasks.size());

                } catch (MissingTaskMsgException e) {
                    System.out.println("WHOOPSIE!! Please enter the description of the task in this format 'todo -taskMsg-'");
                }

            } else if (inputLine.toLowerCase().startsWith("deadline ")) {

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
                    System.out.println("Got it. I've added this task:");
                    System.out.println(deadline);
                    System.out.printf("Now you have %d tasks in the list.\n",listOfTasks.size());

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
                    } else if (fromIndex == -1){
                        throw new MissingArgument1Exception();
                    } else {
                        throw new MissingArgument2Exception();
                    }

                    Event event = new Event(taskMsg, fromWhen, toWhen);
                    listOfTasks.add(event);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(event);
                    System.out.printf("Now you have %d tasks in the list.\n",listOfTasks.size());

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
            } else if (inputLine.toLowerCase().startsWith("delete ")){
                try {
                    String numberPart = inputLine.substring(7).trim();
                    int numberDeleted = Integer.parseInt(numberPart);
                    Task removedTask = listOfTasks.remove(numberDeleted - 1);
                    System.out.println(breakerLine);
                    System.out.println("Noted. I have removed this task:");
                    System.out.printf("  %s\n", removedTask.toString());
                    System.out.printf("Now you have %d tasks in the list.\n",listOfTasks.size());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number given");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Number is not within list size");
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
