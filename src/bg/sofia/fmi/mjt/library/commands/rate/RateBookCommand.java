package bg.sofia.fmi.mjt.library.commands.rate;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class RateBookCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String bookTitle;
    private final String rating;
    private boolean success = true;

    public RateBookCommand(ReaderProfile readerProfile, String bookTitle, String rating) {
        this.readerProfile = readerProfile;
        this.bookTitle = bookTitle;
        this.rating = rating;
    }

    @Override
    public void execute() {
        if (!readerProfile.rateBook(bookTitle, rating)) {
            success = false;
        }

        if (success) {
            CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.RATE.toString(), bookTitle, rating);
            return;
        }

        CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.RATE.toString(), "Unsuccessful rating");
        throw new LibraryException("No book with this title in your library or nod valid value for rating");
    }
}