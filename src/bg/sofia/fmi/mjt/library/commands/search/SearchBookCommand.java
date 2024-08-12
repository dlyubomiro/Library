package bg.sofia.fmi.mjt.library.commands.search;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class SearchBookCommand implements Command {
    private final ReaderProfile profile;
    private final String bookTitle;

    public SearchBookCommand(ReaderProfile profile, String bookTitle) {
        this.profile = profile;
        this.bookTitle = bookTitle;
    }

    @Override
    public void execute() {
        Book searchedBook = profile.searchBook(bookTitle);

        if (searchedBook != null) {
            CommandLogger.logUserCommand(profile.getUsername(), RequestType.SEARCH.toString(), "Successful");
            return;
        }
        CommandLogger.logUserCommand(profile.getUsername(), RequestType.SEARCH.toString(), "Unsuccessful");
        throw new LibraryException("No such book with this title in your library!");
    }
}
