package misora.commands;

import misora.tasks.Priority;

public class AddCommandFactory {

    private static Priority extractPriority(StringBuilder input) {
        Priority priority = Priority.MEDIUM;

        int priorityIndex = input.indexOf("/priority");
        if (priorityIndex != -1) {
            String priorityPart = input.substring(priorityIndex + 9).trim();
            input.delete(priorityIndex, input.length()); // remove /priority section

            try {
                priority = Priority.valueOf(priorityPart.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid priority, using MEDIUM by default.");
            }
        }

        return priority;
    }


    public static AddCommand createTodo(String fullCommand) {
        StringBuilder remaining = new StringBuilder(fullCommand.substring(5).trim());

        Priority priority = extractPriority(remaining);

        String taskMsg = remaining.toString().trim();

        return new AddCommand(taskMsg, priority);
    }

    public static AddCommand createDeadline(String fullCommand) {
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


    public static AddCommand createEvent(String fullCommand) {
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

