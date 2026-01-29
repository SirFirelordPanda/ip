import Exceptions.MissingArgument1Exception;
import Exceptions.MissingTaskMsgException;

import java.io.FileWriter;
import java.util.List;

public class Deadline extends Task{

    public String byWhen;

    public Deadline(String taskMsg, String byWhen){
        super(taskMsg);
        this.byWhen = byWhen;
    }

    public Deadline(String taskMsg, String byWhen, boolean isTaskDone) {
        super(taskMsg, isTaskDone);
        this.byWhen = byWhen;
    }

    @Override
    public String toString() {
        String deadline = String.format(" (by: %s)", byWhen);
        return "[D]" + super.toString() + deadline;
    }

    @Override
    public String toSavedString() {

        return String.format("D | %s | %s", super.toSavedString(), this.byWhen);
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
