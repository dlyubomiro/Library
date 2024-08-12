package bg.sofia.fmi.mjt.library.commands.edit;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class EditBookTitleCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String oldTitle;
    private final String newTitle;
    private static final String ATTRIBUTE = "title";

    public EditBookTitleCommand(ReaderProfile readerProfile, String oldTitle, String newTitle) {
        this.readerProfile = readerProfile;
        this.oldTitle = oldTitle;
        this.newTitle = newTitle;
    }

    @Override
    public void execute() {
        if (readerProfile.editTitle(oldTitle, newTitle)) {
            CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.EDIT.getValue(), ATTRIBUTE, newTitle);
            return;
        }

        CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.EDIT.getValue(), "Unsuccessful editing");
        throw new LibraryException("No book with this title in your library!");
    }
}