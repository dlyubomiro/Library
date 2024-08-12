package bg.sofia.fmi.mjt.library.utills.validator;

import java.util.regex.Pattern;

public class InputValidator {
    private static final int MAX_PASSWORD_LEN = 8;
    private static final String VALID_USERNAME_REGEX = "^[a-zA-Z0-9]{3,20}$";
    private static final String VALID_FULLNAME_REGEX = "^[a-zA-Z\\s]{3,50}$";
    private static final int VALID_AGE_MIN = 4;
    private static final int VALID_AGE_MAX = 120;
    public static boolean isValidUsername(String username) {
        return Pattern.matches(VALID_USERNAME_REGEX, username);
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= MAX_PASSWORD_LEN;
    }

    public static boolean isValidFullName(String fullName) {
        return Pattern.matches(VALID_FULLNAME_REGEX, fullName);
    }

    public static boolean isValidAge(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr);
            return age > VALID_AGE_MIN && age < VALID_AGE_MAX;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
