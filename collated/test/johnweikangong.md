# johnweikangong
###### \java\seedu\address\logic\commands\HomeCommandTest.java
``` java
public class HomeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new HomeCommand().execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeInformationPanelRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\model\person\PostalCodeTest.java
``` java
public class PostalCodeTest {

    @Test
    public void isValidAddress() {
        // invalid postal codes
        assertFalse(PostalCode.isValidPostalCode("@^&")); // special characters
        assertFalse(PostalCode.isValidPostalCode("12345678")); // more than 6 digits long
        assertFalse(PostalCode.isValidPostalCode("12345")); // less than 6 digits long
        assertFalse(PostalCode.isValidPostalCode("915920")); // more than 800000

        // valid postal code
        assertTrue(Address.isValidAddress("000000")); // lower bound of postal code range
        assertTrue(Address.isValidAddress("450920"));
        assertTrue(Address.isValidAddress("800000")); // upper bound of postal code range
    }
}
```
###### \java\seedu\address\ui\GoogleMapBrowserPanelTest.java
``` java
public class GoogleMapBrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private GoogleMapBrowserPanel googleMapBrowserPanel;
    private GoogleMapBrowserPanelHandle googleMapBrowserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> googleMapBrowserPanel = new GoogleMapBrowserPanel());
        uiPartRule.setUiPart(googleMapBrowserPanel);

        googleMapBrowserPanelHandle = new GoogleMapBrowserPanelHandle(googleMapBrowserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, googleMapBrowserPanelHandle.getLoadedUrl());

        String []segment = ALICE.getAddress().value.split("#");

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLEMAP_SEARCH_URL_PREFIX
                + segment[0].replaceAll(" ", "+") + GOOGLEMAP_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(googleMapBrowserPanelHandle);
        assertEquals(expectedPersonUrl, googleMapBrowserPanelHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\InstagramBrowserPanelTest.java
``` java
public class InstagramBrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private InstagramBrowserPanel instagramBrowserPanel;
    private InstagramBrowserPanelHandle instagramBrowserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> instagramBrowserPanel = new InstagramBrowserPanel());
        uiPartRule.setUiPart(instagramBrowserPanel);

        instagramBrowserPanelHandle = new InstagramBrowserPanelHandle(instagramBrowserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, instagramBrowserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(INSTAGRAM_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll("\\s+", "") + "/");

        waitUntilBrowserLoaded(instagramBrowserPanelHandle);
        assertEquals(expectedPersonUrl, instagramBrowserPanelHandle.getLoadedUrl());
    }
}
```
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
public class ChangeInformationPanelSystemTest extends AddressBookSystemTest {

    @Test
    public void changeInformationPanel() {
        final String homePanelId = "[StackPane[id=homePanel]]";
        final String personInformationPanelId = "[SplitPane[id=personInformationPanel, styleClass=split-pane]]";
        final String helpPanelId = "[StackPane[id=helpPanel]]";

        assertHandleSuccess("helpPanel", helpPanelId, "");

        assertHandleSuccess("homePanel", homePanelId, "");

        /* Case: Changes information panel of address book using select command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to person information panel
         */
        String selectCommand = SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        assertCommandSuccess(selectCommand, personInformationPanelId, INDEX_FIRST_PERSON);

        /* Case: Change information panel of address book using help command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to help panel
         */
        assertCommandSuccess(HelpCommand.COMMAND_WORD, helpPanelId, HelpCommand.MESSAGE_SUCCESS);

        /* Case: Change information panel of address book using home command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to home panel
        */
        assertCommandSuccess(HomeCommand.COMMAND_WORD, homePanelId, HomeCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using select command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to person information panel
        */
        selectCommand = SelectCommand.COMMAND_WORD + " " + INDEX_SECOND_PERSON.getOneBased();
        assertCommandSuccess(selectCommand, personInformationPanelId, INDEX_SECOND_PERSON);

        /* Case: Changes information panel of address book using help command word, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to help panel
        */
        assertCommandSuccess("   " + HelpCommand.COMMAND_WORD + " 232##$$% ",
                helpPanelId, HelpCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using home command word, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to home panel
        */
        assertCommandSuccess("       " + HomeCommand.COMMAND_WORD + "  $#%@   ",
                homePanelId, HomeCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using select command word, with leading spaces
         * and trailing spaces -> information panel changed to person information panel
        */
        selectCommand = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " ";
        assertCommandSuccess(selectCommand, personInformationPanelId, INDEX_FIRST_PERSON);

        /* Case: Changes information panel of address book using help command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to help panel
        */
        assertCommandSuccess(HelpCommand.COMMAND_ALIAS, helpPanelId, HelpCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using home command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to home panel
        */
        assertCommandSuccess(HomeCommand.COMMAND_ALIAS, homePanelId, HomeCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using help command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to help panel
        */
        assertCommandSuccess("      " + HelpCommand.COMMAND_ALIAS + " 2##$$% ",
                helpPanelId, HelpCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using home command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to home panel
        */
        assertCommandSuccess("  " + HomeCommand.COMMAND_WORD + "  $#%323@21   ",
                homePanelId, HomeCommand.MESSAGE_SUCCESS);

        /* Case: Mixed case home command word -> rejected */
        assertCommandFailure("HoME", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case home command alias -> rejected */
        assertCommandFailure("hO", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case help command word -> rejected */
        assertCommandFailure("hElP", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case help command alias -> rejected */
        assertCommandFailure("sOs", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to the initial model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedInformationPanelId,
                                      Index expectedSelectedCardIndex) {
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        assertCommandSuccess(command, expectedInformationPanelId, expectedResultMessage, getModel());
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the information panel ID
     * displays the correct panel, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to the initial model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedInformationPanelId, String expectedResultMessage) {
        assertCommandSuccess(command, expectedInformationPanelId, expectedResultMessage, getModel());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the
     * information panel displays {@code expectedInformationPanel}, result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ChangeInformationPanelSystemTest#assertCommandSuccess(String, String, String)
     */
    private void assertCommandSuccess(String command, String expectedInformationPanelId,
                                      String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertInformationPanelShowsCorrectPanel(expectedInformationPanelId);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code handle} and verifies that the command box displays an empty string, the information panel ID
     * displays the correct panel, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to the initial model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertHandleSuccess(String handle, String expectedInformationPanelId,
                                     String expectedResultMessage) {
        assertHandleSuccess(handle, expectedInformationPanelId, expectedResultMessage, getModel());
    }

    /**
     * Performs the same verification as {@code assertHandleSuccess(String, String, String)} except that the
     * information panel displays {@code expectedInformationPanel}, result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ChangeInformationPanelSystemTest#assertHandleSuccess(String, String, String, Model)
     */
    private void assertHandleSuccess(String handle, String expectedInformationPanelId,
                                     String expectedResultMessage, Model expectedModel) {
        executeHandle(handle);
        assertInformationPanelShowsCorrectPanel(expectedInformationPanelId);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\ChangeThemeSystemTest.java
``` java
public class ChangeThemeSystemTest extends AddressBookSystemTest {

    @Test
    public void changeTheme() {
        String brightThemePath = TestApp.class.getResource(FXML_FILE_FOLDER + "BrightTheme.css").toString();
        String darkThemePath = TestApp.class.getResource(FXML_FILE_FOLDER + "DarkTheme.css").toString();
        String extensionsPath = TestApp.class.getResource(FXML_FILE_FOLDER + "Extensions.css").toString();

        final String expectedBrightThemeAllPaths = "[" + extensionsPath + ", " + brightThemePath + "]";
        final String expectedDarkThemeAllPaths = "[" + extensionsPath + ", " + darkThemePath + "]";

        /* Case: Change theme of non-empty address book using command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to dark theme
         */
        assertCommandSuccess(ThemeCommand.COMMAND_WORD, expectedDarkThemeAllPaths);

        /* Case: Change theme of non-empty address book using command word, with leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to bright theme
         */
        assertCommandSuccess("   " + ThemeCommand.COMMAND_WORD + " 434@$   ", expectedBrightThemeAllPaths);

        /* Case: Change theme of non-empty address book using command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to dark theme
         */
        assertCommandSuccess(ThemeCommand.COMMAND_ALIAS, expectedDarkThemeAllPaths);

        /* Case: Change theme of non-empty address book using command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to bright theme
         */
        assertCommandSuccess("  " + ThemeCommand.COMMAND_ALIAS + " 2#2 ", expectedBrightThemeAllPaths);

        /* Case: Mixed case command word -> rejected */
        assertCommandFailure("ChaNgEtHeme", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case command alias -> rejected */
        assertCommandFailure("Ct", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedThemeAllPaths) {
        assertCommandSuccess(command, expectedThemeAllPaths, ThemeCommand.MESSAGE_SUCCESS, getModel());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ChangeThemeSystemTest#assertCommandSuccess(String, String)
     */
    private void assertCommandSuccess(String command, String expectedThemeAllPaths,
                                      String expectedResultMessage, Model expectedModel) {
        assertThemeBeforeChangingNotSame(expectedThemeAllPaths);
        executeCommand(command);
        assertThemeAfterChangingSame(expectedThemeAllPaths);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first person in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        List<ReadOnlyPerson> deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON);
        HashSet<Integer> zeroBasedIndexes = new HashSet<>();
        zeroBasedIndexes.add(INDEX_FIRST_PERSON.getZeroBased());

        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, deletedPerson));
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        deletedPerson.clear();
        zeroBasedIndexes.clear();

        /* Case: delete multiple person in the list, without overlapping range -> deleted */
        Model modelBeforeDeletingMultiple = getModel();
        command = DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_SECOND_PERSON.getOneBased() + " " + INDEX_THIRD_PERSON.getOneBased() + "-"
                + INDEX_FORTH_PERSON.getOneBased();
        deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON, INDEX_FORTH_PERSON);
        zeroBasedIndexes.add(INDEX_FIRST_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_SECOND_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_THIRD_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_FORTH_PERSON.getZeroBased());

        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, deletedPerson));
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        deletedPerson.clear();
        zeroBasedIndexes.clear();

        /* Case: undo deleting multiple persons in the list, without overlap -> multiple persons restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingMultiple, expectedResultMessage);

        /* Case: delete multiple person in the list, with overlapping range -> deleted */
        expectedModel = getModel();
        command = DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_SECOND_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + "-"
                + INDEX_THIRD_PERSON.getOneBased();
        deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        zeroBasedIndexes.add(INDEX_FIRST_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_SECOND_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_THIRD_PERSON.getZeroBased());

        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, deletedPerson));
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        deletedPerson.clear();
        zeroBasedIndexes.clear();

        /* Case: undo deleting multiple persons in the list, with overlap -> multiple persons restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingMultiple, expectedResultMessage);

        /* Case: delete the last person in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePerson(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle person in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, delete index within bounds of address book and person list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered person list, delete index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a person card is selected ------------------------ */

        /* Case: delete the selected person -> person list panel selects the person before the deleted person */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedPerson = removePerson(expectedModel, selectedIndex);
        zeroBasedIndexes.add(selectedIndex.getZeroBased());

        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, deletedPerson));
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);
        deletedPerson.clear();
        zeroBasedIndexes.clear();

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }
```
