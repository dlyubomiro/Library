package bg.sofia.fmi.mjt.library.api;

import bg.sofia.fmi.mjt.library.exception.InvalidRequestException;
import bg.sofia.fmi.mjt.library.exception.ServerException;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.BookRaw;
import bg.sofia.fmi.mjt.library.utills.library.VolumeInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class BooksClient {
    private final HttpClient httpClient;
    private static final int SUCCESS_STATUS_CODE = 200;
    private static final String ITEMS_MEMBER_NAME = "items";
    private static final int SERVER_ERROR_STATUS_CODE_START = 500;
    private static final int SERVER_ERROR_STATUS_CODE_END = 599;
    private final Gson gson = new Gson();

    public BooksClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public static BooksClient createWithDefaultHttpClient() {
        return new BooksClient(HttpClient.newHttpClient());
    }

    private  List<Book> parseBooks(String jsonResponse) {
        List<Book> books = new ArrayList<>();

        JsonObject json = gson.fromJson(jsonResponse, JsonObject.class);

        if (json.has(ITEMS_MEMBER_NAME)) {
            JsonArray items = json.getAsJsonArray(ITEMS_MEMBER_NAME);

            for (JsonElement item : items) {
                BookRaw book = gson.fromJson(item, BookRaw.class);

                VolumeInfo volumeInfo = book.volumeInfo();
                books.add(new Book(book.id(), volumeInfo.industryIdentifiers().getLast().identifier(),
                    volumeInfo.title(), volumeInfo.authors(), volumeInfo.publishedDate(),
                    volumeInfo.description(), volumeInfo.pageCount()));
            }
        }

        return books;
    }

    private  List<Book> searchBooks(URI uri) throws InvalidRequestException {
        HttpResponse<String> httpResponse;

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).build();
            httpResponse = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new InvalidRequestException("Invalid request", e);
        }

        int responseStatusCode = httpResponse.statusCode();
        if (responseStatusCode >= SERVER_ERROR_STATUS_CODE_START &&
            responseStatusCode <= SERVER_ERROR_STATUS_CODE_END) {
            throw new ServerException("Unable to connect to the server.");
        }

        if (httpResponse.statusCode() != SUCCESS_STATUS_CODE) {
            throw new InvalidRequestException("Invalid request");
        }

        List<Book> books = parseBooks(httpResponse.body());
        if (books.isEmpty()) {
            throw new InvalidRequestException("No such book!");
        }
        return books;
    }

    public Book execute(BooksRequest query) throws InvalidRequestException {
        List<Book> books = searchBooks(query.uri());

        for (Book b : books) {
            System.out.println(b.toString());
        }

        return books.getFirst();
    }
}
