package bg.sofia.fmi.mjt.library.commands.add;

import bg.sofia.fmi.mjt.library.api.BooksClient;
import bg.sofia.fmi.mjt.library.api.BooksRequest;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.exception.InvalidRequestException;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class AddBookByTitleCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String bookTitle;
    private final BooksClient booksClient;
    private static final String ATTRIBUTE = "title";

    public AddBookByTitleCommand(ReaderProfile readerProfile, String bookTitle, BooksClient booksClient) {
        this.readerProfile = readerProfile;
        this.bookTitle = bookTitle;
        this.booksClient = booksClient;
    }

    @Override
    public void execute() {
        try {
            Book newBook = booksClient.execute(BooksRequest.newRequest()
                .withTitle(bookTitle)
                .build());
            readerProfile.addBook(newBook);
        } catch (InvalidRequestException e) {
            CommandLogger.logUserCommand(
                readerProfile.getUsername(), RequestType.ADD.toString(), "Adding book by title not successful");
            throw new LibraryException("No such book with this title! Try again - the title should be exact the same!");
        }

        CommandLogger.logUserCommand( readerProfile.getUsername(), RequestType.ADD.toString(), ATTRIBUTE, bookTitle);
    }
}