package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday("9188")); // less than 6 numbers
        assertFalse(Birthday.isValidBirthday("birthday")); // invalid date
        assertFalse(Birthday.isValidBirthday("9011p1")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("931 534")); // spaces within digits

        // valid birthday
        assertTrue(Birthday.isValidBirthday("12 12 87")); // can enter year using 2 digit
        assertTrue(Birthday.isValidBirthday("14-05-1997")); // accepts '-' as separator
        assertTrue(Birthday.isValidBirthday("08 aug 1985")); // name of month
    }
}
