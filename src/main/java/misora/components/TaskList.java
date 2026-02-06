package misora.components;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import misora.tasks.Task;

/**
 * Represents a list of {@link Task} objects in the Misora application.
 * <p>
 * The {@code TaskList} class provides methods for adding, removing, retrieving,
 * and displaying tasks. It acts as the in-memory storage for tasks while
 * the application is running.
 */
public class TaskList {

    /**
     * The internal list that stores all {@link Task} objects managed by this {@code TaskList}.
     * <p>
     * This list serves as the in-memory collection for all tasks currently tracked by the application.
     */
    private final List<Task> listOfTasks;

    /**
     * Creates an empty {@code TaskList}.
     */
    public TaskList() {
        this.listOfTasks = new ArrayList<>();
    }

    /**
     * Creates a {@code TaskList} with a pre-existing list of tasks.
     *
     * @param listOfTasks A list of {@link Task} objects to initialize the task list
     */
    public TaskList(List<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    /**
     * Returns all tasks
     *
     * @return A list of {@link Task} objects containing the search string
     */
    public List<Task> listTasks() {

        return new ArrayList<>(listOfTasks);
    }

    /**
     * Returns the task at position i
     *
     * @param i The index of the task to be returned
     * @return A list of {@link Task} objects containing the search string
     */
    public Task get(int i) {
        return this.listOfTasks.get(i);
    }

    /**
     * Removes all tasks from the task list.
     */
    public void clearTaskList() {
        this.listOfTasks.clear();
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param i The index of the task to remove
     * @return The removed {@link Task}
     */
    public Task remove(int i) {
        return this.listOfTasks.remove(i);
    }

    /**
     * Returns all tasks that occur on the specified {@link LocalDate}.
     *
     * @param date The date to filter tasks by
     * @return A list of {@link Task} objects containing the search string
     */
    public List<Task> getTasksOnDate(LocalDate date) {

        List<Task> tasksOnDate = new ArrayList<>();

        for (Task task : listOfTasks) {
            Task taskOnDateReturn = task.isTaskOnDate(date);

            if (taskOnDateReturn != null) {
                tasksOnDate.add(taskOnDateReturn);
            }
        }

        return tasksOnDate;
    }

    /**
     * Returns all tasks that contain the specified {@link String}.
     *
     * @param searchString The string to filter tasks by
     * @return A list of {@link Task} objects containing the search string
     */
    public List<Task> getTasksContainingString(String searchString) {

        List<Task> tasksContainingString = new ArrayList<>();

        for (Task task : listOfTasks) {

            Task taskContainString = task.doesTaskContainString(searchString);
            if (taskContainString != null) {

                tasksContainingString.add(taskContainString);
            }
        }

        return tasksContainingString;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list
     */
    public int size() {
        return this.listOfTasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return {@code true} if the task list contains no tasks, {@code false} otherwise
     */
    public boolean isEmpty() {
        return this.listOfTasks.isEmpty();
    }

    /**
     * Adds a new task to the task list.
     *
     * @param t The {@link Task} to add
     */
    public void add(Task t) {
        this.listOfTasks.add(t);
    }
}
