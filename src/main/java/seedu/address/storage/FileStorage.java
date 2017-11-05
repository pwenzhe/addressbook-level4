package seedu.address.storage;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.Tag;

/**
 * Represents a storage for {@link CsvFileStorage}.
 */
public interface FileStorage {

    /**
     * Returns the file path of the CSV data file.
     */
    String getCsvFilePath();

    /**
     * Saves the given {@link CsvFileStorage} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveToCsvFile(ReadOnlyAddressBook addressBook) throws IOException;

    void writeLine(Writer writer, List<String> personData, Set<Tag> tags) throws IOException;
}
