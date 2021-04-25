package Security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Testy vztahující se k tříde Communication
 */
public class CipherTest {

    /**
     * Test na vytvoření klůčů a jejich vlastností
     */
    @org.junit.Test
    public void createKeys() {
        try {
            Cipher.setSettingsTest("RSA","AES", 2048,128);
            Cipher.createKeys();
            assertEquals(2048, Cipher.getAsymKeySize());
            assertEquals("RSA", Cipher.getAsymAlgorithm());
            assertEquals("AES", Cipher.getSymAlgorithm());
            assertEquals(128, Cipher.getSymKeySize());

        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    /**
     * Test na zašifrovaní/dešifrování textu
     */
    @org.junit.Test
    public void stringEncryptDecrypt() {
        try {

            String testConnectionID = UUID.randomUUID().toString();

            Cipher.setSettingsTest("RSA","AES", 2048,128);
            Cipher.createKeys();
            Cipher.createAsymKeyForClient(testConnectionID);
            String text = "Toto je text, který bude otestován. $ß";
            String encryptetText = Cipher.encrypt(text,testConnectionID);

            assertNotEquals(text, encryptetText);

            String decryptedMessage = Cipher.decrypt(encryptetText,testConnectionID);
            assertEquals(text, decryptedMessage);

        }catch (Exception e){
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    /**
     * Test na Zašifrování a dešifrovaní AES klíče
     */
    @org.junit.Test
    public void symKeyEncryptDecrypth() {
        try {
            Cipher.setSettingsTest("RSA","AES", 2048,128);
            Cipher.createKeys();

            KeyGenerator kg = KeyGenerator.getInstance(Cipher.getSymAlgorithm());
            kg.init(Cipher.getSymKeySize());
            SecretKey secKey = kg.generateKey();

            String encryptedKey = Base64.getEncoder().encodeToString(Cipher.encryptAESKey(Cipher.getPublicKey(),secKey));
            String decryptedKey = Base64.getEncoder().encodeToString(Cipher.decryptAsymKey(Base64.getDecoder().decode(encryptedKey)));



            assertEquals(Base64.getEncoder().encodeToString(secKey.getEncoded()),decryptedKey);
        }catch (Exception e){
            e.printStackTrace();
            fail(e.getMessage());
        }


    }
}