package bg.sofia.fmi.mjt.library.utills.caesar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CaesarCipherTest {

    @Test
    public void testEncrypt() {
        String result = CaesarCipher.encrypt("password", 3);
        assertEquals("sdvvzrug", result);
    }

    @Test
    public void testDecrypt() {
        String result = CaesarCipher.decrypt("sdvvzrug", 3);
        assertEquals("password", result);
    }
}