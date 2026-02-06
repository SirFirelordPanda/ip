package seedu.misora.componentstests;

import java.time.LocalDate;
import java.util.List;

import misora.components.Ui;
import misora.components.TaskList;
import misora.tasks.ToDo;
import misora.tasks.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UiTest {

    @Test
    void showWelcome_returnsGreeting() {
        Ui ui = new Ui();
        String result = ui.showWelcome();
        assertTrue(result.contains("Nyahallo!"));
        assertTrue(result.contains("MISORA"));
    }

    @Test
    void showExit_returnsExitMessage() {
        Ui ui = new Ui();
        String result = ui.showExit();
        assertTrue(result.contains("Bye bye"));
        assertTrue(result.contains("see you again"));
    }

    @Test
    void showError_returnsErrorMessage() {
        Ui ui = new Ui();
        String result = ui.showError("Test error");
        assertTrue(result.contains("Test error"));
    }

    @Test
    void showList_returnsFormattedTaskList() {
        Ui ui = new Ui();
        Task task1 = new ToDo("task 1");
        Task task2 = new ToDo("task 2");
        TaskList taskList = new TaskList();
        taskList.add(task1);
        taskList.add(task2);

        String result = ui.showList(taskList.listTasks());
        assertTrue(result.contains("1. [T][ ] task 1"));
        assertTrue(result.contains("2. [T][ ] task 2"));
    }

    @Test
    void showList_emptyList_returnsNoTasksMessage() {
        Ui ui = new Ui();
        String result = ui.showList(List.of());
        assertTrue(result.contains("No tasks found"));
    }

    @Test
    void showListClear_returnsMessage() {
        Ui ui = new Ui();
        String result = ui.showListClear();
        assertTrue(result.contains("List has been cleared"));
    }

    @Test
    void showLoadingError_returnsMessage() {
        Ui ui = new Ui();
        String result = ui.showLoadingError();
        assertTrue(result.contains("Misora initialisation failed"));
    }

    @Test
    void showMarkTask_returnsMessage() {
        Ui ui = new Ui();
        Task task = new ToDo("read book");
        task.setTaskDone(true);

        String result = ui.showMarkTask(task);
        assertTrue(result.contains("marked this task as done"));
        assertTrue(result.contains("read book"));
    }

    @Test
    void showUnmarkTask_returnsMessage() {
        Ui ui = new Ui();
        Task task = new ToDo("read book");

        String result = ui.showUnmarkTask(task);
        assertTrue(result.contains("marked this task as not done yet"));
        assertTrue(result.contains("read book"));
    }

    @Test
    void showDeleteTask_returnsMessage() {
        Ui ui = new Ui();
        Task task = new ToDo("read book");
        TaskList taskList = new TaskList();
        taskList.add(task);

        String result = ui.showDeleteTask(task, taskList);
        assertTrue(result.contains("Noted. I have removed this task"));
        assertTrue(result.contains("read book"));
        assertTrue(result.contains("Now you have 1 tasks"));
    }

    @Test
    void showAddTask_returnsMessage() {
        Ui ui = new Ui();
        Task task = new ToDo("read book");
        TaskList taskList = new TaskList();
        taskList.add(task);

        String result = ui.showAddTask(task, taskList);
        assertTrue(result.contains("Got it. I've added this task"));
        assertTrue(result.contains("read book"));
        assertTrue(result.contains("Now you have 1 tasks"));
    }

    @Test
    void showTasksOnDate_noTasks_returnsNoTasksMessage() {
        Ui ui = new Ui();
        LocalDate date = LocalDate.of(2026, 2, 1);

        String result = ui.showTasksOnDate(List.of(), date);
        assertTrue(result.contains("No tasks found"));
        assertTrue(result.contains("2026-02-01"));
    }

    @Test
    void showTasksOnDate_withTasks_returnsTasksAndDate() {
        Ui ui = new Ui();
        LocalDate date = LocalDate.of(2026, 2, 1);
        Task task = new ToDo("read book");

        String result = ui.showTasksOnDate(List.of(task), date);
        assertTrue(result.contains("Tasks on"));
        assertTrue(result.contains("2026-02-01"));
        assertTrue(result.contains("read book"));
    }

    @Test
    void showTasksContainingString_noTasks_returnsNoTasksMessage() {
        Ui ui = new Ui();

        String result = ui.showTasksContainingString(List.of(), "report");
        assertTrue(result.contains("No tasks found"));
        assertTrue(result.contains("report"));
    }

    @Test
    void showTasksContainingString_withTasks_returnsTasksAndString() {
        Ui ui = new Ui();
        Task task = new ToDo("write report");

        String result = ui.showTasksContainingString(List.of(task), "report");
        assertTrue(result.contains("Tasks containing"));
        assertTrue(result.contains("report"));
        assertTrue(result.contains("write report"));
    }
}
