package bg.sofia.fmi.mjt.library.utills.filehandler;

import bg.sofia.fmi.mjt.library.utills.library.profile.ReaderProfile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileHandler {
    private static final String USER_DIRECTORY = "user_data/";
    private static final String JSON_FILE_EXTENSION = ".json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public void saveUserToFile(ReaderProfile profile) throws IOException {
        File file = new File(USER_DIRECTORY + profile.getUsername() + JSON_FILE_EXTENSION);
        try (Writer writer = new FileWriter(file)) {
            GSON.toJson(profile, writer);
        } catch (IOException e) {
            throw new IOException("There was a problem with the saving of the files");
        }
    }

    public static void loadUsers(Map<String, ReaderProfile> readerProfiles) throws IOException {
        Path path = Paths.get(USER_DIRECTORY);
        if (!(Files.exists(path) && Files.isDirectory(path))) {
            Files.createDirectory(path);
        }

        File folder = new File(USER_DIRECTORY);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    try (Reader reader = new FileReader(file)) {
                        ReaderProfile profile = GSON.fromJson(reader, ReaderProfile.class);
                        readerProfiles.put(profile.getUsername(), profile);
                    } catch (IOException e) {
                        throw new IOException("There was a problem with the loading of the files");
                    }
                }
            }
        }
    }
}
