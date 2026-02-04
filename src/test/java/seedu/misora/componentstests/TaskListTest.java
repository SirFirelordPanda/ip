package seedu.misora.componentstests;

import misora.components.TaskList;
import misora.components.Ui;
import misora.tasks.Deadline;
import misora.tasks.ToDo;
import misora.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    //Basic List Operations tests

    @Test
    void add_and_get_returnsCorrectTask() {
        Task task = new ToDo("read book");
        taskList.add(task);

        assertEquals(task, taskList.get(0));
    }

    @Test
    void remove_removesAndReturnsCorrectTask() {
        Task task = new ToDo("read book");
        taskList.add(task);

        Task removed = taskList.remove(0);
        assertEquals(task, removed);
        assertTrue(taskList.isEmpty());
    }

    @Test
    void clearTaskList_emptiesList() {
        taskList.add(new ToDo("task1"));
        taskList.add(new ToDo("task2"));

        taskList.clearTaskList();
        assertTrue(taskList.isEmpty());
        assertEquals(0, taskList.size());
    }

    @Test
    void size_and_isEmpty_workCorrectly() {
        assertEquals(0, taskList.size());
        assertTrue(taskList.isEmpty());

        taskList.add(new ToDo("task1"));
        assertEquals(1, taskList.size());
        assertFalse(taskList.isEmpty());
    }

    //UI Interaction tests

    private static class StubUi extends Ui {
        List<String> displayed = new ArrayList<>();

        @Override
        public void showTasklist(String message) {
            displayed.add(message);
        }
    }

    @Test
    void listTasks_callsUiShowTasklist() {
        taskList.add(new ToDo("task1"));
        taskList.add(new ToDo("task2"));

        StubUi ui = new StubUi();
        taskList.listTasks(ui);

        assertEquals(2, ui.displayed.size());
        assertTrue(ui.displayed.get(0).contains("task1"));
        assertTrue(ui.displayed.get(1).contains("task2"));
    }

    //Date Filtering tests

    @Test
    void getTasksOnDate_returnsOnlyTasksOnCorrectDate() {
        Deadline d1 = new Deadline("submit report", "2026-02-01");
        Deadline d2 = new Deadline("submit project", "2026-03-01");

        taskList.add(d1);
        taskList.add(d2);

        LocalDate targetDate = LocalDate.of(2026, 2, 1);

        List<Task> result = taskList.getTasksOnDate(targetDate);

        assertEquals(1, result.size());
        assertEquals(d1, result.get(0));
    }

    @Test
    void getTasksOnDate_noMatchingTasks_returnsEmptyList() {
        taskList.add(new Deadline("submit project", "2026-03-01"));

        List<Task> result = taskList.getTasksOnDate(
                LocalDate.of(2026, 2, 1));

        assertTrue(result.isEmpty());
    }
}

