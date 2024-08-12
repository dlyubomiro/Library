package bg.sofia.fmi.mjt.library.utills.caesar;

public class CaesarCipher {
    public static String encrypt(String data, int key) {
        StringBuilder result = new StringBuilder();
        for (char character : data.toCharArray()) {
            result.append((char) (character + key));
        }
        return result.toString();
    }

    public static String decrypt(String data, int key) {
        StringBuilder result = new StringBuilder();
        for (char character : data.toCharArray()) {
            result.append((char) (character - key));
        }
        return result.toString();
    }
}
