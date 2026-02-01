package misora;

import misora.commands.Command;
import misora.components.*;
import misora.exceptions.MisoraException;

/**
 * The main entry point of the Misora application.
 * <p>
 * {@code Misora} coordinates the interaction between the {@link Ui},
 * {@link Parser}, {@link TaskList}, and {@link Storage}. It is responsible
 * for initializing the application, running the command-processing loop,
 * and terminating the program gracefully when the user exits.
 */
public class Misora {

    /**
     * Handles loading and saving tasks to persistent storage.
     */
    private Storage storage;

    /**
     * The in-memory list of tasks currently managed by the application.
     */
    private TaskList tasks;

    /**
     * Handles all user interaction and output.
     */
    private Ui ui;

    /**
     * Constructs a {@code Misora} instance using the given file path for storage.
     * <p>
     * Attempts to load previously saved tasks from the file. If loading fails,
     * an empty task list is created and an error message is shown to the user.
     *
     * @param filePath The path to the file used for storing tasks
     */
    public Misora(String filePath) {

        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MisoraException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main execution loop of the Misora application.
     * <p>
     * Continuously reads user commands, parses them into {@link Command}
     * objects, and executes them until an exit command is issued.
     */
    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (MisoraException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }

        ui.showExit();
    }

    /**
     * The entry point of the Misora application.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Misora("data/tasks.txt").run();
    }
}
