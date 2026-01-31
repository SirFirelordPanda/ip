package seedu.misora.componentstests;

import misora.components.Ui;
import misora.components.TaskList;
import misora.tasks.ToDo;
import misora.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UiTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    //Console output tests

    @Test
    void showError_printsMessage() {
        Ui ui = new Ui();
        ui.showError("Test error");
        String output = outContent.toString();
        assertTrue(output.contains("Test error"));
    }

    @Test
    void showTasklist_printsTask() {
        Ui ui = new Ui();
        ui.showTasklist("Task 1");
        String output = outContent.toString();
        assertTrue(output.contains("Task 1"));
    }

    @Test
    void showListClear_printsMessage() {
        Ui ui = new Ui();
        ui.showListClear();
        String output = outContent.toString();
        assertTrue(output.contains("List has been cleared"));
    }

    @Test
    void showLoadingError_printsMessage() {
        Ui ui = new Ui();
        ui.showLoadingError();
        String output = outContent.toString();
        assertTrue(output.contains("misora.Misora initialisation failed"));
    }

    @Test
    void showMarkTask_printsMessage() {
        Ui ui = new Ui();
        Task task = new ToDo("read book");
        task.setTaskDone(true);
        ui.showMarkTask(task);
        String output = outContent.toString();
        assertTrue(output.contains("marked this task as done"));
        assertTrue(output.contains("read book"));
    }

    @Test
    void showUnmarkTask_printsMessage() {
        Ui ui = new Ui();
        Task task = new ToDo("read book");
        ui.showUnmarkTask(task);
        String output = outContent.toString();
        assertTrue(output.contains("marked this task as not done yet"));
        assertTrue(output.contains("read book"));
    }

    @Test
    void showDeleteTask_printsMessage() {
        Ui ui = new Ui();
        Task task = new ToDo("read book");
        TaskList taskList = new TaskList();
        taskList.add(task);
        ui.showDeleteTask(task, taskList);
        String output = outContent.toString();
        assertTrue(output.contains("Noted. I have removed this task"));
        assertTrue(output.contains("read book"));
        assertTrue(output.contains("Now you have 1 tasks"));
    }

    @Test
    void showAddTask_printsMessage() {
        Ui ui = new Ui();
        Task task = new ToDo("read book");
        TaskList taskList = new TaskList();
        taskList.add(task);
        ui.showAddTask(task, taskList);
        String output = outContent.toString();
        assertTrue(output.contains("Got it. I've added this task"));
        assertTrue(output.contains("read book"));
        assertTrue(output.contains("Now you have 1 tasks"));
    }

    @Test
    void showDate_printsDate() {
        Ui ui = new Ui();
        LocalDate date = LocalDate.of(2026, 2, 1);
        ui.showDate(date);
        String output = outContent.toString();
        assertTrue(output.contains("2026-02-01"));
    }

    //readCommand tests

    @Test
    void readCommand_readsInput() {
        String input = "todo read book\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);

        Ui ui = new Ui();
        String command = ui.readCommand();
        assertEquals("todo read book", command);
    }
}
