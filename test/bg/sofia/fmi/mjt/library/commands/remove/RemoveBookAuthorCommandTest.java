package bg.sofia.fmi.mjt.library.commands.remove;

import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveBookAuthorCommandTest {
    @Test
    public void testRemoveBookAuthorCommandSuccessful() {
        Book book = new Book("id", "isbn", "initial title", List.of(new String[] {"Jane Austen"}), null, null, 0);
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        RemoveBookAuthorCommand command = new RemoveBookAuthorCommand(profile, "Jane Austen");
        profile.addBook(book);
        command.execute();

        Collection<String> authors;
        for (Book b : profile.getBooks().values()) {
            authors = b.authors();
            assertFalse(authors.contains("Jane Austen"));
        }
    }

    @Test
    public void testRemoveBookAuthorCommandUnsuccessful() {
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");
        RemoveBookAuthorCommand command = new RemoveBookAuthorCommand(profile, "Jane Austen");

        assertThrows(LibraryException.class, command::execute,
            "No book with this author in the library should throw LibraryException!");
    }
}
