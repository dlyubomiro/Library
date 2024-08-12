package bg.sofia.fmi.mjt.library.commands.remove;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveBookTitleCommandTest {
    @Test
    public void testRemoveBookTitleCommandSuccessful() {
        Book book = new Book("id", "000", "initial title", List.of(new String[] {"Jane Austen"}), null, null, 0);
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        Command command = new RemoveBookTitleCommand(profile, "initial title");
        profile.addBook(book);
        command.execute();

        assertFalse(profile.getBooks().containsKey("initial title"));
    }

    @Test
    public void testRemoveBookTitleCommandUnsuccessful() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        Command command = new RemoveBookTitleCommand(profile, "initial title");

        assertThrows(LibraryException.class, command::execute,
            "No book with this title in the library should throw LibraryException!");
    }
}
