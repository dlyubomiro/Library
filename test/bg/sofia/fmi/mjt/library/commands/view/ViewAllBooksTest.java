package bg.sofia.fmi.mjt.library.commands.view;

import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewAllBooksTest {
    @Test
    public void testViewAllBooksCommandSuccessful() {
        Book book = new Book("id", "000", "initial title", List.of(new String[] {"Jane Austen"}), null, null, 0);
        ReaderProfile profile = new ReaderProfile("name", 20, "username", "password");

        profile.addBook(book);
        ViewUserBooksCommand command = new ViewUserBooksCommand(profile);
        command.execute();

        assertEquals(1, profile.getBooks().size());
    }
}
