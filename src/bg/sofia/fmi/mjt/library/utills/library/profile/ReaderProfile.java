package bg.sofia.fmi.mjt.library.utills.library.profile;

import bg.sofia.fmi.mjt.library.utills.library.Book;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ReaderProfile implements Comparable<ReaderProfile> {
    private static final String IS_READ_POSITIVE = "yes";
    private static final String IS_READ_NEGATIVE = "no";

    private int id;
    private final String fullName;
    private final int age;
    private final String username;
    private final String password;
    private final Map<String, Book> books;
    private boolean isLoggedIn;

    public ReaderProfile(String fullName, int age, String username, String password) {
        books = new TreeMap<>();
        this.age = age;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public Map<String, Book> getBooks() {
        return books;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addBook(Book newBook) {
        books.put(newBook.title(), newBook);
    }

    public boolean deleteBookByTitle(String title) {
        int size = books.size();
        books.remove(title);
        return size == books.size() + 1;
    }

    public boolean deleteBookByAuthor(String author) {
        int size = books.size();

        books.entrySet().removeIf(entry -> {
            Book book = entry.getValue();
            return book.authors().contains(author);
        });

        return size == books.size() + 1;
    }

    public boolean deleteBookByISBN(String isbn) {
        int size = books.size();

        books.entrySet().removeIf(entry -> {
            Book book = entry.getValue();
            return book.isbn().equals(isbn);
        });

        return size == books.size() + 1;
    }

    public boolean editIsRead(String title, String isRead) {
        if (!books.containsKey(title)) {
            return false;
        }

        if (isRead.equals(IS_READ_POSITIVE)) {
            books.get(title).setIsRead(true);
        } else if (isRead.equals(IS_READ_NEGATIVE)) {
            books.get(title).setIsRead(false);
        } else {
            books.get(title).setIsRead(false);
        }

        return true;
    }

    private static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean rateBook(String title, String value) {
        boolean isValidInt = isValidInteger(value);

        if (!isValidInt || books.get(title) == null) {
            return false;
        }

        int rate = Integer.parseInt(value);
        books.get(title).setRate(rate);
        return true;
    }

    public boolean editTitle(String title, String newTitle) {
        if (!books.containsKey(title)) {
            return false;
        }

        Book newBook = books.get(title);
        books.remove(title);
        newBook.setTitle(newTitle);
        books.put(newTitle, newBook);

        return true;
    }

    public boolean editDescription(String title, String newDescription) {
        if (!books.containsKey(title)) {
            return false;
        }

        books.get(title).setDescription(newDescription);

        return true;
    }

    public Book searchBook(String title) {
        return books.get(title);
    }

    @Override
    public int compareTo(ReaderProfile other) {
        return this.fullName.compareTo(other.fullName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReaderProfile that = (ReaderProfile) o;
        return id == that.id && age == that.age && Objects.equals(fullName, that.fullName) &&
            Objects.equals(username, that.username) && Objects.equals(password, that.password) &&
            Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, age, username, password, books);
    }

    public Book getBook(String title) {
        return books.get(title);
    }

    public void setLoggedIn(boolean b) {
        isLoggedIn = b;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}

