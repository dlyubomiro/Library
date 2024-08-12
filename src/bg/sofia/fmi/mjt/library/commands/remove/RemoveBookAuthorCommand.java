package bg.sofia.fmi.mjt.library.commands.remove;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class RemoveBookAuthorCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String bookAuthor;
    private static final String ATTRIBUTE = "author";

    public RemoveBookAuthorCommand(ReaderProfile readerProfile, String bookAuthor) {
        this.readerProfile = readerProfile;
        this.bookAuthor = bookAuthor;
    }

    @Override
    public void execute() {
        if (!readerProfile.deleteBookByAuthor(bookAuthor)) {
            throw new LibraryException("No book with this author in your library!");
        }

        CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.REMOVE.toString(), ATTRIBUTE, bookAuthor);
    }
}
