package misora;

import misora.commands.Command;
import misora.components.*;
import misora.exceptions.MisoraException;

public class Misora {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new Misora("data/tasks.txt").run();
    }
}
