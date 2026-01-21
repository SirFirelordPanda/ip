import java.util.Scanner;

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

                for (int i = 1; i < listLength + 1; i ++) {
                    System.out.printf("%d.%s\n", i, listOfTasks[i - 1].toString());
                }

            } else if (inputLine.toLowerCase().startsWith("mark ")) {

                try {
                    String numberPart = inputLine.substring(5).trim();
                    int numberMarked = Integer.parseInt(numberPart);
                    listOfTasks[numberMarked - 1].setTaskDone(true);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.printf("   %s\n", listOfTasks[numberMarked - 1].toString());
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
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.printf("   %s\n", listOfTasks[numberUnmarked - 1].toString());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number given");
                } catch (NullPointerException e) {
                    System.out.println("Number given is too big");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Number given is too small");
                }

            } else {
                System.out.println(breakerLine);
                if (listLength < 99) {
                    listOfTasks[listLength] = new Task(inputLine);
                    listLength++;
                }
                System.out.println("added: " + inputLine);
            }
        }

        scanner.close();
        System.out.println(breakerLine);
        System.out.println(exit);
    }
}
