package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTest {

    @Test
    public void isValidPhone() {
        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("9188")); // less than 6 numbers
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("9011p1")); // alphabets within digits
        assertFalse(Date.isValidDate("931 534")); // spaces within digits

        // valid date
        assertTrue(Date.isValidDate("121287")); // exactly 6 numbers
        assertTrue(Date.isValidDate("140597"));
        assertTrue(Date.isValidDate("57247542979")); // long date numbers
    }
}
