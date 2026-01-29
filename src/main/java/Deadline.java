import Exceptions.MissingArgument1Exception;
import Exceptions.MissingTaskMsgException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Deadline extends Task{

    public Object byWhen;

    public Deadline(String taskMsg, String byWhenRaw){
        super(taskMsg);
        this.byWhen = parseDateTime(byWhenRaw);
    }

    public Deadline(String taskMsg, String byWhenRaw, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
        this.byWhen = parseDateTime(byWhenRaw);
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

        String deadline = String.format(" (by: %s)", this.formatForDisplay(byWhen));
        return "[D]" + super.toString() + deadline;
    }

    @Override
    public String toSavedString() {

        return String.format("D | %s | %s", super.toSavedString(), this.byWhen);
    }

    @Override
    public Task isTaskOnDate(LocalDate date) {
        if (byWhen instanceof LocalDate) {
            if (date.equals(byWhen)) {
                return this;
            }

        } else if (byWhen instanceof LocalDateTime dateTime) {
            if (date.equals(dateTime.toLocalDate())) {
                return this;
            }

        }
        return null;
    }

    public static void deadline(List<Task> listOfTasks, String inputLine, FileWriter taskWriter) {
        try {
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
            taskWriter.write(deadline.toSavedString() + "\n");
            taskWriter.flush();
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
}
