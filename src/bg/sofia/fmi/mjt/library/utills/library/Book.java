package bg.sofia.fmi.mjt.library.utills.library;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Book {
    private final String id;
    private final String isbn;
    private String title;
    private final List<String> authors;
    private final String publishedDate;
    private String description;
    private final int pagesCount;
    private boolean isRead;
    private int rate;

    public Book(String id, String isbn, String title,
                List<String> authors, String publishedDate,
                String description, int pagesCount) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pagesCount = pagesCount;
    }

    public void setRate(int value) {
        rate = value;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String isbn() {
        return isbn;
    }

    public String title() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> authors() {
        return authors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pagesCount == book.pagesCount && isRead == book.isRead && rate == book.rate &&
            Objects.equals(id, book.id) && Objects.equals(isbn, book.isbn) &&
            Objects.equals(title, book.title) && Objects.equals(authors, book.authors) &&
            Objects.equals(publishedDate, book.publishedDate) &&
            Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, authors, publishedDate, description, pagesCount, isRead, rate);
    }

    @Override
    public String toString() {
        return "Book{" +
            "id='" + id  + "',|" +
            "isbn='" + isbn  +  "',|" +
            "title='" + title + "',|" +
            "authors=" + authors + "',|" +
            "publishedDate='" + publishedDate + "',|" +
            "description='" + description + "',|" +
            "pagesCount=" + pagesCount + "',|" +
            "isRead=" + isRead + "',|" +
            '}';
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public Collection<String> getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getRating() {
        return rate;
    }
}
