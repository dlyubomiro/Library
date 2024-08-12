package bg.sofia.fmi.mjt.library.api;

import java.net.URI;
import java.net.URISyntaxException;

public class BooksRequest {
    private static final String API_KEY = "AIzaSyAqQ8UdBvbldmYEZM4_8vEP67r4wrlkB8A";
    private static final String CLIENT_ID = "206749507340-2vkblcc74lfn6kgvbbi1cpsq9lp5v4qu.apps.googleusercontent.com";
    private static final String SCHEME = "https";
    private static final String HOST = "www.googleapis.com";
    private static final String PATH = "/books/v1/volumes";
    private static final String KEY_QUERY = "key=";
    private static final String CLIENT_ID_QUERY = "client_id=";
    private static final String Q_QUERY = "q=";
    private static final String INTITLE_QUERY = "intitle:";
    private static final String INAUTHOR_QUERY = "inauthor:";
    private static final String ISBN_QUERY = "isbn:";
    private static final String MAX_RESULTS_QUERY = "&maxResults=";
    private static final String AMPERSAND = "&";
    private static final String PLUS = "+";
    private static final String ONE = "1";

    private final String title;
    private final String isbnId;
    private final String author;
    private final int resultsCount;

    BooksRequest(BooksRequestBuilder builder) {
        this.title = builder.getTitle();
        this.author = builder.getAuthor();
        this.isbnId = builder.getIsbnId();
        this.resultsCount = builder.getResultsCount();
    }

    public static BooksRequestBuilder newRequest() {
        return new BooksRequestBuilder();
    }

    public URI uri() {
        try {
            return new URI(SCHEME, HOST, PATH, getQuery(), null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String getQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append(KEY_QUERY + API_KEY + AMPERSAND);
        sb.append(CLIENT_ID_QUERY + CLIENT_ID + AMPERSAND);
        sb.append(Q_QUERY);

        if (title != null) {
            sb.append(INTITLE_QUERY).append(title).append(PLUS);
        }
        if (author != null) {
            sb.append(INAUTHOR_QUERY).append(author).append(PLUS);
        }

        if (isbnId != null) {
            sb.append(ISBN_QUERY).append(isbnId);
        }

        if (resultsCount == 0) {
            sb.append(MAX_RESULTS_QUERY + ONE);
        } else {
            sb.append(MAX_RESULTS_QUERY).append(resultsCount);
        }

        return sb.toString();
    }
}