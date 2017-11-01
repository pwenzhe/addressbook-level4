# Valerieyue
###### \main\java\seedu\address\logic\commands\BirthdayStatisticsCommand.java
``` java
/**
 * Shows the birthday statistics.
 */
public class BirthdayStatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";
    public static final String COMMAND_ALIAS = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the birthday statistics.\n"
            + "Example: " + COMMAND_WORD;
    public static final String BIRTHDAY_STATISTICS_PANEL_REQUEST_EVENT = "BirthdayStatisticsPanel";

    public static final String MESSAGE_SUCCESS = "Birthday statistics displayed";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance()
                .post(new ChangeInformationPanelRequestEvent(BIRTHDAY_STATISTICS_PANEL_REQUEST_EVENT));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

```
###### \main\java\seedu\address\logic\parser\AddCommandParser.java
``` java
    private static Optional<String> areValuePresent(Optional<String> checkPresent) {
        return Optional.of(checkPresent.orElse(""));
    }
```
###### \main\java\seedu\address\logic\parser\AddCommandParser.java
``` java

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \main\java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday should be in the format DDMMYY, separated by '/' or '-' or '.' or ' '. \n"
                   + "The month can be entered as a number or name of the month."
                   + "The date entered must be valid.\n"
                   + "eg. b/09 8 1987, b/23-Mar-98";
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.| )(?:0?[13578]|1[02]|"
           + "(?:jan|mar|may|jul|aug|oct|dec)))\\1|(?:(?:29|30)(\\/|-|\\.| )(?:0?[1,3-9]|1[0-2]|"
           + "(?:jan|mar|apr|may|jun|jul|aug|sep|oct|nov|dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.| )"
           + "(?:0?2|(?:feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
           + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.| )(?:(?:0?[1-9]|"
           + "(?:jan|feb|mar|apr|may|jun|jul|aug|sep))|(?:1[0-2]|(?:oct|nov|dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim().toLowerCase();
        String temp = trimmedBirthday;
        if (!trimmedBirthday.isEmpty() & !isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        } else if (!"".equals(trimmedBirthday)) {
            String[] array = trimmedBirthday.replace("/", " ").replace("-", " ")
                    .replace(".", " ").split(" ");
            int monthInt = 0;
            String day;
            String month;
            String year;

            // day
            if (array[0].length() == 2) {
                if (array[0].startsWith("0")) {
                    day = array[0].substring(1); //0X
                } else {
                    day = array[0]; // XX
                }
            } else {
                day = array[0]; // X
            }

            // year
            if (array[2].length() == 2) {
                if (Integer.valueOf(array[2]) < 18) {
                    year = "20" + array[2];
                } else {
                    year = "19" + array[2];
                }
            } else {
                year = array[2];
            }

            // month
            if (array[1].length() < 3) {
                monthInt = Integer.valueOf(array[1]);
            }

            switch (monthInt) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
            default:
                month = "";
                break;
            }
            if (array[1].length() == 3) {
                month = array[1].substring(0, 1).toUpperCase() + array[1].substring(1);
            }
            temp = day + " " + month + " " + year;
        }
        this.value = temp;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

```
###### \main\java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts person name in alphabetical order
     */
    public void sort() {
        Comparator<Person> comparator = (p1, p2) -> (comparePeople(p1, p2));
        Collections.sort(internalList, comparator);
    }
```
###### \main\java\seedu\address\model\person\UniquePersonList.java
``` java

    /**
     * Compare Persons by favourite status and then name
     */
    public int comparePeople(Person p1, Person p2) {
        int compare;
        String p1Fav = "";
        String p2Fav = "";
        if (p1.getFavourite().getFavourite()) {
            p1Fav = "1";
        } else {
            p1Fav = "2";
        }
        if (p2.getFavourite().getFavourite()) {
            p2Fav = "1";
        } else {
            p2Fav = "2";
        }
        compare = p1Fav.compareTo(p2Fav);
        if (compare == 0) {
            compare = p1.getName().toString().compareTo(p2.getName().toString());
        }

        return compare;
    }


    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \main\java\seedu\address\ui\BirthdayStatisticsPanel.java
``` java
/**
 * The birthday statistics panel of the App.
 */
public class BirthdayStatisticsPanel extends UiPart<Region> {

    private static final String FXML = "BirthdayStatisticsPanel.fxml";

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    public BirthdayStatisticsPanel(ReadOnlyAddressBook readOnlyAddressBook) {
        super(FXML);

        // Get an array with the English month names.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getShortMonths();
        logger.info("Month: " + Arrays.toString(months));
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
        setPersonData(readOnlyAddressBook);
        barChart.setCategoryGap(10);
        barChart.setTitle("Birthday Statistics");
        xAxis.setLabel("Month");
        registerAsAnEventHandler(this);
    }

    private void setPersonData(ReadOnlyAddressBook readOnlyAddressBook) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        // Count the number of people having their birthday in a specific month.
        int[] monthCounter = new int[12];
        int monthInt;
        for (ReadOnlyPerson p : readOnlyAddressBook.getPersonList()) {
            String month = p.getBirthday().toString().replaceAll("[^a-zA-Z]+", "");
            switch(month) {
            case "Jan":
                monthInt = 0;
                break;
            case "Feb":
                monthInt = 1;
                break;
            case "Mar":
                monthInt = 2;
                break;
            case "Apr":
                monthInt = 3;
                break;
            case "May":
                monthInt = 4;
                break;
            case "Jun":
                monthInt = 5;
                break;
            case "Jul":
                monthInt = 6;
                break;
            case "Aug":
                monthInt = 7;
                break;
            case "Sep":
                monthInt = 8;
                break;
            case "Oct":
                monthInt = 9;
                break;
            case "Nov":
                monthInt = 10;
                break;
            case "Dec":
                monthInt = 11;
                break;
            default:
                monthInt = 0;
                break;
            }
            monthCounter[monthInt]++;
        }

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }
        barChart.getData().clear();
        barChart.getData().add(series);

    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info("Birthday statistics updated");
        setPersonData(abce.data);
    }
}
```
###### \main\java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the birthday statistics panel.
     */
    @FXML
    public void handleBirthdayStatistics() {
        changeInformationPanel(new ChangeInformationPanelRequestEvent(BIRTHDAY_STATISTICS_PANEL));
    }
```
###### \main\java\seedu\address\ui\MainWindow.java
``` java

    /**
     * Opens the help panel.
     */
    @FXML
    public void handleHelp() {
        changeInformationPanel(new ChangeInformationPanelRequestEvent(HELP_PANEL));
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    public void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    void releaseResources() {
        personInformationPanel.releaseResources();
    }
}
```
###### \test\java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // Empty string
        assertFalse(Birthday.isValidBirthday("9188")); // Less than 6 numbers
        assertFalse(Birthday.isValidBirthday("birthday")); // Invalid date
        assertFalse(Birthday.isValidBirthday("9011p1")); // Alphabets within digits
        assertFalse(Birthday.isValidBirthday("931 534")); // Spaces within digits

        // valid birthday
        assertTrue(Birthday.isValidBirthday("12 12 87")); // Can enter year using 2 digit
        assertTrue(Birthday.isValidBirthday("14-05-1997")); // Accepts '-' as separator
        assertTrue(Birthday.isValidBirthday("08 aug 1985")); // Name of month
    }
}
```
