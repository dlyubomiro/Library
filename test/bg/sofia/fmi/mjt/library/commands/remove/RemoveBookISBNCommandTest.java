package bg.sofia.fmi.mjt.library.commands.remove;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveBookISBNCommandTest {
    @Test
    public void testRemoveBookISBNCommandSuccessful() {
        Book book = new Book("id", "000", "initial title", List.of(new String[] {"Jane Austen"}), null, null, 0);
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        Command command = new RemoveBookISBNCommand(profile, "000");
        profile.addBook(book);
        command.execute();

        for (Book b : profile.getBooks().values()) {
            assertNotEquals("000", b.getIsbn());
        }
    }

    @Test
    public void testRemoveBookISBNCommandUnsuccessful() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        Command command = new RemoveBookISBNCommand(profile, "000");

        assertThrows(LibraryException.class, command::execute,
            "No book with this ISBN in the library should throw LibraryException!");
    }
}
