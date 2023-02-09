import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class BlowFish {
    private static String encryptionKey = "mmg";

    public static String encrypt(String plainText) {
        try {
            Key key = new SecretKeySpec(encryptionKey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean comparePasswords(String enteredPassword, String encryptedPassword) {
        String encryptedEnteredPassword = encrypt(enteredPassword);
        return encryptedEnteredPassword.equals(encryptedPassword);
    }
}
