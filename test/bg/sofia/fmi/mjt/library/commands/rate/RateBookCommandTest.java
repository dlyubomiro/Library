package bg.sofia.fmi.mjt.library.commands.rate;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RateBookCommandTest {
    @Test
    public void testRateBookCommandSuccessful() {
        Book book = new Book(null,null,"title", null,null,"initial description", 0);
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        Command command = new RateBookCommand(profile, "title", "5");

        profile.addBook(book);
        command.execute();

        assertEquals(5, profile.getBook("title").getRating());
    }

    @Test
    public void testRateBookCommandUnsuccessful() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        Command command = new RateBookCommand(profile, "title", "5");

        assertThrows(LibraryException.class, command::execute,
            "No book with this title in the library should throw LibraryException!");
    }
}
