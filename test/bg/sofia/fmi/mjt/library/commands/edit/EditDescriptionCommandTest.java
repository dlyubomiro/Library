package bg.sofia.fmi.mjt.library.commands.edit;

import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditDescriptionCommandTest {
    @Test
    public void testEditDescriptionCommand() {
        Book book = new Book(null,null,"title", null,null,"initial description", 0);
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        EditBookDescriptionCommand command = new EditBookDescriptionCommand(profile, "title","new description");

        profile.addBook(book);
        command.execute();

        assertEquals("new description", book.getDescription());
    }

    @Test
    public void testEditDescriptionCommandUnsuccessful() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        EditBookDescriptionCommand command = new EditBookDescriptionCommand(profile, "title","new description");

        assertThrows(LibraryException.class, command::execute, "No book with this title in the library should throw LibraryException!");
    }
}