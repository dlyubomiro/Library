package bg.sofia.fmi.mjt.library.commands.edit;

import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditBookTitleCommandTest {
    @Test
    public void testEditBookTitleCommandSuccessful() {
        Book book = new Book("id","isbn", "initial title", null, null, null, 0);
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        EditBookTitleCommand command = new EditBookTitleCommand(profile, "initial title", "new title");
        profile.addBook(book);
        command.execute();

        assertEquals("new title", book.getTitle());
    }

    @Test
    public void testEditBookTitleCommandUnsuccessful() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        EditBookTitleCommand command = new EditBookTitleCommand(profile, "initial title", "new title");

        assertThrows(LibraryException.class, command::execute, "No book with this title in the library should throw LibraryException!");
    }
}