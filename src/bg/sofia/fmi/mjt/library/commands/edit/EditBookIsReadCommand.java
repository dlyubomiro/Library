package bg.sofia.fmi.mjt.library.commands.edit;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class EditBookIsReadCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String bookTitle;
    private final String newIsRead;
    private static final String ATTRIBUTE = "isRead";

    public EditBookIsReadCommand(ReaderProfile readerProfile, String bookTitle, String newIsRead) {
        this.readerProfile = readerProfile;
        this.bookTitle = bookTitle;
        this.newIsRead = newIsRead;
    }

    @Override
    public void execute() {
        if (readerProfile.editIsRead(bookTitle, newIsRead)) {
            CommandLogger.logUserCommand(readerProfile.getUsername(),
                RequestType.EDIT.getValue(), ATTRIBUTE, newIsRead);
            return;
        }

        CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.EDIT.getValue(), "Unsuccessful editing");
        throw new LibraryException("No book with this title in your library!");
    }
}