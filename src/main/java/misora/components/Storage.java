package misora.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import misora.exceptions.CorruptedSavedTaskFileException;
import misora.exceptions.MisoraException;
import misora.exceptions.UnableToCloseStorageException;
import misora.exceptions.UnableToWriteToFileException;
import misora.tasks.*;

/**
 * Represents a storage block that handles reading from and writing to
 * the persistent task storage file in the Misora application.
 * <p>
 * The {@code Storage} class is responsible for:
 * <ul>
 *   <li>Loading tasks from a saved file into a {@link TaskList}</li>
 *   <li>Saving tasks to the file when they are added, updated, or deleted</li>
 *   <li>Clearing the file or closing resources when the application exits</li>
 * </ul>
 */
public class Storage {

    /**
     * The {@link File} used for storing all task data.
     * <p>
     * This file is the persistent storage backend for tasks in the application.
     */
    private final File taskFile;

    /**
     * The {@link FileWriter} used to write tasks to {@link #taskFile}.
     */
    private FileWriter taskWriter;

    /**
     * The {@link Scanner} used to read tasks from {@link #taskFile}.
     */
    private Scanner taskScanner;

    /**
     * Creates a {@code Storage} object for a specified file path.
     * <p>
     * If the file or its parent directories do not exist, they are created.
     * Initializes a {@link Scanner} for reading and a {@link FileWriter} for appending tasks.
     *
     * @param filePath The file path to store the task data
     */
    public Storage(String filePath) {
        assert filePath != null : "filePath reference should not be null";

        taskFile = new File(filePath);
        if (!taskFile.exists()) {
            try {
                System.out.println("File does not exist\nCreating file");
                File parent = taskFile.getParentFile();
                if (parent != null) {
                    if (parent.mkdirs()) {
                        System.out.println("Folder created: " + taskFile.getParentFile());
                    }
                }

                if (taskFile.createNewFile()) {
                    System.out.println("File created: " + taskFile.getPath());
                }
            } catch (IOException e) {
                System.out.println("File was unable to be created");
            }
        }

        try {
            taskScanner = new Scanner(taskFile);
            taskWriter = new FileWriter(taskFile, true);
        } catch (java.io.FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("data/misora.txt could not be found\nScanner could not be initiated");
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
            System.out.println("data/misora.txt could not be found\nWriter could not be initiated");
        }
    }

    /**
     * Loads tasks from the storage file into a list.
     * <p>
     * Each line of the file is parsed into a {@link Task} object, which
     * can be a {@link ToDo}, {@link Deadline}, or {@link Event}. If the file
     * contains corrupted or unrecognized data, a {@link CorruptedSavedTaskFileException} is thrown.
     *
     * @return A {@link List} of {@link Task} objects loaded from the file
     * @throws CorruptedSavedTaskFileException If the file contains invalid or corrupted task data
     */
    public List<Task> load() throws CorruptedSavedTaskFileException {
        assert taskFile != null : "taskFile must exist before loading";

        List<Task> listOfTasks = new ArrayList<>();

        if (taskScanner != null) {
            while (taskScanner.hasNext()) {
                String task = taskScanner.nextLine();
                String[] parts = task.split("\\s*\\|\\s*");
                try {
                    Priority p;
                    switch (parts[2]) {
                    case "L":
                        p = Priority.LOW;
                        break;
                    case "M":
                        p = Priority.MEDIUM;
                        break;
                    case "H":
                        p = Priority.HIGH;
                        break;
                    default:
                        //set default priority
                        p = Priority.MEDIUM;
                    }

                    switch (parts[0]) {
                    case "T":
                        listOfTasks.add(new ToDo(parts[3], parts[1].equalsIgnoreCase("X"), p));
                        break;
                    case "D":
                        listOfTasks.add(new Deadline(parts[3], parts[4], parts[1].equalsIgnoreCase("X"), p));
                        break;
                    case "E":
                        listOfTasks.add(new Event(parts[3], parts[4], parts[5], parts[1].equalsIgnoreCase("X"), p));
                        break;
                    default:
                        throw new CorruptedSavedTaskFileException();
                    }
                } catch (MisoraException e) {
                    throw new CorruptedSavedTaskFileException();
                }
            }
        }

        return listOfTasks;
    }

    /**
     * Clears all data from the storage file.
     * <p>
     * This effectively removes all saved tasks.
     */
    public void clearSavedFile() {
        try {
            new FileWriter(taskFile).close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the storage file with the current tasks in the given {@link TaskList}.
     * <p>
     * This overwrites the file to match the current state of the task list.
     *
     * @param taskList The {@link TaskList} containing tasks to save
     * @throws IOException If an error occurs while writing to the file
     */
    public void updateSavedFileFromTaskList(TaskList taskList) throws IOException {
        assert taskList != null : "TaskList must not be null";

        FileWriter writer = new FileWriter(taskFile); // overwrite
        for (int i = 0; i < taskList.size(); i++) {
            writer.write(taskList.get(i).toSavedString() + "\n");
        }
        writer.close();
    }

    /**
     * Appends a single {@link Task} to the storage file.
     *
     * @param task The {@link Task} to add
     * @param ui The {@link Ui} (not used in this method but required by the signature)
     * @throws UnableToWriteToFileException If an I/O error occurs while writing
     */
    public void addTaskToFile(Task task, Ui ui) throws UnableToWriteToFileException {
        assert task != null : "Task must not be null";
        assert ui != null : "Ui must not be null";

        try {
            taskWriter.write(task.toSavedString() + "\n");
            taskWriter.flush();
        } catch (java.io.IOException e) {
            throw new UnableToWriteToFileException("Unable to write new task to file");
        }
    }

    /**
     * Closes the {@link Scanner} and {@link FileWriter} associated with this storage.
     *
     * @throws UnableToCloseStorageException If an I/O error occurs while closing resources
     */
    public void exit() throws UnableToCloseStorageException {
        assert taskScanner != null : "Scanner should be initialized";
        assert taskWriter != null : "Writer should be initialized";

        try {
            taskScanner.close();
            taskWriter.close();
        } catch (IOException e) {
            throw new UnableToCloseStorageException("Unable to close storage properly");
        }
    }

}
