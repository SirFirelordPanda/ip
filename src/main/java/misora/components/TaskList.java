package misora.components;

import misora.tasks.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskList{

    private List<Task> listOfTasks;

    public TaskList() {
        this.listOfTasks = new ArrayList<>();
    }

    public TaskList(List<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    public void listTasks(Ui ui) {

        for (int i = 0; i < listOfTasks.size(); i++) {

            ui.showTasklist(String.format("%d. %s", i + 1, listOfTasks.get(i)));
        }
    }

    public Task get(int i) {
        return this.listOfTasks.get(i);
    }

    public void clearTaskList() {

        this.listOfTasks.clear();
    }

    public Task remove(int i) {

        return this.listOfTasks.remove(i);
    }

    public void showTasksOnDate(LocalDate date) {

        for (Task task : listOfTasks) {

            Task taskOnDateReturn = task.isTaskOnDate(date);
            if (taskOnDateReturn != null) {

                System.out.println(taskOnDateReturn.toString());
            }
        }
    }

    public void showTasksContainingString(String searchString) {

        for (Task task : listOfTasks) {

            Task taskContainString = task.doesTaskContainString(searchString);
            if (taskContainString != null) {

                System.out.println(taskContainString.toString());
            }
        }
    }

    public int size() {
        return this.listOfTasks.size();
    }

    public boolean isEmpty() {
        return this.listOfTasks.isEmpty();
    }

    public void add(Task t) {
        this.listOfTasks.add(t);
    }
}
