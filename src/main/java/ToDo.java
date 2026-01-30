import Exceptions.MissingTaskMsgException;

import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;

public class ToDo extends Task{

    public ToDo(String taskMsg) {

        super(taskMsg);
    }

    public ToDo(String taskMsg, boolean isTaskDone) {

        super(taskMsg, isTaskDone);
    }

    @Override
    public String toString() {

        return "[T]" + super.toString();
    }

    @Override
    public String toSavedString() {

        return String.format("T | %s", super.toSavedString());
    }

    public static void todo(List<Task> listOfTasks, String inputLine, FileWriter taskWriter){
        try {
            String taskMsg = inputLine.substring(5).trim();
            if (taskMsg.isEmpty()) {
                throw new MissingTaskMsgException();
            }

            ToDo todo = new ToDo(taskMsg);
            listOfTasks.add(todo);
            taskWriter.write(todo.toSavedString() + "\n");
            taskWriter.flush();
            System.out.println("Got it. I've added this task:");
            System.out.println(todo);
            System.out.printf("Now you have %d tasks in the list.\n",listOfTasks.size());

        } catch (MissingTaskMsgException e) {
            System.out.println("WHOOPSIE!! Please enter the description of the task in this format 'todo -taskMsg-'");
        } catch (java.io.IOException e) {
            System.out.println("Unable to write new todo to file");
        }
    }
}
