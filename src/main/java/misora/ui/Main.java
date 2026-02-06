package misora.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The entry point for the Misora JavaFX application.
 * <p>
 * This class extends {@link javafx.application.Application} and is responsible
 * for initializing and displaying the main window of the Misora chat interface.
 * It sets up the primary stage, scene, and window layout.
 */
public class Main extends Application {

    /**
     * Initializes and displays the primary stage of the JavaFX application.
     * <p>
     * This method creates an instance of {@link MainWindow}, sets it as the root
     * of a {@link Scene}, and configures the {@link Stage} with title and size.
     *
     * @param stage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        MainWindow window = new MainWindow();
        Scene scene = new Scene(window.getRoot(), 420, 620);

        stage.setTitle("Misora");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main entry point of the program.
     * <p>
     * Launches the JavaFX application by invoking {@link Application#launch(String...)}.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
