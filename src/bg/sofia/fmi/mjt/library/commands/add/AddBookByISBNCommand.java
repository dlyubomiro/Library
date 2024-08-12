package bg.sofia.fmi.mjt.library.commands.add;

import bg.sofia.fmi.mjt.library.api.BooksClient;
import bg.sofia.fmi.mjt.library.api.BooksRequest;
import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.InvalidRequestException;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class AddBookByISBNCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String isbn;
    private final BooksClient booksClient;
    private static final String ATTRIBUTE = "ISBN";

    public AddBookByISBNCommand(ReaderProfile readerProfile, String isbn, BooksClient booksClient) {
        this.readerProfile = readerProfile;
        this.isbn = isbn;
        this.booksClient = booksClient;
    }

    @Override
    public void execute() {
        try {
            Book newBook = booksClient.execute(BooksRequest.newRequest()
                .withIsbnId(isbn)
                .build());
            readerProfile.addBook(newBook);
        } catch (InvalidRequestException e) {
            CommandLogger.logUserCommand(
                readerProfile.getUsername(), RequestType.ADD.toString(), "Adding book by ISBN not successful");
            throw new LibraryException("No such book with this ISBN! Try again!");
        }

        CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.ADD.toString(), ATTRIBUTE, isbn);
    }
}