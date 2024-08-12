package bg.sofia.fmi.mjt.library.commands.remove;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class RemoveBookISBNCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String bookISBN;
    private static final String ATTRIBUTE = "ISBN";

    public RemoveBookISBNCommand(ReaderProfile readerProfile, String bookISBN) {
        this.readerProfile = readerProfile;
        this.bookISBN = bookISBN;
    }

    @Override
    public void execute() {
        if (!readerProfile.deleteBookByISBN(bookISBN)) {
            throw new LibraryException("No book with this ISBN in your library!");
        }

        CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.REMOVE.toString(), ATTRIBUTE, bookISBN);
    }
}