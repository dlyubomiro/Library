package bg.sofia.fmi.mjt.library.commands.view;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.library.Book;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class ViewUserBooksCommand implements Command {
    private final ReaderProfile readerProfile;
    private final StringBuilder books = new StringBuilder("|Books:|");
    private static final String SEPARATOR = "|";

    public ViewUserBooksCommand(ReaderProfile readerProfile) {
        this.readerProfile = readerProfile;
    }

    @Override
    public void execute() {
        for (Book book : readerProfile.getBooks().values()) {
            books.append(book.title()).append(SEPARATOR);
        }

        CommandLogger.logUserCommand(readerProfile.getUsername(), RequestType.VIEWALLBOOKS.toString(), "Successful");
    }

    public String getBooks() {
        return books.toString();
    }
}