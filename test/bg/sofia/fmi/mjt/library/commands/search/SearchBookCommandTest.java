package bg.sofia.fmi.mjt.library.commands.search;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchBookCommandTest {
    @Test
    public void testSearchBookCommandSuccessful() {
        Book book = new Book(null,null,"title", null,null,"initial description", 0);
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        profile.addBook(book);
        Command command = new SearchBookCommand(profile, "title");
        assertDoesNotThrow(command::execute);
    }

    @Test
    public void testSearchBookCommandUnsuccessful() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        Command command = new SearchBookCommand(profile, "title");
        assertThrows(LibraryException.class, command::execute,
            "No such book with this title in your library should throw LibraryException!");
    }
}
