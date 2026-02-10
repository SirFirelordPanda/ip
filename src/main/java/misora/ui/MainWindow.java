package misora.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import misora.Misora;

/**
 * Represents the main window of the Misora chat application.
 * <p>
 * This class sets up the user interface for the chat, including the scrollable
 * chat area, user input field, send button, and initial greeting banner. It handles
 * displaying user and bot messages and coordinating interactions with the {@link Misora}
 * application logic.
 */
public class MainWindow {

    private static final String USER_STYLE =
            "-fx-background-color: #3A3A3A; " +
                    "-fx-text-fill: white; " +
                    "-fx-padding: 8; " +
                    "-fx-background-radius: 8;";

    private static final String BOT_STYLE =
            "-fx-background-color: #2D2D2D; " +
                    "-fx-text-fill: #E0E0E0; " +
                    "-fx-padding: 8; " +
                    "-fx-background-radius: 8;";

    /**
     * Scrollable container that holds the chat messages.
     */
    private final ScrollPane scrollPane;

    /**
     * The core Misora application instance used to generate responses.
     */
    private final Misora misora;

    /**
     * Vertical container holding individual chat message nodes.
     */
    private final VBox chatBox;

    /**
     * Text input field where the user types commands.
     */
    private final TextField input;

    /**
     * The root node of the scene graph for this window.
     */
    private final Parent root;

    /**
     * Constructs the {@code MainWindow}, initializes the UI components,
     * sets styles for dark mode, and displays the initial greeting message.
     */
    public MainWindow() {
        misora = new Misora("data/tasks.txt");

        chatBox = new VBox(10);
        chatBox.setFillWidth(true);
        chatBox.setStyle("-fx-background-color: #1E1E1E;");

        scrollPane = new ScrollPane(chatBox);
        scrollPane.setStyle(
                "-fx-background: #1E1E1E;" +
                        "-fx-background-color: #1E1E1E;" +
                        " -fx-padding: 0;" +
                        " -fx-control-inner-background: #1E1E1E;"
        );
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        chatBox.heightProperty().addListener((obs, oldVal, newVal) ->
                scrollPane.setVvalue(1.0)
        );

        input = new TextField();
        input.setPromptText("Type a command...");

        Button send = new Button("Send");

        input.setStyle("-fx-background-color: #2D2D2D; -fx-text-fill: white;");
        send.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");

        send.setOnAction(e -> handleInput());
        input.setOnAction(e -> handleInput());

        HBox inputRow = new HBox(10, input, send);
        HBox.setHgrow(input, Priority.ALWAYS);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root = new VBox(10, scrollPane, inputRow);
        root.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 10;");

        addBanner();
        addMessage(misora.getWelcomeMessage(), true);
    }

    /**
     * Returns the root {@link Parent} node of this window.
     *
     * @return the root node for the scene
     */
    public Parent getRoot() {
        assert root != null : "root should not be null";
        return root;
    }

    /**
     * Handles the user input when Enter is pressed or the Send button is clicked.
     * <p>
     * It displays the user's message, gets the bot response from {@link Misora},
     * displays it, and exits the application if the user types "bye".
     */
    private void handleInput() {
        assert input != null : "input should not be null when handling input";
        String text = input.getText().trim();
        if (text.isEmpty()) {
            return;
        }

        addMessage(text, false);

        String response = misora.getResponse(text);
        assert response != null : "response from Misora should not be null";
        addMessage(response, true);

        if (text.equalsIgnoreCase("bye")) {
            addMessage(misora.getExitMessage(), true);
            Platform.exit();
        }

        input.clear();
    }

    /**
     * Adds a message sent by the user to the chat area.
     *
     * @param text the message text typed by the user
     */
    private void addMessage(String text, boolean isBot) {
        assert text != null;
        Label label = new Label(text);
        label.setWrapText(true);
        label.setStyle(isBot ? BOT_STYLE : USER_STYLE);

        ImageView icon = new ImageView(isBot ? "images/bot.jpg" : "images/user.jpg");
        icon.setFitHeight(36);
        icon.setFitWidth(36);

        HBox box = isBot ? new HBox(10, icon, label) : new HBox(10, label, icon);
        box.setAlignment(isBot ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
        if (!isBot) box.setPadding(new Insets(0, 10, 0, 0));

        assert chatBox != null : "chatBox should not be null when adding user message";
        chatBox.getChildren().add(box);
    }

    /**
     * Adds a banner image to the top of the chat area.
     * <p>
     * The banner is horizontally centered and padded to match the chat layout.
     */
    private void addBanner() {
        ImageView banner = new ImageView("images/banner.jpg");
        banner.setFitWidth(380);
        banner.setPreserveRatio(true);

        HBox box = new HBox(banner);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(0, 6, 0, 0));

        assert chatBox != null : "chatBox should not be null when adding banner";
        chatBox.getChildren().add(box);
    }
}
