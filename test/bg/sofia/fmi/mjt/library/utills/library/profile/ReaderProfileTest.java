package bg.sofia.fmi.mjt.library.utills.library.profile;

import bg.sofia.fmi.mjt.library.utills.library.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderProfileTest {
    private static final String TEST_USERNAME = "mimi03";
    private static final String TEST_PASSWORD = "1234";
    private ReaderProfile profile;

    @BeforeEach
    public void setUp() {
        profile = new ReaderProfile("Maria Ivanova", 20, TEST_USERNAME, TEST_PASSWORD);
    }

    @Test
    public void testAddBook() {
        Book book = new Book("123", "1234567890", "Test Book", null, null, null, 0);
        profile.addBook(book);
        Map<String, Book> expectedBooks = new TreeMap<>();
        expectedBooks.put("Test Book", book);
        assertEquals(expectedBooks, profile.getBooks());
    }

    @Test
    public void testEditBookByTitleSuccessful() {
        Book book = new Book("123", "1234567890", "Test Book", null, null, null, 0);
        profile.addBook(book);
        assertTrue(profile.editTitle("Test Book", "new title"));
        Book expectedBook = new Book("123", "1234567890", "new title", null, null, null, 0);

        Map<String, Book> expectedBooks = new TreeMap<>();
        expectedBooks.put("new title", expectedBook);
        assertEquals(expectedBooks, profile.getBooks());
    }

    @Test
    public void testEditBookByTitleNotSuccessful() {
        assertFalse(profile.editTitle("Test Book", "new title"));
    }

    @Test
    public void testDeleteBookByTitle() {
        Book book = new Book("123", "1234", "Test Book", null, null, null, 0);
        profile.addBook(book);
        assertTrue(profile.deleteBookByTitle("Test Book"));
        assertFalse(profile.deleteBookByTitle("Non-existing Title"));
    }

    @Test
    public void testDeleteBookByAuthor() {
        Book book = new Book("123", "1234567890", "Test Book", List.of(new String[] {"J. K. Rowling"}), null, null, 0);
        profile.addBook(book);
        assertTrue(profile.deleteBookByAuthor("J. K. Rowling"));
        assertFalse(profile.deleteBookByAuthor("Non-existing author"));
    }

    @Test
    public void testDeleteBookByISBN() {
        Book book = new Book("123", "1234", "Test Book", null, null, null, 0);
        profile.addBook(book);
        assertTrue(profile.deleteBookByISBN("1234"));
        assertFalse(profile.deleteBookByISBN("Non-existing ISBN"));
    }

    @Test
    public void testRateBook() {
        Book book = new Book("123", "1234", "Test Book", null, null, null, 0);
        profile.addBook(book);
        assertTrue(profile.rateBook("Test Book", "5"));
        assertFalse(profile.rateBook("Non-existing title", "5"));
    }

    @Test
    public void testSearchBook() {
        Book book = new Book("123", "1234567890", "Test Book", null, null, null, 0);
        profile.addBook(book);
        assertEquals(book, profile.searchBook("Test Book"));
        assertNull(profile.searchBook("Non-existing Title"));
    }

    @Test
    public void testEditIsRead() {
        assertFalse(profile.editIsRead("Unknown", "yes"));
        Book book = new Book("123", "1234567890", "Test Book", null, null, null, 0);
        profile.addBook(book);
        assertTrue(profile.editIsRead("Test Book", "yes"));
        assertTrue(profile.editIsRead("Test Book", "no"));
    }
}