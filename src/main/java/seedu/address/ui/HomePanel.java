package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * The Home Panel of the App.
 */
public class HomePanel extends UiPart<Region> {

    private static final String FXML = "HomePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public HomePanel() {
        super(FXML);
    }
}
