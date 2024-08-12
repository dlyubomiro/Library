package bg.sofia.fmi.mjt.library.commands.guest;

import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.utills.validator.InputValidator;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterCommand implements Command {
    private final BufferedReader in;
    private final PrintWriter out;
    private ReaderProfile readerProfile;

    public RegisterCommand(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void execute() {
        String username = readUsername();
        String password = readPassword();
        String fullName = readFullName();
        String age = readAge();

        if (username == null || password == null || fullName == null || age == null) {
            throw new LibraryException("Invalid input!");
        }

        readerProfile = new ReaderProfile(fullName, Integer.parseInt(age), username, password);
        readerProfile.setLoggedIn(true);
        CommandLogger.logUserCommand(username, RequestType.REGISTER.getValue(), "Register successful!");
        out.println("Register successful!");
    }

    public ReaderProfile getReaderProfile() {
        return readerProfile;
    }

    private String readUsername() {
        String username;

        try {
            username = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!InputValidator.isValidUsername(username)) {
            out.println("Invalid username. Username should be with length between 3 and 20 characters.");
            return null;
        }

        return username;
    }

    private String readPassword() {
        String password;
        try {
            password = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!InputValidator.isValidPassword(password)) {
            out.println("Invalid password. Password should be at least 8 characters long.");
            return null;
        }
        return password;
    }

    private String readFullName() {
        String fullName;

        try {
            fullName = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!InputValidator.isValidFullName(fullName)) {
            out.println(
                "Invalid full name. Name should be between 3 and 50 characters, containing only letters and spaces.");
            return null;
        }

        return fullName;
    }

    private String readAge() {
        String age;

        try {
            age = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!InputValidator.isValidAge(age)) {
            out.println("Invalid age. Age should be a positive integer.");
            return null;
        }

        return age;
    }
}
