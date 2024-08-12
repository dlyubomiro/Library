package bg.sofia.fmi.mjt.library.commands.guest;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterCommandTest {
    @Test
    public void testRegisterCommandSuccessful() {
        BufferedReader reader = new BufferedReader(new StringReader("username\npassword123\nname\n20\n"));
        PrintWriter writer = new PrintWriter(new StringWriter());

        RegisterCommand command = new RegisterCommand(reader, writer);
        command.execute();

        assertTrue(command.getReaderProfile().isLoggedIn());
    }

    @Test
    public void testRegisterCommandUnsuccessfulInvalidUsernameFormat() {
        BufferedReader reader = new BufferedReader(new StringReader("us\npassword123\nname\n20\n"));
        PrintWriter writer = new PrintWriter(new StringWriter());

        Command command = new RegisterCommand(reader, writer);

        assertThrows(LibraryException.class, command::execute,
            "Invalid username format should throw LibraryException!");
    }

    @Test
    public void testRegisterCommandUnsuccessfulInvalidPasswordFormat() {
        BufferedReader reader = new BufferedReader(new StringReader("username\npass\nname\n20\n"));
        PrintWriter writer = new PrintWriter(new StringWriter());

        Command command = new RegisterCommand(reader, writer);

        assertThrows(LibraryException.class, command::execute,
            "Invalid password format should throw LibraryException!");
    }

    @Test
    public void testRegisterCommandUnsuccessfulInvalidAgeFormat() {
        BufferedReader reader = new BufferedReader(new StringReader("username\npass\nname\n-3\n"));
        PrintWriter writer = new PrintWriter(new StringWriter());

        Command command = new RegisterCommand(reader, writer);

        assertThrows(LibraryException.class, command::execute,
            "Invalid age format should throw LibraryException!");
    }

    @Test
    public void testRegisterCommandUnsuccessfulInvalidFullNameFormat() {
        BufferedReader reader = new BufferedReader(new StringReader("username\npass\nn\n20\n"));
        PrintWriter writer = new PrintWriter(new StringWriter());

        Command command = new RegisterCommand(reader, writer);

        assertThrows(LibraryException.class, command::execute,
            "Invalid full name format should throw LibraryException!");
    }

    @Test
    public void testRegisterCommandThrowsRuntimeException() {
        BufferedReader reader = new BufferedReader(new StringReader("username\npassword123\nname\n20\n")) {
            @Override
            public String readLine() {
                throw new RuntimeException("Test RuntimeException");
            }
        };
        PrintWriter writer = new PrintWriter(new StringWriter());

        RegisterCommand command = new RegisterCommand(reader, writer);

        assertThrows(RuntimeException.class, command::execute);
    }
}
