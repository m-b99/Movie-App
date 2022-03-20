package ma.ac.ensias.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    private PasswordEncryptor() {

    }
    public static String encrypt(String value) {
        String salt = BCrypt.gensalt(10);
        String hashedValue = BCrypt.hashpw(value, salt);
        return hashedValue;
    }

    public static boolean check(String value, String encValue) {
        boolean result = BCrypt.checkpw(value, encValue);
        return result;
    }
}
