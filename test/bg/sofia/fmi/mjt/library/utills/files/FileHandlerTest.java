package bg.sofia.fmi.mjt.library.utills.files;

import bg.sofia.fmi.mjt.library.utills.filehandler.FileHandler;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    private static final String TEST_USERNAME = "rubi";
    private static final String TEST_PASSWORD = "rubipudela";
    private static final String TEST_FULL_NAME = "Rubi Dubi";
    private static final int TEST_AGE = 13;
    private ReaderProfile testProfile;
    private Map<String, ReaderProfile> readerProfiles;
    private FileHandler fileHandler;

    @BeforeEach
    public void setUp() {
        testProfile = new ReaderProfile(TEST_FULL_NAME, TEST_AGE, TEST_USERNAME, TEST_PASSWORD);
        readerProfiles = new HashMap<>();
        fileHandler = new FileHandler();
    }

    @Test
    public void testSaveAndLoadUserToFile() throws IOException {
        fileHandler.saveUserToFile(testProfile);
        FileHandler.loadUsers(readerProfiles);
        assertTrue(readerProfiles.containsKey(TEST_USERNAME));
        assertEquals(testProfile, readerProfiles.get(TEST_USERNAME));
    }
}
