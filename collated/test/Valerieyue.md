# Valerieyue
###### \java\seedu\address\model\person\BirthdayTest.java
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
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
        /* Case: Change information panel of address book using birthday statistics command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to birthday statistics panel.
        */
        assertCommandSuccess(BirthdayStatisticsCommand.COMMAND_WORD, birthdayStatisticsPanelId,
                BirthdayStatisticsCommand.MESSAGE_SUCCESS);

        /* Case: Change information panel of address book using tag statistics command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to tag statistics panel.
        */
        assertCommandSuccess(TagStatisticsCommand.COMMAND_WORD, tagStatisticsPanelId,
                TagStatisticsCommand.MESSAGE_SUCCESS);
```
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
        /* Case: Changes information panel of address book using birthday statistics command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to birthday statistics panel.
        */
        assertCommandSuccess(BirthdayStatisticsCommand.COMMAND_ALIAS, birthdayStatisticsPanelId,
                BirthdayStatisticsCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using tag statistics command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to tag statistics panel.
        */
        assertCommandSuccess(TagStatisticsCommand.COMMAND_ALIAS, tagStatisticsPanelId,
                TagStatisticsCommand.MESSAGE_SUCCESS);
```
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
        /* Case: Changes information panel of address book using birthday statistics command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to birthday statistics panel.
        */
        assertCommandSuccess("  " + BirthdayStatisticsCommand.COMMAND_WORD + "  $#%543@$   ",
                birthdayStatisticsPanelId, BirthdayStatisticsCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using tag statistics command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to tag statistics panel.
        */
        assertCommandSuccess("  " + TagStatisticsCommand.COMMAND_WORD + "  $#%543@$   ",
                tagStatisticsPanelId, TagStatisticsCommand.MESSAGE_SUCCESS);
```
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
        /* Case: Mixed case birthday statistics command word -> rejected. */
        assertCommandFailure("birTHDaystaTIStics", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case birthday statistics command alias -> rejected. */
        assertCommandFailure("bstATs", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case tag statistics command word -> rejected. */
        assertCommandFailure("tagstaTIStics", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case tag statistics command alias -> rejected. */
        assertCommandFailure("tstATs", MESSAGE_UNKNOWN_COMMAND);
```
