package bg.sofia.fmi.mjt.library.utills.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandLogger {
    private static final String LOG_FILE_PATH_PREFIX = "user_commands";
    private static int versionsCounter;
    private static final String DATE_TIME_PATTERN = "HH:mm:ss yyyy-MM-dd";
    private static final String LOG_MESSAGE_FORMAT = "User '%s' executed command: '%s' ";
    private static final String LOG_MESSAGE_FORMAT_WITH_ATTRIBUTE =
        "User '%s' executed command: '%s' with attribute '%s' and value '%s'";
    private static final String FILE_EXTENSION = ".log";
    private static final String OPEN_BRACKET = " [";
    private static final String CLOSE_BRACKET = "] ";

    public static void logUserCommand(String username, String command, String message) {
        String logMessage = String.format(formatMessage() +
            LOG_MESSAGE_FORMAT + message, username, command);
        writeLog(logMessage);
    }

    public static void setVersionsCounter(int version) {
        versionsCounter = version;
    }

    public static void logUserCommand(String username, String command, String attribute, String value) {
        String logMessage = String.format(formatMessage() + LOG_MESSAGE_FORMAT_WITH_ATTRIBUTE,
            username, command, attribute, value);
        writeLog(logMessage);
    }

    public static void logUserCommand(String message) {
        writeLog(formatMessage() + message);
    }

    private static String formatMessage() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(formatter);
        return OPEN_BRACKET + formattedTime + CLOSE_BRACKET;
    }

    public static void writeLog(String message) {
        try (PrintWriter writer = new PrintWriter(
            new FileWriter(LOG_FILE_PATH_PREFIX + versionsCounter + FILE_EXTENSION, true))) {
            writer.println(message);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write log: " + e.getMessage());
        }
    }
}
