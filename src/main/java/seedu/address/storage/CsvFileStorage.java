package seedu.address.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Stores addressbook data in a CSV file
 */
public class CsvFileStorage implements FileStorage {
    private static final char WORD_SEPARATOR = ',';

    private static String filePath;

    public CsvFileStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getCsvFilePath() {
        return filePath;
    }

    /**
     * Saves the given addressbook data to the specified file.
     */
    @Override
    public void saveToCsvFile(ReadOnlyAddressBook addressBook)
            throws IOException {
        try {
            FileWriter writer = new FileWriter(filePath);
            List<ReadOnlyPerson> persons = addressBook.getPersonList();

            for (ReadOnlyPerson person : persons) {
                writeLine(writer, Arrays.asList(person.getName().fullName, person.getPhone().value,
                        person.getBirthday().value, person.getEmail().value, person.getAddress().value,
                        person.getPostalCode().value));
            }

            writer.flush();
            writer.close();
        } catch (IOException ioe) {
            assert false : "Unexpected exception " + ioe.getMessage();
        }
    }

    @Override
    public void writeLine(Writer writer, List<String> values) throws IOException {
        boolean isFirstWord = true;
        StringBuilder sb = new StringBuilder();

        for (String value : values) {
            if (!isFirstWord) {
                sb.append(WORD_SEPARATOR);
            }

            sb.append(value);
            isFirstWord = false;
        }

        sb.append("\n");
        writer.append(sb.toString());
    }
}
