package Security;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

/**
 * Třída která se stará o hashování a získávání hesel
 *
 */
public final class Passwords {

    private static Random RANDOM = new SecureRandom();
    private static int ITERATIONS;
    private static int KEY_LENGTH;
    private static int SALT_LENGTH;




    public void setSettings(){
        ITERATIONS = SettingsUtil.getHashIter();
        KEY_LENGTH = SettingsUtil.getHashSize();
        SALT_LENGTH = SettingsUtil.getSaltSize();
    }

    public void setSettings(int iterations, int keyLenght, int saltLenght){
        ITERATIONS = iterations;
        KEY_LENGTH = keyLenght;
        SALT_LENGTH = saltLenght;
    }



    /**
     * Vygenerování náhodné soli
     * @return hash hesla
     */
    public byte[] getNextSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Hashování hesla
     * @param password
     * @param salt
     * @return
     */
    public byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }


    /**
     * Ověření hesla jestli se shoduje s z hashem už vytvořeného hesla
     * @param password
     * @param salt
     * @param expectedHash
     * @return
     */
    public boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }



    public static int getKEY_LENGTH() {
        return KEY_LENGTH;
    }

    public static int getSALT_LENGTH() {
        return SALT_LENGTH;
    }


}