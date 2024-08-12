package bg.sofia.fmi.mjt.library.api;

public class BooksRequestBuilder {
    private String title;
    private String isbnId;
    private String author;
    private final int resultsCount;

    public BooksRequestBuilder() {
        this.title = null;
        this.author = null;
        this.isbnId = null;
        this.resultsCount = 0;
    }

    public BooksRequestBuilder withIsbnId(String isbnId) {
        this.isbnId = isbnId;
        return this;
    }

    public BooksRequestBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public BooksRequestBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbnId() {
        return isbnId;
    }

    public String getTitle() {
        return title;
    }

    public int getResultsCount() {
        return resultsCount;
    }

    public BooksRequest build() {
        return new BooksRequest(this);
    }
}
