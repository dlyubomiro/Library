package bg.sofia.fmi.mjt.library;

import bg.sofia.fmi.mjt.library.api.BooksClient;
import bg.sofia.fmi.mjt.library.commands.Command;
import bg.sofia.fmi.mjt.library.commands.enums.RequestType;
import bg.sofia.fmi.mjt.library.commands.add.AddBookByAuthorCommand;
import bg.sofia.fmi.mjt.library.commands.add.AddBookByISBNCommand;
import bg.sofia.fmi.mjt.library.commands.add.AddBookByTitleCommand;
import bg.sofia.fmi.mjt.library.commands.edit.EditBookDescriptionCommand;
import bg.sofia.fmi.mjt.library.commands.edit.EditBookIsReadCommand;
import bg.sofia.fmi.mjt.library.commands.edit.EditBookTitleCommand;
import bg.sofia.fmi.mjt.library.commands.guest.LogInCommand;
import bg.sofia.fmi.mjt.library.commands.guest.RegisterCommand;
import bg.sofia.fmi.mjt.library.commands.rate.RateBookCommand;
import bg.sofia.fmi.mjt.library.commands.remove.RemoveBookAuthorCommand;
import bg.sofia.fmi.mjt.library.commands.remove.RemoveBookISBNCommand;
import bg.sofia.fmi.mjt.library.commands.remove.RemoveBookTitleCommand;
import bg.sofia.fmi.mjt.library.commands.search.SearchBookCommand;
import bg.sofia.fmi.mjt.library.commands.view.ViewUserBooksCommand;
import bg.sofia.fmi.mjt.library.utills.filehandler.FileHandler;
import bg.sofia.fmi.mjt.library.exception.InvalidRequestException;
import bg.sofia.fmi.mjt.library.exception.LibraryException;
import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;
import bg.sofia.fmi.mjt.library.utills.logger.CommandLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryServer implements Server {
    private static final int PORT = 12345;
    private static final int MAX_EXECUTOR_THREADS = 10;
    private static final String TITLE_ATR = "title";
    private static final String DESCR_ATR = "description";
    private static final String AUTHOR_ATR = "author";
    private static final String ISBN_ATR = "isbn";
    private static final String REGISTER = "REGISTER";
    private static final String LOGIN = "LOGIN";
    private static final String REQUEST_SPLIT_REGEX = "-";
    private static final String REMOVE = "remove";
    private static final String EDIT = "edit";
    private static final String SEARCH = "search";
    private static final String RATE = "rate";
    private static final String ADD = "add";
    private static final int MAX_LENGTH_COMMAND = 5;
    private static final int EDITED_ITEM_TOKEN_IND = 4;
    private static final int VALUE_TOKEN_IND = 3;
    private final Map<String, ReaderProfile> readerProfiles;
    private static final int VERSION_COUNTER = 0;
    private final FileHandler fileHandler = new FileHandler();

    public LibraryServer() {
        readerProfiles = new TreeMap<>();
    }

    public static void main(String... args) {
        LibraryServer server = new LibraryServer();
        server.start();
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS);
        CommandLogger.setVersionsCounter(VERSION_COUNTER);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executor.execute(clientHandler);
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        } finally {
            executor.shutdown();
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket clientSocket;
        BooksClient httpClient;
        private String username;
        private static final String INVALID_COMMAND_STRING = "Invalid input. No such command";

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            httpClient = BooksClient.createWithDefaultHttpClient();
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                FileHandler.loadUsers(readerProfiles);

                boolean operationSuccessful = executeGuestOperation(in, out);
                if (!operationSuccessful) {
                    return;
                }

                processRequests(in, out);
                fileHandler.saveUserToFile(readerProfiles.get(username));
            } catch (SocketException e) {
                System.out.println("Server has been closed: client disconnected");
            } catch (IOException | InvalidRequestException e) {
                System.out.println("Error occurred while processing client request: client disconnected");
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error occurred while closing the client socket: " + e.getMessage());
                }
            }
        }

        private boolean executeGuestOperation(BufferedReader in, PrintWriter out) throws IOException {
            boolean operationSuccessful = false;
            while (!operationSuccessful) {
                String requestType = in.readLine();

                switch (requestType) {
                    case REGISTER -> {
                        if (register(in, out)) {
                            operationSuccessful = true;
                        }
                    }
                    case LOGIN -> {
                        if (login(in, out)) {
                            operationSuccessful = true;
                        }
                    }
                    default -> {
                        out.println("Invalid request type!");
                        CommandLogger.logUserCommand(INVALID_COMMAND_STRING);
                    }
                }
            }
            return operationSuccessful;
        }

        private void processRequests(BufferedReader in, PrintWriter out)
            throws IOException, InvalidRequestException {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                String response = processRequest(inputLine);
                out.println(response);
            }
        }

        private String processRequest(String request) {
            String[] parts = request.split(REQUEST_SPLIT_REGEX);

            if (parts.length == 1 && request.equals("viewAllBooks")) {
                return processViewAllBooksRequest();
            }

            String action = parts[0].trim();
            String attribute = parts[2].trim();
            String value = parts[VALUE_TOKEN_IND].trim().replace("\"", "");
            String editedItem = "";

            if (parts.length == MAX_LENGTH_COMMAND) {
                editedItem = parts[EDITED_ITEM_TOKEN_IND];
            }

            return switch (action) {
                case ADD -> processAddRequest(attribute, value);
                case REMOVE -> processRemoveRequest(attribute, value);
                case EDIT -> processEditRequest(attribute, value, editedItem);
                case SEARCH -> processSearchRequest(attribute, value);
                case RATE -> processRatingRequest(value, parts[EDITED_ITEM_TOKEN_IND]);
                default -> "Invalid input. No such command";
            };
        }

        private String processViewAllBooksRequest() {
            ViewUserBooksCommand command = new ViewUserBooksCommand(readerProfiles.get(username));
            try {
                command.execute();
                return command.getBooks();
            } catch (LibraryException e) {
                return e.getMessage();
            }
        }

        private String processRatingRequest(String title, String value) {
            Command command = new RateBookCommand(readerProfiles.get(username), title, value);

            try {
                command.execute();
            } catch (LibraryException e) {
                return e.getMessage();
            }

            return "Book rated successfully!";
        }

        private String processEditRequest(String attribute, String value, String editedItem) {
            Command command;
            synchronized (readerProfiles) {
                switch (attribute) {
                    case TITLE_ATR ->
                        command = new EditBookTitleCommand(readerProfiles.get(username), value, editedItem);
                    case DESCR_ATR ->
                        command = new EditBookDescriptionCommand(readerProfiles.get(username), value, editedItem);
                    case "isRead" ->
                        command = new EditBookIsReadCommand(readerProfiles.get(username), value, editedItem);
                    default -> {
                        return "Invalid input. No such command";
                    }
                }
            }

            try {
                command.execute();
            } catch (LibraryException e) {
                return e.getMessage();
            }

            return "Book edited successfully!";
        }

        private String processAddRequest(String attribute, String value) {
            Command command;
            synchronized (readerProfiles) {
                switch (attribute) {
                    case TITLE_ATR ->
                        command = new AddBookByTitleCommand(readerProfiles.get(username), value, httpClient);
                    case AUTHOR_ATR ->
                        command = new AddBookByAuthorCommand(readerProfiles.get(username), value, httpClient);
                    case ISBN_ATR ->
                        command = new AddBookByISBNCommand(readerProfiles.get(username), value, httpClient);
                    default -> {
                        return "You can add a book only by a title, author or ISBN number!";
                    }
                }
            }

            try {
                command.execute();
            } catch (LibraryException e) {
                return e.getMessage();
            }

            return "Book added successfully";
        }

        private String processRemoveRequest(String attribute, String value) {
            ReaderProfile currentProfile = readerProfiles.get(username);
            Command command;
            synchronized (readerProfiles) {
                switch (attribute) {
                    case TITLE_ATR -> command = new RemoveBookTitleCommand(currentProfile, value);
                    case AUTHOR_ATR -> command = new RemoveBookAuthorCommand(currentProfile, value);
                    case ISBN_ATR -> command = new RemoveBookISBNCommand(currentProfile, value);
                    default -> {
                        CommandLogger.logUserCommand(username, RequestType.REMOVE.toString(), "Unsuccessful remove");
                        return "Item toRemove not valid!";
                    }
                }
            }

            try {
                command.execute();
            } catch (LibraryException e) {
                return e.getMessage();
            }

            return "Book removed successfully";
        }

        private String processSearchRequest(String attribute, String value) {
            ReaderProfile currentProfile = readerProfiles.get(username);

            if (attribute.equals(TITLE_ATR)) {
                Command command = new SearchBookCommand(currentProfile, value);
                try {
                    command.execute();
                } catch (LibraryException e) {
                    return e.getMessage();
                }
                return currentProfile.searchBook(value).toString();
            }

            CommandLogger.logUserCommand(username, RequestType.SEARCH.toString(), "Unsuccessful");
            return "You can search a book only by it's title";
        }

        private boolean register(BufferedReader in, PrintWriter out) {
            var command = new RegisterCommand(in, out);

            try {
                command.execute();

                username = command.getReaderProfile().getUsername();
                readerProfiles.put(username, command.getReaderProfile());

                return true;
            } catch (LibraryException e) {
                out.println(e.getMessage());
                return false;
            }
        }

        private boolean login(BufferedReader in, PrintWriter out) throws IOException {
            username = in.readLine();
            String password = in.readLine();

            Command command = new LogInCommand(readerProfiles.get(username), username, password);
            try {
                command.execute();
                out.println("Login successful!");
                return true;
            } catch (LibraryException e) {
                out.println(e.getMessage());
                return false;
            }
        }
    }
}
