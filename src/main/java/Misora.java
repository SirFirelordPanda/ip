import java.util.Scanner;

public class Misora {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] listOfStrings = new String[100];
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
                    System.out.printf("%d. %s\n", i, listOfStrings[i - 1]);
                }
            }

            System.out.println(breakerLine);
            if (listLength < 99) {
                listOfStrings[listLength] = inputLine;
                listLength++;
            }
            System.out.println("added: " + inputLine);
        }

        scanner.close();
        System.out.println(breakerLine);
        System.out.println(exit);
    }
}
