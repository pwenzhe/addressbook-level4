package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

// @@author johnweikangong
public class CsvFileStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath(
            "./src/test/data/CsvFileStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void saveToCsvFile_allInOrder_success() throws Exception {
        String filePath = TEST_DATA_FOLDER + "ActualBevy.csv";
        AddressBook original = getTypicalAddressBook();
        CsvFileStorage csvFileStorage = new CsvFileStorage(filePath);

        // Save in new file and assert file contents
        csvFileStorage.saveToCsvFile(original, filePath);
        List<String> expectedFileContent = Files.readAllLines(Paths.get(TEST_DATA_FOLDER + "ExpectedBevy1.csv"));
        List<String> actualFileContent = Files.readAllLines(Paths.get(filePath));
        assertEquals(expectedFileContent, actualFileContent);

        // Modify data by adding and removing same person, overwrite existing file, and assert file contents
        List<ReadOnlyPerson> keys = new ArrayList<>();
        keys.add(new Person(HOON));

        original.addPerson(new Person(HOON));
        original.removePersons(keys);
        csvFileStorage.saveToCsvFile(original, filePath);
        actualFileContent = Files.readAllLines(Paths.get(filePath));
        assertEquals(expectedFileContent, actualFileContent);

        // Modify data by adding new person, overwrite existing file, and assert file contents
        original.addPerson(new Person(IDA));
        csvFileStorage.saveToCsvFile(original, filePath);
        expectedFileContent = Files.readAllLines(Paths.get(TEST_DATA_FOLDER + "ExpectedBevy2.csv"));
        actualFileContent = Files.readAllLines(Paths.get(filePath));
        assertEquals(expectedFileContent, actualFileContent);
    }

    @Test
    public void saveToCsvFile_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveToCsvFile(null, "SomeFile.xml");
    }

    @Test
    public void saveToCsvFile_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveToCsvFile(new AddressBook(), null);
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveToCsvFile(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new CsvFileStorage(filePath).saveToCsvFile(addressBook);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the CSV file.", ioe);
        }
    }
}
