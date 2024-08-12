package bg.sofia.fmi.mjt.library.commands.edit;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class EditBookDescriptionCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String bookTitle;
    private final String newDescription;
    private static final String ATTRIBUTE = "description";

    public EditBookDescriptionCommand(ReaderProfile readerProfile, String bookTitle, String newDescription) {
        this.readerProfile = readerProfile;
        this.bookTitle = bookTitle;
        this.newDescription = newDescription;
    }

    @Override
    public void execute() {
        if (readerProfile.editDescription(bookTitle, newDescription)) {
            CommandLogger.logUserCommand(readerProfile.getUsername(),
                RequestType.EDIT.getValue(), ATTRIBUTE, newDescription);
            return;
        }

        CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.EDIT.getValue(), "Unsuccessful editing");
        throw new LibraryException("No book with this title in your library!");
    }
}