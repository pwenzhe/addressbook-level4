package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.commons.events.ui.ChangeInformationPanelRequestEvent;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App). */
    void start(Stage primaryStage);

    /** Changes the PersonInformationPanels */
    void changeInformationPanel(ChangeInformationPanelRequestEvent event);

    /** Changes the stylesheet used by the UI on changetheme command. */
    void changeTheme();


    /** Stops the UI. */
    void stop();
}
