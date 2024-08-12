package bg.sofia.fmi.mjt.library.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.exception.InvalidRequestException;
import bg.sofia.fmi.mjt.library.exception.ServerException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BookClientTest {
    @Mock
    private HttpClient mockHttpClient;

    @Mock
    BooksRequest mockBooksRequest = mock();

    @Test
    void testExecuteWithAuthorSuccessful() throws InvalidRequestException, IOException, InterruptedException {
        BooksClient booksClient = new BooksClient(mockHttpClient);
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=inauthor:Jane+Austen&maxResults=1"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(200);

        String jsonResponse = "{\"kind\": \"books#volumes\",\"totalItems\": 433,\"items\": [ {\"kind\": \"books#volume\",\"id\": \"3GOFouoFqoIC\",\"etag\": \"LbGMmuIs2zM\",\"selfLink\": \"https://www.googleapis.com/books/v1/volumes/3GOFouoFqoIC\",\"volumeInfo\": {\"title\": \"The Complete Novels of Jane Austen\",\"authors\": [\"Jane Austen\" ],\"publisher\": \"Wordsworth Editions\",\"publishedDate\": \"2007\",\"description\": \"Jane Austen is without question, one of England's most enduring and skilled novelists. With her wit, social precision, and unerring ability to create some of literature's most charismatic and believable heroines, she mesmerises her readers as much today as when her novels were first published. Whether it is her sharp, ironic gaze at the Gothic genre invoked by the adventures of Catherine Morland in Northanger Abbey; the diffident and much put-upon Fanny Price struggling to cope with her emotions in Mansfield Park; her delightfully paced comedy of manners and the machinations of the sisters Elinor and Marianne in Sense and Sensibility; the quiet strength of Anne Elliot in Persuasion succeeding in a world designed to subjugate her very existence; and Emma - 'a heroine whom no one but myself will like' teased Austen - yet another irresistible character on fire with imagination and foresight. Indeed not unlike her renowned creator. Jane Austen is as sure-footed in her steps through society's whirlpools of convention and prosaic mores as she is in her sometimes restrained but ever precise and enduring prose.\",\"industryIdentifiers\": [{\"type\": \"ISBN_10\",\"identifier\": \"1840225564\" }, {\"type\": \"ISBN_13\",\"identifier\": \"9781840225563\" }],\"readingModes\": {\"text\": false,\"image\": true },\"pageCount\": 1444,\"printType\": \"BOOK\",\"categories\": [\"England\" ],\"maturityRating\": \"NOT_MATURE\",\"allowAnonLogging\": false,\"contentVersion\": \"0.2.3.0.preview.1\",\"panelizationSummary\": {\"containsEpubBubbles\": false,\"containsImageBubbles\": false },\"imageLinks\": {\"smallThumbnail\": \"http://books.google.com/books/content?id=3GOFouoFqoIC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\"thumbnail\": \"http://books.google.com/books/content?id=3GOFouoFqoIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\" },\"language\": \"en\",\"previewLink\": \"http://books.google.com/books?id=3GOFouoFqoIC&printsec=frontcover&dq=inauthor:Jane+Austen&hl=&cd=1&source=gbs_api\",\"infoLink\": \"http://books.google.com/books?id=3GOFouoFqoIC&dq=inauthor:Jane+Austen&hl=&source=gbs_api\",\"canonicalVolumeLink\": \"https://books.google.com/books/about/The_Complete_Novels_of_Jane_Austen.html?hl=&id=3GOFouoFqoIC\" },\"saleInfo\": {\"country\": \"US\",\"saleability\": \"NOT_FOR_SALE\",\"isEbook\": false },\"accessInfo\": {\"country\": \"US\",\"viewability\": \"PARTIAL\",\"embeddable\": true,\"publicDomain\": false,\"textToSpeechPermission\": \"ALLOWED\",\"epub\": {\"isAvailable\": false },\"pdf\": {\"isAvailable\": true,\"acsTokenLink\": \"http://books.google.com/books/download/The_Complete_Novels_of_Jane_Austen-sample-pdf.acsm?id=3GOFouoFqoIC&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\" },\"webReaderLink\": \"http://play.google.com/books/reader?id=3GOFouoFqoIC&hl=&source=gbs_api\",\"accessViewStatus\": \"SAMPLE\",\"quoteSharingAllowed\": false },\"searchInfo\": {\"textSnippet\": \"Jane Austen is without question, one of England&#39;s most enduring and skilled novelists.\" }}]}";
        when(httpResponseMock.body()).thenReturn(jsonResponse);

        Book expected = new Book("3GOFouoFqoIC", "9781840225563", "The Complete Novels of Jane Austen",
            List.of(new String[] {"Jane Austen"}), "2007",
            "Jane Austen is without question, one of England's most enduring and skilled novelists. With her wit, social precision, and unerring ability to create some of literature's most charismatic and believable heroines, she mesmerises her readers as much today as when her novels were first published. Whether it is her sharp, ironic gaze at the Gothic genre invoked by the adventures of Catherine Morland in Northanger Abbey; the diffident and much put-upon Fanny Price struggling to cope with her emotions in Mansfield Park; her delightfully paced comedy of manners and the machinations of the sisters Elinor and Marianne in Sense and Sensibility; the quiet strength of Anne Elliot in Persuasion succeeding in a world designed to subjugate her very existence; and Emma - 'a heroine whom no one but myself will like' teased Austen - yet another irresistible character on fire with imagination and foresight. Indeed not unlike her renowned creator. Jane Austen is as sure-footed in her steps through society's whirlpools of convention and prosaic mores as she is in her sometimes restrained but ever precise and enduring prose.",
            1444);

        assertEquals(expected, booksClient.execute(
            BooksRequest.newRequest()
                .withAuthor("Jane Austin")
                .build()), "Expected and actual should be the same");
    }

    @Test
    void testExecuteInvalidRequestExceptionStatusCode() throws IOException, InterruptedException {
        BooksClient booksClient = new BooksClient(mockHttpClient);
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=inauthor:Jane+Austen&maxResults=1"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(400);

        assertThrows(InvalidRequestException.class,
            () -> booksClient.execute(mockBooksRequest),
            "Status code 400, should throw InvalidRequestException!");
    }

    @Test
    void testExecuteInvalidRequestException() throws IOException, InterruptedException {
        BooksClient booksClient = new BooksClient(mockHttpClient);
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=inauthor:Jane+Austen&maxResults=1"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);


        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);

        assertThrows(InvalidRequestException.class,
            () -> booksClient.execute(mockBooksRequest),
            "Invalid request");
    }

    @Test
    void testExecuteWithTitleSuccessful() throws IOException, InterruptedException, InvalidRequestException {
        Book book = new Book("etukl7GfrxQC", "1781100527", "Harry Potter and the Goblet of Fire",
            List.of(new String[] {"J.K. Rowling"}), "2015-12-08",
            "'There will be three tasks, spaced throughout the school year, and they will test the champions in many different ways ... their magical prowess - their daring - their powers of deduction - and, of course, their ability to cope with danger.' The Triwizard Tournament is to be held at Hogwarts. Only wizards who are over seventeen are allowed to enter - but that doesn't stop Harry dreaming that he will win the competition. Then at Hallowe'en, when the Goblet of Fire makes its selection, Harry is amazed to find his name is one of those that the magical cup picks out. He will face death-defying tasks, dragons and Dark wizards, but with the help of his best friends, Ron and Hermione, he might just make it through - alive! Having become classics of our time, the Harry Potter eBooks never fail to bring comfort and escapism. With their message of hope, belonging and the enduring power of truth and love, the story of the Boy Who Lived continues to delight generations of new readers.",
            750);

        BooksClient booksClient = new BooksClient(mockHttpClient);
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=intitle:Harry+Potter+and+the+goblet+of+fire&maxResults=1"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(200);

        String jsonResponse = "{\"kind\":\"books#volumes\",\"totalItems\":338,\"items\":[{\"kind\":\"books#volume\",\"id\":\"etukl7GfrxQC\",\"etag\":\"8V5KTEbp6dA\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/etukl7GfrxQC\",\"volumeInfo\":{\"title\":\"Harry Potter and the Goblet of Fire\",\"authors\":[\"J.K. Rowling\"],\"publisher\":\"Pottermore Publishing\",\"publishedDate\":\"2015-12-08\",\"description\":\"'There will be three tasks, spaced throughout the school year, and they will test the champions in many different ways ... their magical prowess - their daring - their powers of deduction - and, of course, their ability to cope with danger.' The Triwizard Tournament is to be held at Hogwarts. Only wizards who are over seventeen are allowed to enter - but that doesn't stop Harry dreaming that he will win the competition. Then at Hallowe'en, when the Goblet of Fire makes its selection, Harry is amazed to find his name is one of those that the magical cup picks out. He will face death-defying tasks, dragons and Dark wizards, but with the help of his best friends, Ron and Hermione, he might just make it through - alive! Having become classics of our time, the Harry Potter eBooks never fail to bring comfort and escapism. With their message of hope, belonging and the enduring power of truth and love, the story of the Boy Who Lived continues to delight generations of new readers.\",\"industryIdentifiers\":[{\"type\":\"ISBN_13\",\"identifier\":\"9781781100523\"},{\"type\":\"ISBN_10\",\"identifier\":\"1781100527\"}],\"readingModes\":{\"text\":true,\"image\":true},\"pageCount\":750,\"printType\":\"BOOK\",\"categories\":[\"Fiction\"],\"averageRating\":4.5,\"ratingsCount\":84,\"maturityRating\":\"NOT_MATURE\",\"allowAnonLogging\":true,\"contentVersion\":\"3.23.22.0.preview.3\",\"panelizationSummary\":{\"containsEpubBubbles\":false,\"containsImageBubbles\":false},\"imageLinks\":{\"smallThumbnail\":\"http://books.google.com/books/content?id=etukl7GfrxQC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\"thumbnail\":\"http://books.google.com/books/content?id=etukl7GfrxQC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"},\"language\":\"en\",\"previewLink\":\"http://books.google.com/books?id=etukl7GfrxQC&printsec=frontcover&dq=intitle:Harry+Pottter+and+the+goblet+of+fire&hl=&cd=1&source=gbs_api\",\"infoLink\":\"https://play.google.com/store/books/details?id=etukl7GfrxQC&source=gbs_api\",\"canonicalVolumeLink\":\"https://play.google.com/store/books/details?id=etukl7GfrxQC\"},\"saleInfo\":{\"country\":\"US\",\"saleability\":\"FOR_SALE\",\"isEbook\":true,\"listPrice\":{\"amount\":9.99,\"currencyCode\":\"USD\"},\"retailPrice\":{\"amount\":9.99,\"currencyCode\":\"USD\"},\"buyLink\":\"https://play.google.com/store/books/details?id=etukl7GfrxQC&rdid=book-etukl7GfrxQC&rdot=1&source=gbs_api\",\"offers\":[{\"finskyOfferType\":1,\"listPrice\":{\"amountInMicros\":9990000,\"currencyCode\":\"USD\"},\"retailPrice\":{\"amountInMicros\":9990000,\"currencyCode\":\"USD\"},\"giftable\":true}]},\"accessInfo\":{\"country\":\"US\",\"viewability\":\"PARTIAL\",\"embeddable\":true,\"publicDomain\":false,\"textToSpeechPermission\":\"ALLOWED\",\"epub\":{\"isAvailable\":true,\"acsTokenLink\":\"http://books.google.com/books/download/Harry_Potter_and_the_Goblet_of_Fire-sample-epub.acsm?id=etukl7GfrxQC&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"pdf\":{\"isAvailable\":true,\"acsTokenLink\":\"http://books.google.com/books/download/Harry_Potter_and_the_Goblet_of_Fire-sample-pdf.acsm?id=etukl7GfrxQC&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"webReaderLink\":\"http://play.google.com/books/reader?id=etukl7GfrxQC&hl=&source=gbs_api\",\"accessViewStatus\":\"SAMPLE\",\"quoteSharingAllowed\":false},\"searchInfo\":{\"textSnippet\":\"With their message of hope, belonging and the enduring power of truth and love, the story of the Boy Who Lived continues to delight generations of new readers.\"}}]}" ;
        when(httpResponseMock.body()).thenReturn(jsonResponse);

        assertEquals(book, booksClient.execute(
            BooksRequest.newRequest()
                .withTitle("Harry Potter and the Goblet of Fire")
                .build()), "Expected and actual should be the same");
    }

    @Test
    void testExecuteWithISBNSuccessful() throws IOException, InterruptedException, InvalidRequestException {
        Book book = new Book("3GOFouoFqoIC", "9781840225563", "The Complete Novels of Jane Austen",
            List.of(new String[] {"Jane Austen"}), "2007",
            "Jane Austen is without question, one of England's most enduring and skilled novelists. With her wit, social precision, and unerring ability to create some of literature's most charismatic and believable heroines, she mesmerises her readers as much today as when her novels were first published. Whether it is her sharp, ironic gaze at the Gothic genre invoked by the adventures of Catherine Morland in Northanger Abbey; the diffident and much put-upon Fanny Price struggling to cope with her emotions in Mansfield Park; her delightfully paced comedy of manners and the machinations of the sisters Elinor and Marianne in Sense and Sensibility; the quiet strength of Anne Elliot in Persuasion succeeding in a world designed to subjugate her very existence; and Emma - 'a heroine whom no one but myself will like' teased Austen - yet another irresistible character on fire with imagination and foresight. Indeed not unlike her renowned creator. Jane Austen is as sure-footed in her steps through society's whirlpools of convention and prosaic mores as she is in her sometimes restrained but ever precise and enduring prose.",
            1444);

        BooksClient booksClient = new BooksClient(mockHttpClient);
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=isbn:9781840225563&maxResults=1"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(200);

        String jsonResponse = "{\"kind\": \"books#volumes\",\"totalItems\": 1,\"items\": [ {\"kind\": \"books#volume\",\"id\": \"3GOFouoFqoIC\",\"etag\": \"LbGMmuIs2zM\",\"selfLink\": \"https://www.googleapis.com/books/v1/volumes/3GOFouoFqoIC\",\"volumeInfo\": {\"title\": \"The Complete Novels of Jane Austen\",\"authors\": [\"Jane Austen\" ],\"publisher\": \"Wordsworth Editions\",\"publishedDate\": \"2007\",\"description\": \"Jane Austen is without question, one of England's most enduring and skilled novelists. With her wit, social precision, and unerring ability to create some of literature's most charismatic and believable heroines, she mesmerises her readers as much today as when her novels were first published. Whether it is her sharp, ironic gaze at the Gothic genre invoked by the adventures of Catherine Morland in Northanger Abbey; the diffident and much put-upon Fanny Price struggling to cope with her emotions in Mansfield Park; her delightfully paced comedy of manners and the machinations of the sisters Elinor and Marianne in Sense and Sensibility; the quiet strength of Anne Elliot in Persuasion succeeding in a world designed to subjugate her very existence; and Emma - 'a heroine whom no one but myself will like' teased Austen - yet another irresistible character on fire with imagination and foresight. Indeed not unlike her renowned creator. Jane Austen is as sure-footed in her steps through society's whirlpools of convention and prosaic mores as she is in her sometimes restrained but ever precise and enduring prose.\",\"industryIdentifiers\": [{\"type\": \"ISBN_10\",\"identifier\": \"1840225564\" }, {\"type\": \"ISBN_13\",\"identifier\": \"9781840225563\" }],\"readingModes\": {\"text\": false,\"image\": true },\"pageCount\": 1444,\"printType\": \"BOOK\",\"categories\": [\"England\" ],\"maturityRating\": \"NOT_MATURE\",\"allowAnonLogging\": false,\"contentVersion\": \"0.2.3.0.preview.1\",\"panelizationSummary\": {\"containsEpubBubbles\": false,\"containsImageBubbles\": false },\"imageLinks\": {\"smallThumbnail\": \"http://books.google.com/books/content?id=3GOFouoFqoIC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\"thumbnail\": \"http://books.google.com/books/content?id=3GOFouoFqoIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\" },\"language\": \"en\",\"previewLink\": \"http://books.google.com/books?id=3GOFouoFqoIC&printsec=frontcover&dq=inauthor:Jane+Austen&hl=&cd=1&source=gbs_api\",\"infoLink\": \"http://books.google.com/books?id=3GOFouoFqoIC&dq=inauthor:Jane+Austen&hl=&source=gbs_api\",\"canonicalVolumeLink\": \"https://books.google.com/books/about/The_Complete_Novels_of_Jane_Austen.html?hl=&id=3GOFouoFqoIC\" },\"saleInfo\": {\"country\": \"US\",\"saleability\": \"NOT_FOR_SALE\",\"isEbook\": false },\"accessInfo\": {\"country\": \"US\",\"viewability\": \"PARTIAL\",\"embeddable\": true,\"publicDomain\": false,\"textToSpeechPermission\": \"ALLOWED\",\"epub\": {\"isAvailable\": false },\"pdf\": {\"isAvailable\": true,\"acsTokenLink\": \"http://books.google.com/books/download/The_Complete_Novels_of_Jane_Austen-sample-pdf.acsm?id=3GOFouoFqoIC&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\" },\"webReaderLink\": \"http://play.google.com/books/reader?id=3GOFouoFqoIC&hl=&source=gbs_api\",\"accessViewStatus\": \"SAMPLE\",\"quoteSharingAllowed\": false },\"searchInfo\": {\"textSnippet\": \"Jane Austen is without question, one of England&#39;s most enduring and skilled novelists.\" }}]}";
        when(httpResponseMock.body()).thenReturn(jsonResponse);

        assertEquals(book, booksClient.execute(
            BooksRequest.newRequest()
                .withIsbnId("9781840225563")
                .build()), "Expected and actual should be the same");
    }

    @Test
    void testExecuteServerException() throws IOException, InterruptedException {
        BooksClient booksClient = new BooksClient(mockHttpClient);
        when(mockBooksRequest.uri()).thenReturn(
            URI.create("https://www.googleapis.com/books/v1/volumes?q=inauthor:Jane+Austen&maxResults=1"));

        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);

        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(501);

        assertThrows(ServerException.class,
            () -> booksClient.execute(mockBooksRequest),
            "Status code 501, should throw ServerException!");
    }
}
