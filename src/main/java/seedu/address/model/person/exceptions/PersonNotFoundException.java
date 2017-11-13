package seedu.address.model.person.exceptions;

// @@author pwenzhe
/**
 * Signals that the operation is unable to find the specified person.
 */
public class PersonNotFoundException extends Exception {
    public PersonNotFoundException() {
        super("Operation could not find the specified person");
    }
}
