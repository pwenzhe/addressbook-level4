# johnweikangong
###### \java\seedu\address\commons\events\ui\ChangeInformationPanelRequestEvent.java
``` java
/**
 * Indicates a request for Information Panel change.
 */
public class ChangeInformationPanelRequestEvent extends BaseEvent {

    private String panelRequestEvent;

    public ChangeInformationPanelRequestEvent(String panelRequestEvent) {
        this.panelRequestEvent = panelRequestEvent;
    }

    public String getPanelRequestEvent() {
        return panelRequestEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\HelpCommand.java
``` java
/**
 * Shows help panel of the App.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_ALIAS = "sos";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    public static final String HELP_PANEL_REQUEST_EVENT = "HelpPanel";

    public static final String MESSAGE_SUCCESS = "Help is here!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeInformationPanelRequestEvent(HELP_PANEL_REQUEST_EVENT));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\HomeCommand.java
``` java
/**
 * Shows home panel of the App.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";
    public static final String COMMAND_ALIAS = "ho";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns you back to home.\n"
            + "Example: " + COMMAND_WORD;
    public static final String HOME_PANEL_REQUEST_EVENT = "HomePanel";

    public static final String MESSAGE_SUCCESS = "You are back at ho-ho-home!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeInformationPanelRequestEvent(HOME_PANEL_REQUEST_EVENT));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * If postal code is not specified and can be found in address, return
     * postal code found in address, else return postal code entered
     * @throws IllegalValueException if postal code entered is not exactly 6 digit
     * and/or more than the postal code upper range.
     */
    private PostalCode getPostalCode(Optional<String> address, Optional<String> postalCode)
            throws IllegalValueException {
        Pattern pattern = Pattern.compile("(?<postalCode>(?<!\\d)\\d{6}(?!\\d))");
        Matcher match = pattern.matcher(address.get());

        if (!postalCode.isPresent() && match.find()) {
            return ParserUtil.parsePostalCode(Optional.of(match.group("postalCode"))).get();
        } else if (!postalCode.isPresent()) {
            return new PostalCode("");
        } else {
            return ParserUtil.parsePostalCode(postalCode).get();
        }
    }
```
###### \java\seedu\address\model\person\PostalCode.java
``` java
/**
 * Represents a Person's postal code in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {


    public static final String MESSAGE_POSTALCODE_CONSTRAINTS =
            "Postal codes can only contain numbers, should be exactly 6 digits long and less than 800000";
    public static final String POSTALCODE_VALIDATION_REGEX = "(?<!\\d)\\d{6}(?!\\d)";
    public static final int POSTALCODE_UPPER_RANGE = 800000;
    public final Boolean isPresent;
    public final String value;

    /**
     * Validates given postal code.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);

        if ("".equals(postalCode)) {
            isPresent = false;
            this.value = postalCode;
        } else {
            String trimmedPostalCode = postalCode.trim();
            if (!isValidPostalCode(trimmedPostalCode)) {
                throw new IllegalValueException(MESSAGE_POSTALCODE_CONSTRAINTS);
            }

            isPresent = true;
            this.value = trimmedPostalCode;
        }
    }

    /**
     * Returns true if a given string is a valid person postal code.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTALCODE_VALIDATION_REGEX) && Integer.parseInt(test) <= POSTALCODE_UPPER_RANGE;
    }

    /**
     * Returns true if a valid postal code is present.
     * @return
     */
    public boolean isPresentPostalCode() {
        return isPresent;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalCode // instanceof handles nulls
                && this.value.equals(((PostalCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\ui\GoogleMapBrowserPanel.java
``` java
/**
 * The google map browser panel of the App.
 */
public class GoogleMapBrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLEMAP_SEARCH_URL_PREFIX = "https://www.google.com/maps/place/";
    public static final String GOOGLEMAP_SEARCH_URL_SUFFIX = "?dg=dbrw&newdg=1";
    private static final String FXML = "GoogleMapBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView googleMapBrowser;

    public GoogleMapBrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Tokenise the address to remove substring after "#" and use this address to load page
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        String []segment = person.getAddress().value.split("#");

        loadPage(GOOGLEMAP_SEARCH_URL_PREFIX + segment[0].replaceAll(" ", "+")
            + GOOGLEMAP_SEARCH_URL_SUFFIX);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> googleMapBrowser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        googleMapBrowser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
```
###### \java\seedu\address\ui\HelpPanel.java
``` java
/**
 * The help panel of the App.
 */
public class HelpPanel extends UiPart<Region> {

    private static final String FXML = "HelpPanel.fxml";

    public HelpPanel() {
        super(FXML);
    }

}
```
###### \java\seedu\address\ui\HomePanel.java
``` java
/**
 * The home panel of the App.
 */
public class HomePanel extends UiPart<Region> {

    private static final String FXML = "HomePanel.fxml";

    @FXML
    private Label totalPersonsAndTags;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public HomePanel(ReadOnlyAddressBook readOnlyAddressBook) {
        super(FXML);
        setData(readOnlyAddressBook.getPersonList().size(), readOnlyAddressBook.getTagList().size());

        registerAsAnEventHandler(this);
    }

    private void setData(int totalPersons, int totalTags) {
        this.totalPersonsAndTags.setText("You have " + totalPersons + " friends and " + totalTags + " tags");
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info("Home panel new status: " + abce.data.getPersonList().size() + " persons and "
                + abce.data.getTagList().size());
        setData(abce.data.getPersonList().size(), abce.data.getTagList().size());
    }
}
```
###### \java\seedu\address\ui\InstagramBrowserPanel.java
``` java
/**
 * The Instagram browser panel of the App.
 */
public class InstagramBrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String INSTAGRAM_SEARCH_URL_PREFIX = "https://www.instagram.com/";

    private static final String FXML = "InstagramBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView instagramBrowser;

    public InstagramBrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(INSTAGRAM_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll("\\s+", ""));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> instagramBrowser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        instagramBrowser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        /** At start, Initalise all dynamic information panels for MainWindowHandle
         * to be able to initalise these panel in its respective handles for testing. */
        personInformationPanel = new PersonInformationPanel();
        informationPanelPlaceholder.getChildren().add(personInformationPanel.getRoot());

        helpPanel = new HelpPanel();
        informationPanelPlaceholder.getChildren().add(helpPanel.getRoot());

        homePanel = new HomePanel(logic.getAddressBook());
        informationPanelPlaceholder.getChildren().add(homePanel.getRoot());
    }

    /** Changes the information panel based on request event. */
    public void changeInformationPanel(ChangeInformationPanelRequestEvent event) {
        if (event.getPanelRequestEvent().equals(currentInformationPanel)) {
            return; // Short circuit if the current information panel is the same as the requested information panel
        } else {
            informationPanelPlaceholder.getChildren().removeAll(homePanel.getRoot(), personInformationPanel.getRoot(),
                    helpPanel.getRoot());

            if (event.getPanelRequestEvent().equals(PERSON_INFORMATION_PANEL)) {
                informationPanelPlaceholder.getChildren().add(personInformationPanel.getRoot());
            } else if (event.getPanelRequestEvent().equals((HOME_PANEL))) {
                informationPanelPlaceholder.getChildren().add(homePanel.getRoot());
            } else if (event.getPanelRequestEvent().equals((HELP_PANEL))) {
                informationPanelPlaceholder.getChildren().add(helpPanel.getRoot());
            }
        }

        currentInformationPanel = event.getPanelRequestEvent();
    }

    /**
     * Changes the stylesheet used by UI when change theme command is executed.
     */
    public void changeTheme() {
        String brightThemePath = MainApp.class.getResource(FXML_FILE_FOLDER + "BrightTheme.css").toString();
        String darkThemePath = MainApp.class.getResource(FXML_FILE_FOLDER + "DarkTheme.css").toString();
        String extensionsPath = MainApp.class.getResource(FXML_FILE_FOLDER + "Extensions.css").toString();

        String brightThemeAllPaths = "[" + extensionsPath + ", " + brightThemePath + "]";

        if (getRoot().getStylesheets().toString().equals(brightThemeAllPaths)) {
            getRoot().getStylesheets().remove(brightThemePath);
            getRoot().getStylesheets().add(darkThemePath);
        } else {
            getRoot().getStylesheets().remove(darkThemePath);
            getRoot().getStylesheets().add(brightThemePath);
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the home panel.
     */
    @FXML
    public void handleHome() {
        changeInformationPanel(new ChangeInformationPanelRequestEvent(HOME_PANEL));
    }

    /**
     * Opens the help panel.
     */
    @FXML
    public void handleHelp() {
        changeInformationPanel(new ChangeInformationPanelRequestEvent(HELP_PANEL));
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    void releaseResources() {
        personInformationPanel.releaseResources();
    }
```
###### \java\seedu\address\ui\PersonDetailsPanel.java
``` java
/**
 * An UI component that displays details of a {@code Person} upon selection.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label birthday;
    @FXML
    private Label address;
    @FXML
    private Label postalCode;
    @FXML
    private Label email;

    public PersonDetailsPanel() {
        super(FXML);
        name.setText("");
        phone.setText("");
        birthday.setText("");
        address.setText("");
        postalCode.setText("");
        email.setText("");

        registerAsAnEventHandler(this);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void setLabel(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        postalCode.textProperty().bind(Bindings.convert(person.postalCodeProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setLabel(event.getNewSelection().person);
    }
}

```
###### \java\seedu\address\ui\PersonInformationPanel.java
``` java
/**
 * An UI component that displays information - details, Instagram and
 * google map of a {@code Person} upon selection.
 */
public class PersonInformationPanel extends UiPart<Region> {
    private static final String FXML = "PersonInformationPanel.fxml";

    private InstagramBrowserPanel instagramBrowserPanel;
    private GoogleMapBrowserPanel googleMapBrowserPanel;

    @FXML
    private AnchorPane personDetailsPlaceholder;

    @FXML
    private StackPane instagramBrowserPlaceholder;

    @FXML
    private StackPane googleMapBrowserPaceholder;

    public PersonInformationPanel() {
        super(FXML);
        PersonDetailsPanel personDetailsPanel = new PersonDetailsPanel();
        personDetailsPlaceholder.getChildren().add(personDetailsPanel.getRoot());

        instagramBrowserPanel = new InstagramBrowserPanel();
        instagramBrowserPlaceholder.getChildren().add(instagramBrowserPanel.getRoot());

        googleMapBrowserPanel = new GoogleMapBrowserPanel();
        googleMapBrowserPaceholder.getChildren().add(googleMapBrowserPanel.getRoot());
    }

    /**
     * Release the resources used in the web browsers
     */
    public void releaseResources() {
        instagramBrowserPanel.freeResources();
        googleMapBrowserPanel.freeResources();
    }
}

```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Override
    public void changeInformationPanel(ChangeInformationPanelRequestEvent event) {
        mainWindow.changeInformationPanel(event);
    }

    @Override
    public void changeTheme() {
        mainWindow.changeTheme();
    }
```
###### \resources\view\BrightTheme.css
``` css
#welcomeHeaderText {
    -fx-fill: black;
}

#welcomeContentText {
    -fx-fill: #818181;
}

#homePanel {
    -fx-background-color: #DDD9D7;
}

#homePanel .label {
    -fx-background-color: #578564;
    -fx-background-radius: 10 10 10 10;
    -fx-font-size: 15pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

#helpPanel {
    -fx-background-color: #CECECE;
}

#helpPanel #header {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: black;
}

#helpPanel .label {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

#bevyAssistant {
    -fx-fill: black;
}
```
###### \resources\view\DarkTheme.css
``` css
#welcomeHeaderText {
    -fx-fill: white;
}

#welcomeContentText {
    -fx-fill: #D6D6D6;
}

#homePanel {
    -fx-background-color: #464646;
}

#homePanel .label {
    -fx-background-color: #3e7b91;
    -fx-background-radius: 10 10 10 10;
    -fx-font-size: 15pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

#helpPanel {
    -fx-background-color: #464646;
}

#helpPanel #header {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
}

#helpPanel .label {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

#bevyAssistant {
    -fx-fill: white;
}
```
###### \resources\view\GoogleMapBrowserPanel.fxml
``` fxml
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.web.WebView?>

<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="googleMapBrowser" prefHeight="650.0" />
</StackPane>
```
###### \resources\view\HomePanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<StackPane fx:id="homePanel" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <VBox alignment="CENTER">
      <children>
         <Text fx:id="welcomeHeaderText" fill="#464646" strokeType="OUTSIDE" strokeWidth="0.0" text="Welcome to Bevy.">
            <font>
               <Font name="Segoe UI Semibold" size="56.0" />
            </font>
         </Text>
         <Text fx:id="welcomeContentText" fill="#464646" strokeType="OUTSIDE" strokeWidth="0.0" text="To begin, type on the Command Line Interface" textAlignment="CENTER">
            <font>
               <Font name="Segoe UI Semibold" size="18.0" />
            </font>
         </Text>
         <Text fx:id="welcomeContentText" fill="#464646" strokeType="OUTSIDE" strokeWidth="0.0" text="Not sure what to do? Type help to find out more!">
            <font>
               <Font name="Segoe UI Semibold" size="18.0" />
            </font>
         </Text>
         <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0">
            <children>
               <Label fx:id="totalPersonsAndTags">
                  <padding>
                     <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
                  </padding>
               </Label>
            </children>
         </HBox>
      </children>
   </VBox>
</StackPane>
```
###### \resources\view\InstagramBrowserPanel.fxml
``` fxml
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.web.WebView?>

<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="instagramBrowser" prefHeight="650.0" />
</StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml

<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Menu?>
<?import javafx.scene.control.MenuBar?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">

  <MenuBar fx:id="menuBar" nodeOrientation="LEFT_TO_RIGHT" VBox.vgrow="NEVER">
    <Menu mnemonicParsing="false" text="Bevy">
         <MenuItem fx:id="homeMenuItem" mnemonicParsing="false" onAction="#handleHome" text="Home" />
      <MenuItem fx:id="exitMenuItem" mnemonicParsing="false" onAction="#handleExit" text="Exit" />
    </Menu>
      <Menu mnemonicParsing="false" text="Statistics">
         <items>
            <MenuItem mnemonicParsing="false" text="Birthday" />
            <MenuItem mnemonicParsing="false" text="Tags" />
         </items>
      </Menu>
    <Menu mnemonicParsing="false" text="Help">
      <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
    </Menu>
  </MenuBar>

  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.5" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList" maxWidth="350.0" minWidth="300.0" prefWidth="300.0" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="personListPanelPlaceholder" focusTraversable="true" VBox.vgrow="ALWAYS" />
    </VBox>
      <VBox SplitPane.resizableWithParent="false">
         <children>
           <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border">
             <padding>
               <Insets bottom="5" left="10" right="10" top="5" />
             </padding>
           </StackPane>
           <StackPane fx:id="resultDisplayPlaceholder" maxHeight="90.0" minHeight="90.0" prefHeight="90.0" styleClass="pane-with-border">
             <padding>
               <Insets bottom="5" left="10" right="10" top="5" />
             </padding>
           </StackPane>
            <StackPane fx:id="informationPanelPlaceholder" prefHeight="150.0" prefWidth="200.0" VBox.vgrow="ALWAYS">
               <padding>
                  <Insets bottom="10.0" left="2.0" right="10.0" top="5.0" />
               </padding></StackPane>
         </children>
      </VBox>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" />
   <stylesheets>
      <URL value="@Extensions.css" />
      <URL value="@BrightTheme.css" />
   </stylesheets>
</VBox>
```
###### \resources\view\PersonDetailsPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>

<AnchorPane fx:id="personDetailsPanel" styleClass="anchor-pane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane layoutX="94.0" layoutY="45.0" minHeight="100.0" minWidth="400.0" prefHeight="148.0" prefWidth="432.0" styleClass="personGrid" AnchorPane.leftAnchor="20.0" AnchorPane.rightAnchor="10.0" AnchorPane.topAnchor="60.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="284.0" minWidth="10.0" prefWidth="119.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="410.0" minWidth="10.0" prefWidth="313.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints maxHeight="30.0" minHeight="10.0" prefHeight="25.0" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="30.0" minHeight="10.0" prefHeight="25.0" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="30.0" minHeight="10.0" prefHeight="25.0" vgrow="SOMETIMES" />
            <RowConstraints maxHeight="30.0" minHeight="10.0" prefHeight="25.0" vgrow="SOMETIMES" />
            <RowConstraints maxHeight="30.0" prefHeight="25.0" />
        </rowConstraints>
         <children>
            <Label text="Phone" />
            <Label text="Birthday" GridPane.rowIndex="1" />
            <Label text="Address" GridPane.rowIndex="3" />
            <Label text="Postal Code" GridPane.rowIndex="4" />
            <Label fx:id="phone" text="Label" GridPane.columnIndex="1" />
            <Label fx:id="birthday" text="Label" GridPane.columnIndex="1" GridPane.rowIndex="1" />
            <Label fx:id="address" text="Label" GridPane.columnIndex="1" GridPane.rowIndex="3" />
            <Label text="Email" GridPane.rowIndex="2" />
            <Label fx:id="email" text="Label" GridPane.columnIndex="1" GridPane.rowIndex="2" />
            <Label fx:id="postalCode" text="Label" GridPane.columnIndex="1" GridPane.rowIndex="4" />
         </children>
      </GridPane>
      <Label fx:id="name" layoutX="20.0" layoutY="14.0" styleClass="personLabel" text="Label">
         <padding>
            <Insets bottom="10.0" />
         </padding></Label>
   </children>
</AnchorPane>
```
###### \resources\view\PersonInformationPanel.fxml
``` fxml
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.StackPane?>

<SplitPane fx:id="personInformationPanel" dividerPositions="0.5" orientation="VERTICAL" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <items>
      <AnchorPane id="personInformationPanel" fx:id="personDetailsPlaceholder" maxHeight="220.0" minHeight="220.0" prefHeight="220.0" />
      <SplitPane dividerPositions="0.5">
         <items>
            <StackPane fx:id="instagramBrowserPlaceholder" maxWidth="350.0" minHeight="300.0" minWidth="300.0" prefWidth="300.0" />
            <StackPane fx:id="googleMapBrowserPaceholder" minWidth="400.0" prefWidth="400.0" />
         </items>
      </SplitPane>
   </items>
</SplitPane>
```
