package bg.sofia.fmi.mjt.library.commands.remove;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class RemoveBookTitleCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String bookTitle;
    private static final String ATTRIBUTE = "title";

    public RemoveBookTitleCommand(ReaderProfile readerProfile, String bookTitle) {
        this.readerProfile = readerProfile;
        this.bookTitle = bookTitle;
    }

    @Override
    public void execute() {
        if (!readerProfile.deleteBookByTitle(bookTitle)) {
            throw new LibraryException("No book with this title in your library!");
        }

        CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.REMOVE.toString(), ATTRIBUTE, bookTitle);
    }
}