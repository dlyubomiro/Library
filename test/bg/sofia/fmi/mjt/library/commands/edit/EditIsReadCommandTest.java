package bg.sofia.fmi.mjt.library.commands.edit;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditIsReadCommandTest {
    @Test
    public void testEditIsReadCommandSuccessful() {
        Book book = new Book(null,null,"title", null,null,"initial description", 0);
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        Command command = new EditBookIsReadCommand(profile, "title", "yes");

        profile.addBook(book);
        command.execute();

        assertTrue(book.getIsRead());
    }

    @Test
    public void testEditIsReadCommandUnsuccessful() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        Command command = new EditBookIsReadCommand(profile, "title", "yes");

        assertThrows(LibraryException.class, command::execute,
            "No book with this title in the library should throw LibraryException!");
    }
}
