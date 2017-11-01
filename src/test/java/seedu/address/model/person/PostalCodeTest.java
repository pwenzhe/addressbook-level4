package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// @@author johnweikangong
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
