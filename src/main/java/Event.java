import Exceptions.MissingArgument1Exception;
import Exceptions.MissingArgument2Exception;
import Exceptions.MissingTaskMsgException;

import java.io.FileWriter;
import java.util.List;

public class Event extends Task{

    String fromWhen;
    String toWhen;

    public Event(String taskMsg, String fromWhen, String toWhen) {
        super(taskMsg);
        this.fromWhen = fromWhen;
        this.toWhen = toWhen;
    }

    public Event(String taskMsg, String fromWhen, String toWhen, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
        this.fromWhen = fromWhen;
        this.toWhen = toWhen;
    }

    @Override
    public String toString() {
        String eventTime = String.format(" (from: %s to: %s)", fromWhen, toWhen);
        return "[E]" + super.toString() + eventTime;
    }

    @Override
    public String toSavedString() {
        return String.format("E | %s | %s | %s", super.toSavedString(), this.fromWhen, this.toWhen);
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
