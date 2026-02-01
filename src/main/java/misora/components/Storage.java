package misora.components;

import misora.exceptions.CorruptedSavedTaskFileException;
import misora.exceptions.MisoraException;
import misora.exceptions.UnableToCloseStorageException;
import misora.exceptions.UnableToWriteToFileException;
import misora.tasks.Deadline;
import misora.tasks.Event;
import misora.tasks.Task;
import misora.tasks.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {

    private final File TASKFILE;
    private FileWriter taskWriter;
    private Scanner taskScanner;

    public Storage (String filePath) {
        TASKFILE = new File(filePath);
        if (!TASKFILE.exists()) {
            try {
                System.out.println("File does not exist\nCreating file");
                File parent = TASKFILE.getParentFile();
                if (parent != null) {
                    if (parent.mkdirs()) {
                        System.out.println("Folder created: " + TASKFILE.getParentFile());
                    }
                }

                if (TASKFILE.createNewFile()) {
                    System.out.println("File created: " + TASKFILE.getPath());
                }
            } catch (IOException e) {
                System.out.println("File was unable to be created");
            }
        }

        try {
            taskScanner = new Scanner(TASKFILE);
            taskWriter = new FileWriter(TASKFILE, true);
        } catch (java.io.FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("data/misora.txt could not be found\nScanner could not be initiated");
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
            System.out.println("data/misora.txt could not be found\nWriter could not be initiated");
        }
    }

    public List<Task> load() throws CorruptedSavedTaskFileException{
        List<Task> listOfTasks = new ArrayList<>();

        if (taskScanner != null) {
            while (taskScanner.hasNext()) {
                String task = taskScanner.nextLine();
                String[] parts = task.split("\\s*\\|\\s*");
                try {
                    switch (parts[0]) {
                        case "T":
                            listOfTasks.add(new ToDo(parts[2], parts[1].equalsIgnoreCase("X")));
                            break;
                        case "D":
                            listOfTasks.add(new Deadline(parts[2], parts[3], parts[1].equalsIgnoreCase("X")));
                            break;
                        case "E":
                            listOfTasks.add(new Event(parts[2], parts[3], parts[4], parts[1].equalsIgnoreCase("X")));
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

    public void clearSavedFile() {
        try {
            new FileWriter(TASKFILE).close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateSavedFileFromTaskList(TaskList taskList) throws IOException{
        FileWriter writer = new FileWriter(TASKFILE); // overwrite
        for (int i = 0; i < taskList.size(); i++) {
            writer.write(taskList.get(i).toSavedString() + "\n");
        }
        writer.close();
    }

    public void addTaskToFile(Task task, Ui ui) throws UnableToWriteToFileException {
        try {
            taskWriter.write(task.toSavedString() + "\n");
            taskWriter.flush();
        } catch (java.io.IOException e) {
            throw new UnableToWriteToFileException("Unable to write new task to file");
        }
    }

    public void exit() throws UnableToCloseStorageException {
        try {
            taskScanner.close();
            taskWriter.close();
        } catch (IOException e) {
            throw new UnableToCloseStorageException("Unable to close storage properly");
        }
    }

}
