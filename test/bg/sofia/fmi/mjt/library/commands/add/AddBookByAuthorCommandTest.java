package bg.sofia.fmi.mjt.library.commands.add;

import bg.sofia.fmi.mjt.library.api.BooksClient;
import bg.sofia.fmi.mjt.library.api.BooksRequest;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddBookByAuthorCommandTest {
    @Mock
    HttpClient mockHttpClient = mock();

    @Mock
    BooksRequest mockBooksRequest = mock();

    @Test
    public void testAddBookByAuthorCommandWithValidInput()
        throws IOException, InterruptedException {
        Book book = new Book("3GOFouoFqoIC", "9781840225563", "The Complete Novels of Jane Austen",
            List.of(new String[] {"Jane Austen"}), "2007",
            "Jane Austen is without question, one of England's most enduring and skilled novelists. With her wit, social precision, and unerring ability to create some of literature's most charismatic and believable heroines, she mesmerises her readers as much today as when her novels were first published. Whether it is her sharp, ironic gaze at the Gothic genre invoked by the adventures of Catherine Morland in Northanger Abbey; the diffident and much put-upon Fanny Price struggling to cope with her emotions in Mansfield Park; her delightfully paced comedy of manners and the machinations of the sisters Elinor and Marianne in Sense and Sensibility; the quiet strength of Anne Elliot in Persuasion succeeding in a world designed to subjugate her very existence; and Emma - 'a heroine whom no one but myself will like' teased Austen - yet another irresistible character on fire with imagination and foresight. Indeed not unlike her renowned creator. Jane Austen is as sure-footed in her steps through society's whirlpools of convention and prosaic mores as she is in her sometimes restrained but ever precise and enduring prose.",
            1444);

        BooksClient booksClient = new BooksClient(mockHttpClient);
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=inauthor:Jane+Austen&maxResults=1"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(200);

        String jsonResponse = "{\"kind\": \"books#volumes\",\"totalItems\": 433,\"items\": [ {\"kind\": \"books#volume\",\"id\": \"3GOFouoFqoIC\",\"etag\": \"LbGMmuIs2zM\",\"selfLink\": \"https://www.googleapis.com/books/v1/volumes/3GOFouoFqoIC\",\"volumeInfo\": {\"title\": \"The Complete Novels of Jane Austen\",\"authors\": [\"Jane Austen\" ],\"publisher\": \"Wordsworth Editions\",\"publishedDate\": \"2007\",\"description\": \"Jane Austen is without question, one of England's most enduring and skilled novelists. With her wit, social precision, and unerring ability to create some of literature's most charismatic and believable heroines, she mesmerises her readers as much today as when her novels were first published. Whether it is her sharp, ironic gaze at the Gothic genre invoked by the adventures of Catherine Morland in Northanger Abbey; the diffident and much put-upon Fanny Price struggling to cope with her emotions in Mansfield Park; her delightfully paced comedy of manners and the machinations of the sisters Elinor and Marianne in Sense and Sensibility; the quiet strength of Anne Elliot in Persuasion succeeding in a world designed to subjugate her very existence; and Emma - 'a heroine whom no one but myself will like' teased Austen - yet another irresistible character on fire with imagination and foresight. Indeed not unlike her renowned creator. Jane Austen is as sure-footed in her steps through society's whirlpools of convention and prosaic mores as she is in her sometimes restrained but ever precise and enduring prose.\",\"industryIdentifiers\": [{\"type\": \"ISBN_10\",\"identifier\": \"1840225564\" }, {\"type\": \"ISBN_13\",\"identifier\": \"9781840225563\" }],\"readingModes\": {\"text\": false,\"image\": true },\"pageCount\": 1444,\"printType\": \"BOOK\",\"categories\": [\"England\" ],\"maturityRating\": \"NOT_MATURE\",\"allowAnonLogging\": false,\"contentVersion\": \"0.2.3.0.preview.1\",\"panelizationSummary\": {\"containsEpubBubbles\": false,\"containsImageBubbles\": false },\"imageLinks\": {\"smallThumbnail\": \"http://books.google.com/books/content?id=3GOFouoFqoIC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\"thumbnail\": \"http://books.google.com/books/content?id=3GOFouoFqoIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\" },\"language\": \"en\",\"previewLink\": \"http://books.google.com/books?id=3GOFouoFqoIC&printsec=frontcover&dq=inauthor:Jane+Austen&hl=&cd=1&source=gbs_api\",\"infoLink\": \"http://books.google.com/books?id=3GOFouoFqoIC&dq=inauthor:Jane+Austen&hl=&source=gbs_api\",\"canonicalVolumeLink\": \"https://books.google.com/books/about/The_Complete_Novels_of_Jane_Austen.html?hl=&id=3GOFouoFqoIC\" },\"saleInfo\": {\"country\": \"US\",\"saleability\": \"NOT_FOR_SALE\",\"isEbook\": false },\"accessInfo\": {\"country\": \"US\",\"viewability\": \"PARTIAL\",\"embeddable\": true,\"publicDomain\": false,\"textToSpeechPermission\": \"ALLOWED\",\"epub\": {\"isAvailable\": false },\"pdf\": {\"isAvailable\": true,\"acsTokenLink\": \"http://books.google.com/books/download/The_Complete_Novels_of_Jane_Austen-sample-pdf.acsm?id=3GOFouoFqoIC&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\" },\"webReaderLink\": \"http://play.google.com/books/reader?id=3GOFouoFqoIC&hl=&source=gbs_api\",\"accessViewStatus\": \"SAMPLE\",\"quoteSharingAllowed\": false },\"searchInfo\": {\"textSnippet\": \"Jane Austen is without question, one of England&#39;s most enduring and skilled novelists.\" }}]}";
        when(httpResponseMock.body()).thenReturn(jsonResponse);

        ReaderProfile readerProfile = new ReaderProfile("Maria Ivanova", 20, "mimi03", "1234");
        AddBookByAuthorCommand command = new AddBookByAuthorCommand(readerProfile, "Jane Austen", booksClient);
        command.execute();

        assertTrue(readerProfile.getBooks().containsValue(book));
    }

    @Test
    public void testAddBookByAuthorCommandWithInvalidInput()
        throws IOException, InterruptedException {
        BooksClient booksClient = BooksClient.createWithDefaultHttpClient();
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=inauthor:brumgdfs"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

        String jsonResponse = "{\"kind\":\"books#volumes\",\"totalItems\":0}";
        when(httpResponseMock.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(200);

        ReaderProfile readerProfile = new ReaderProfile("Maria Ivanova", 20, "mimi03", "1234");
        AddBookByAuthorCommand command = new AddBookByAuthorCommand(readerProfile, "brumgdfs", booksClient);
        assertThrows(LibraryException.class, command::execute
        , "No books found by author brumgdfs, method execute should throw LibraryException");
    }

}
