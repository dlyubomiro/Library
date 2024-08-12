package bg.sofia.fmi.mjt.library.commands.guest;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogInCommandTest {
    @Test
    public void testLogInCommandSuccessful() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password123");
        Command command = new LogInCommand(profile, "username", "password123");

        command.execute();

        assertTrue(profile.isLoggedIn());
    }

    @Test
    public void testLogInCommandUnsuccessfulIncorrectPassword() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password123");
        Command command = new LogInCommand(profile, "username", "password555");

        try {
            command.execute();
        } catch (LibraryException e) {
            assertEquals("Invalid username or password!", e.getMessage());
        }
    }

    @Test
    public void testLogInCommandUnsuccessfulIncorrectUsername() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password123");
        Command command = new LogInCommand(profile, "user", "password123");

        try {
            command.execute();
        } catch (LibraryException e) {
            assertEquals("Invalid username or password!", e.getMessage());
        }
    }

    @Test
    public void testLogInCommandUnsuccessfulIncorrectUsernameLength() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password123");
        Command command = new LogInCommand(profile, "us", "password");

        try {
            command.execute();
        } catch (LibraryException e) {
            assertEquals("Invalid username or password!", e.getMessage());
        }
    }

    @Test
    public void testLogInCommandUnsuccessfulIncorrectPasswordLength() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password123");
        Command command = new LogInCommand(profile, "username", "pa");

        try {
            command.execute();
        } catch (LibraryException e) {
            assertEquals("Invalid username or password!", e.getMessage());
        }
    }
}
