import Exceptions.MissingArgument1Exception;
import Exceptions.MissingArgument2Exception;
import Exceptions.MissingTaskMsgException;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Event extends Task{

    Object fromWhen;
    Object toWhen;

    public Event(String taskMsg, String fromWhenRaw, String toWhenRaw) {
        super(taskMsg);
        this.fromWhen = parseDateTime(fromWhenRaw);
        this.toWhen = parseDateTime(toWhenRaw);
    }

    public Event(String taskMsg, String fromWhenRaw, String toWhenRaw, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
        this.fromWhen = parseDateTime(fromWhenRaw);
        this.toWhen = parseDateTime(toWhenRaw);
    }

    private Object parseDateTime(String input) {
        try {
            return LocalDateTime.parse(input);
        } catch (DateTimeParseException e1) {
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e2) {
                return input;
            }
        }
    }

    private String formatForDisplay(Object date) {
        if (date instanceof LocalDateTime dt) {
            return dt.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss"));
        }
        if (date instanceof LocalDate d) {
            return d.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        }
        return date.toString();
    }

    @Override
    public String toString() {
        String eventTime = String.format(" (from: %s to: %s)", this.formatForDisplay(fromWhen), this.formatForDisplay(toWhen));
        return "[E]" + super.toString() + eventTime;
    }

    @Override
    public String toSavedString() {
        return String.format("E | %s | %s | %s", super.toSavedString(), this.fromWhen, this.toWhen);
    }

    @Override
    public Task isTaskOnDate(LocalDate date) {
        if (fromWhen instanceof LocalDate d) {
            if (date.equals(d)) {
                return this;
            }
        }

        if (toWhen instanceof LocalDate d) {
            if (date.equals(d)) {
                return this;
            }
        }

        if (fromWhen instanceof LocalDateTime dt) {
            if (date.equals(dt.toLocalDate())) {
                return this;
            }
        }

        if (toWhen instanceof LocalDateTime dt) {
            if (date.equals(dt.toLocalDate())) {
                return this;
            }
        }

        return null;
    }

    public static void event(List<Task> listOfTasks, String inputLine, FileWriter taskWriter) {
        try {
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
            listOfTasks.add(event);
            taskWriter.write(event.toSavedString() + "\n");
            taskWriter.flush();
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
}
