package seedu.address.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Stores addressbook data in a Csv file
 */
public class CsvFileStorage implements FileStorage {
    private static final String WORD_SEPARATOR = ", ";

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

            writeLine(writer, Arrays.asList("Name", "Phone", "Birthday", "Email", "Address", "Postal Code",
                    "Favourite", "Tags"), new HashSet<>());

            for (ReadOnlyPerson person : persons) {
                writeLine(writer, Arrays.asList(person.getName().fullName, person.getPhone().value,
                        person.getBirthday().value, person.getEmail().value, person.getAddress().value,
                        person.getPostalCode().value, person.getFavourite().value), person.getTags());
            }

            writer.flush();
            writer.close();
        } catch (IOException ioe) {
            assert false : "Unexpected exception " + ioe.getMessage();
        }
    }

    @Override
    public void writeLine(Writer writer, List<String> personData, Set<Tag> tags) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (String data : personData) {

            if (data.contains(",")) {
               data = data.replace(",", "");
            }

            sb.append(data);
            sb.append(WORD_SEPARATOR);
        }

        tags.forEach(tag -> sb.append(tag + " "));

        sb.append("\n");
        writer.append(sb.toString());
    }
}
