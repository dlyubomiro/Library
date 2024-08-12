package bg.sofia.fmi.mjt.library.commands.add;

import bg.sofia.fmi.mjt.library.api.BooksClient;
import bg.sofia.fmi.mjt.library.api.BooksRequest;
import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddBookByTitleCommandTest {
    @Mock
    HttpClient mockHttpClient = mock();

    @Mock
    BooksRequest mockBooksRequest = mock();
    @Test
    public void testAddBookByTitleCommandWithValidInput()
        throws IOException, InterruptedException {
        BooksClient booksClient = new BooksClient(mockHttpClient);
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=intitle:Harry+Potter+and+the+goblet+of+fire&maxResults=1"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(200);

        String jsonResponse = "{\"kind\":\"books#volumes\",\"totalItems\":338,\"items\":[{\"kind\":\"books#volume\",\"id\":\"etukl7GfrxQC\",\"etag\":\"8V5KTEbp6dA\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/etukl7GfrxQC\",\"volumeInfo\":{\"title\":\"Harry Potter and the Goblet of Fire\",\"authors\":[\"J.K. Rowling\"],\"publisher\":\"Pottermore Publishing\",\"publishedDate\":\"2015-12-08\",\"description\":\"'There will be three tasks, spaced throughout the school year, and they will test the champions in many different ways ... their magical prowess - their daring - their powers of deduction - and, of course, their ability to cope with danger.' The Triwizard Tournament is to be held at Hogwarts. Only wizards who are over seventeen are allowed to enter - but that doesn't stop Harry dreaming that he will win the competition. Then at Hallowe'en, when the Goblet of Fire makes its selection, Harry is amazed to find his name is one of those that the magical cup picks out. He will face death-defying tasks, dragons and Dark wizards, but with the help of his best friends, Ron and Hermione, he might just make it through - alive! Having become classics of our time, the Harry Potter eBooks never fail to bring comfort and escapism. With their message of hope, belonging and the enduring power of truth and love, the story of the Boy Who Lived continues to delight generations of new readers.\",\"industryIdentifiers\":[{\"type\":\"ISBN_13\",\"identifier\":\"9781781100523\"},{\"type\":\"ISBN_10\",\"identifier\":\"1781100527\"}],\"readingModes\":{\"text\":true,\"image\":true},\"pageCount\":750,\"printType\":\"BOOK\",\"categories\":[\"Fiction\"],\"averageRating\":4.5,\"ratingsCount\":84,\"maturityRating\":\"NOT_MATURE\",\"allowAnonLogging\":true,\"contentVersion\":\"3.23.22.0.preview.3\",\"panelizationSummary\":{\"containsEpubBubbles\":false,\"containsImageBubbles\":false},\"imageLinks\":{\"smallThumbnail\":\"http://books.google.com/books/content?id=etukl7GfrxQC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\"thumbnail\":\"http://books.google.com/books/content?id=etukl7GfrxQC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"},\"language\":\"en\",\"previewLink\":\"http://books.google.com/books?id=etukl7GfrxQC&printsec=frontcover&dq=intitle:Harry+Pottter+and+the+goblet+of+fire&hl=&cd=1&source=gbs_api\",\"infoLink\":\"https://play.google.com/store/books/details?id=etukl7GfrxQC&source=gbs_api\",\"canonicalVolumeLink\":\"https://play.google.com/store/books/details?id=etukl7GfrxQC\"},\"saleInfo\":{\"country\":\"US\",\"saleability\":\"FOR_SALE\",\"isEbook\":true,\"listPrice\":{\"amount\":9.99,\"currencyCode\":\"USD\"},\"retailPrice\":{\"amount\":9.99,\"currencyCode\":\"USD\"},\"buyLink\":\"https://play.google.com/store/books/details?id=etukl7GfrxQC&rdid=book-etukl7GfrxQC&rdot=1&source=gbs_api\",\"offers\":[{\"finskyOfferType\":1,\"listPrice\":{\"amountInMicros\":9990000,\"currencyCode\":\"USD\"},\"retailPrice\":{\"amountInMicros\":9990000,\"currencyCode\":\"USD\"},\"giftable\":true}]},\"accessInfo\":{\"country\":\"US\",\"viewability\":\"PARTIAL\",\"embeddable\":true,\"publicDomain\":false,\"textToSpeechPermission\":\"ALLOWED\",\"epub\":{\"isAvailable\":true,\"acsTokenLink\":\"http://books.google.com/books/download/Harry_Potter_and_the_Goblet_of_Fire-sample-epub.acsm?id=etukl7GfrxQC&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"pdf\":{\"isAvailable\":true,\"acsTokenLink\":\"http://books.google.com/books/download/Harry_Potter_and_the_Goblet_of_Fire-sample-pdf.acsm?id=etukl7GfrxQC&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"webReaderLink\":\"http://play.google.com/books/reader?id=etukl7GfrxQC&hl=&source=gbs_api\",\"accessViewStatus\":\"SAMPLE\",\"quoteSharingAllowed\":false},\"searchInfo\":{\"textSnippet\":\"With their message of hope, belonging and the enduring power of truth and love, the story of the Boy Who Lived continues to delight generations of new readers.\"}}]}" ;
        when(httpResponseMock.body()).thenReturn(jsonResponse);

        String bookTitle = "Harry Potter and the Goblet of Fire";
        ReaderProfile readerProfile = new ReaderProfile("Maria Ivanova", 20, "mimi03", "1234");
        Command command = new AddBookByTitleCommand(readerProfile, bookTitle, booksClient);
        command.execute();

        assertTrue(readerProfile.getBooks().containsKey(bookTitle));
    }

    @Test
    public void testAddBookByTitleCommandWithInvalidInput()
        throws IOException, InterruptedException {
        BooksClient booksClient = new BooksClient(mockHttpClient);
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=intitle:lalalallallalalalaa&maxResults=1"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class.toString());

        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(200);

        String jsonResponse = "{\"kind\":\"books#volumes\",\"totalItems\":338}";
            when(httpResponseMock.body()).thenReturn(jsonResponse);

        ReaderProfile readerProfile = new ReaderProfile("Maria Ivanova", 20, "mimi03", "1234");
        Command command = new AddBookByTitleCommand(readerProfile, "lalalallallalalalaa", booksClient);

        assertThrows(LibraryException.class, command::execute
            , "The book with title lalalallallalalalaa was not found");
    }
}
