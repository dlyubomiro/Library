package bg.sofia.fmi.mjt.library.utills.logger;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandLoggerTest {
    private static final String TEST_LOG_FILE_PATH = "user_commands-1.log";
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_COMMAND = "testCommand";
    private static final String TEST_ATTRIBUTE = "testAttribute";
    private static final String TEST_VALUE = "testValue";
    private static final String TEST_MESSAGE = "testMessage";
    private static final String DATE_TIME_PATTERN = "HH:mm:ss yyyy-MM-dd";

    @Test
    public void testLogUserCommandWithMessage() {
        CommandLogger.setVersionsCounter(-1);
        CommandLogger.logUserCommand(TEST_MESSAGE);
        assertLogContainsMessage(TEST_MESSAGE);
    }

    @Test
    public void testLogUserCommandWithCommandAndMessage() {
        CommandLogger.setVersionsCounter(-1);
        CommandLogger.logUserCommand(TEST_USERNAME, TEST_COMMAND, TEST_MESSAGE);
        assertLogContainsMessage(TEST_MESSAGE);
    }

    @Test
    public void testLogUserCommandWithCommandAttributeAndValue() {
        CommandLogger.setVersionsCounter(-1);
        CommandLogger.logUserCommand(TEST_USERNAME, TEST_COMMAND, TEST_ATTRIBUTE, TEST_VALUE);
        String expectedLogMessage = getExpectedLogMessage();
        assertLogContainsMessage(expectedLogMessage);
    }

    private void assertLogContainsMessage(String expectedMessage) {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_LOG_FILE_PATH))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains(expectedMessage)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Expected log message not found in log file: " + expectedMessage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read log file: " + e.getMessage());
        }
    }

    private String getExpectedLogMessage() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(formatter);
        return " [" + formattedTime + "] " +
            "User '" + TEST_USERNAME + "' executed command: '" + TEST_COMMAND +
            "' with attribute '" + TEST_ATTRIBUTE + "' and value '" + TEST_VALUE + "'";
    }
}
