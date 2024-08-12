package bg.sofia.fmi.mjt.library;

import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import static bg.sofia.fmi.mjt.library.utills.caesar.CaesarCipher.encrypt;

public class LibraryClient implements Client {
    private static final int CEASAR_CIPHER_KEY = 34;
    private static final String SERVER_ADDRESS = "localhost";
    private static final String BOOKS_ITEMS_SEPARATOR = "\\|";
    private static final int SERVER_PORT = 12345;
    private static final String REGISTER_OPTION = "1";
    private static final String LOGIN_OPTION = "2";
    private static final String LOGOUT_OPTION = "quit";

    @Override
    public void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connected to the server.");
            CommandLogger.logUserCommand("User '%s' connected to the server.");

            boolean operationSuccessful = executeUserAuthentication(out, in, scanner);
            if (!operationSuccessful) {
                return;
            }

            processUserCommands(out, in, scanner);
        } catch (IOException e) {
            CommandLogger.logUserCommand("There is a problem with the network communication");
            System.out.println("There is a problem with the network communication");
        }
    }

    private boolean executeUserAuthentication(PrintWriter out, BufferedReader in, Scanner scanner)
        throws IOException {
        boolean operationSuccessful = false;
        while (!operationSuccessful) {
            try {
                System.out.println("Choose an option:");
                System.out.println("1 - Register");
                System.out.println("2 - Login");
                String option = scanner.nextLine();

                if (option.equals(REGISTER_OPTION)) {
                    if (registration(out, in, scanner)) {
                        operationSuccessful = true;
                    }
                } else if (option.equals(LOGIN_OPTION)) {
                    if (logIn(out, in, scanner)) {
                        operationSuccessful = true;
                    }
                } else {
                    System.out.println("Invalid option! Try again!");
                }
            } catch (SocketException e) {
                System.out.println("Server has been closed: client disconnected");
                System.exit(0);
            }
        }
        return operationSuccessful;
    }

    private void processUserCommands(PrintWriter out, BufferedReader in, Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter command (add, remove, search, edit, rate) or \"quit\": ");
                String message = scanner.nextLine();

                if (LOGOUT_OPTION.equals(message)) {
                    break;
                }
                out.println(message);
                System.out.println("The server replied:");
                String line;
                line = in.readLine();
                if (line != null) {
                    String[] lines = line.split(BOOKS_ITEMS_SEPARATOR);
                    for (String currentLine : lines) {
                        System.out.println(currentLine);
                    }
                } else {
                    System.out.println("No response from the server.");
                }
            } catch (SocketException e) {
                System.out.println("Server has been closed: client disconnected");
                System.exit(0);
            } catch (IOException e) {
                CommandLogger.logUserCommand("There is a problem with the network communication");
                System.out.println("There is a problem with the network communication");
            }
        }
    }

    private boolean registration(PrintWriter out, BufferedReader in, Scanner scanner) throws IOException {
        try {
            out.println("REGISTER");

            String username = promptUserForInput("Enter username:", scanner);
            out.println(username);

            String password = promptUserForInput("Enter password:", scanner);
            password = encrypt(password, CEASAR_CIPHER_KEY);
            out.println(password);

            String fullName = promptUserForInput("Enter full name:", scanner);
            out.println(fullName);

            String ageStr = promptUserForInput("Enter age:", scanner);
            out.println(ageStr);
            String serverReply = in.readLine();

            if (!serverReply.equals("Register successful!")) {
                System.out.println("Server: " + serverReply);
                return false;
            }
            System.out.println("Server: " + serverReply);
            return true;
        } catch (SocketException e) {
            System.out.println("Server has been closed: client disconnected");
            System.exit(0);
            return false;
        }
    }

    private String promptUserForInput(String prompt, Scanner scanner) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    private boolean logIn(PrintWriter out, BufferedReader in, Scanner scanner) throws IOException {
        out.println("LOGIN");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        password = encrypt(password, CEASAR_CIPHER_KEY);

        out.println(username);
        out.println(password);

        String serverReply = in.readLine();
        System.out.println("Server: " + serverReply);
        if (serverReply == null) {
            return false;
        }
        return serverReply.equals("Login successful!");
    }
}
