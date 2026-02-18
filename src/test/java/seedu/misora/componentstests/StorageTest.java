package seedu.misora.componentstests;

import misora.components.Storage;
import misora.components.TaskList;
import misora.components.Ui;
import misora.exceptions.CorruptedSavedTaskFileException;
import misora.exceptions.MisoraException;
import misora.exceptions.UnableToCloseStorageException;
import misora.exceptions.UnableToWriteToFileException;
import misora.tasks.ToDo;
import misora.tasks.Task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    private File tempFile;
    private Storage storage;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("storageTest", ".txt").toFile();
        storage = new Storage(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void addTaskToFile_taskCanBeLoaded() throws MisoraException, UnableToWriteToFileException, CorruptedSavedTaskFileException {
        ToDo task = new ToDo("read book");
        Ui ui = new Ui();

        storage.addTaskToFile(task, ui);

        List<Task> tasks = storage.load();
        assertEquals(1, tasks.size());
        assertInstanceOf(ToDo.class, tasks.get(0));
        assertEquals("read book", tasks.get(0).getDescription());
    }

    @Test
    void clearSavedFile_fileIsEmpty() throws MisoraException, UnableToWriteToFileException, CorruptedSavedTaskFileException {
        ToDo task = new ToDo("read book");
        storage.addTaskToFile(task, new Ui());

        storage.clearSavedFile();
        List<Task> tasks = storage.load();
        assertEquals(0, tasks.size());
    }

    @Test
    void updateSavedFileFromTaskList_overwritesFile() throws IOException, MisoraException, CorruptedSavedTaskFileException {
        TaskList taskList = new TaskList();
        taskList.add(new ToDo("task1"));
        taskList.add(new ToDo("task2"));

        storage.updateSavedFileFromTaskList(taskList);

        List<Task> loadedTasks = storage.load();
        assertEquals(2, loadedTasks.size());
        assertEquals("task1", loadedTasks.get(0).getDescription());
        assertEquals("task2", loadedTasks.get(1).getDescription());
    }

    @Test
    void load_corruptedFile_throwsCorruptedSavedTaskFileException() throws IOException {
        Files.writeString(tempFile.toPath(), "X | ? | garbage");

        assertThrows(CorruptedSavedTaskFileException.class, () -> storage.load());
    }

    @Test
    void exit_closesWithoutException() throws UnableToCloseStorageException {
        assertDoesNotThrow(storage::exit);
    }

    @Test
    void exit_throwsUnableToCloseStorageException_ifIOExceptionOccurs() {
        Storage brokenStorage = new Storage(tempFile.getAbsolutePath()) {
            @Override
            public void exit() throws UnableToCloseStorageException {
                throw new UnableToCloseStorageException("Simulated IO failure");
            }
        };

        assertThrows(UnableToCloseStorageException.class, brokenStorage::exit);
    }

    @Test
    void addTaskToFile_throwsUnableToWriteToFileException_ifWriterFails() {
        Storage brokenStorage = new Storage(tempFile.getAbsolutePath()) {
            @Override
            public void addTaskToFile(Task task, Ui ui) throws UnableToWriteToFileException {
                throw new UnableToWriteToFileException("Simulated write failure");
            }
        };

        assertThrows(UnableToWriteToFileException.class,
                () -> brokenStorage.addTaskToFile(new ToDo("read"), new Ui()));
    }
}
