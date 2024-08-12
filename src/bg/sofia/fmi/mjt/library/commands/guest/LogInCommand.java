package bg.sofia.fmi.mjt.library.commands.guest;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

public class LogInCommand implements Command {
    private final ReaderProfile readerProfile;
    private final String username;
    private final String password;

    public LogInCommand(ReaderProfile readerProfile, String username, String password) {
        this.readerProfile = readerProfile;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() {
        if (isValidLogin(password)) {
            CommandLogger.logUserCommand(username, RequestType.LOGIN.getValue(), "Login successful!");
            readerProfile.setLoggedIn(true);
            return;
        }
        CommandLogger.logUserCommand(username, RequestType.LOGIN.getValue(), "Invalid username or password!");
        throw new LibraryException("Invalid username or password!");
    }

    private boolean isValidLogin(String password) {
        ReaderProfile profile = readerProfile;
        return profile != null && profile.getPassword().equals(password);
    }
}