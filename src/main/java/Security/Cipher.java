/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import Network.Client.RunningClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


/**
 * Třída která se stará o šifrování/dešifrování komunikace
 * @author pix
 */
public final class Cipher {

    private static int ASYM_KEY_SIZE;
    private static int SYM_KEY_SIZE;
    private static String ASYM_ALGORITHM;
    private static String SYM_ALGORITHM;
    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private static SecretKey secKey;
    private static ConcurrentHashMap<String,PublicKey> clientPublicKeys;
    private static ConcurrentHashMap<String,SecretKey> clientSecretKeys;
    private static final Logger logger = LoggerFactory.getLogger(Cipher.class);

    /**
     *  Metoda na vytvoření klíčů pomocí zadaných algoritmů
     * @throws NoSuchAlgorithmException
     */

    public static void setSettings(){
        Cipher.SYM_ALGORITHM = SettingsUtil.getSymAlgorithm();
        Cipher.ASYM_ALGORITHM = SettingsUtil.getAsymAlgorithm();
        Cipher.ASYM_KEY_SIZE = SettingsUtil.getAsymKeySize();
        Cipher.SYM_KEY_SIZE = SettingsUtil.getSymKeySize();
    }

    public  static void setSettingsTest(String asymAlgorithm, String symAlgorithm, int asymKeySize, int symKeySize){
        Cipher.ASYM_KEY_SIZE = asymKeySize;
        Cipher.SYM_KEY_SIZE = symKeySize;
        Cipher.ASYM_ALGORITHM = asymAlgorithm;
        Cipher.SYM_ALGORITHM = symAlgorithm;
    }


    public static void createKeys() throws NoSuchAlgorithmException {

        // Generování asym klíčů
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ASYM_ALGORITHM);
        kpg.initialize(ASYM_KEY_SIZE);
        KeyPair keys = kpg.genKeyPair();
        privateKey = keys.getPrivate();
        publicKey = keys.getPublic();

        clientPublicKeys = new ConcurrentHashMap<>();
        clientSecretKeys = new ConcurrentHashMap<>();

    }


    public static SecretKey createAsymKeyForClient(String ConnectionID) throws NoSuchAlgorithmException {

        //Generování sym klíče pro klienta
        KeyGenerator kg = KeyGenerator.getInstance(SYM_ALGORITHM);
        kg.init(SYM_KEY_SIZE);
        secKey = kg.generateKey();
        clientSecretKeys.put(ConnectionID,secKey);
        logger.debug("Vygenerovaný AES klíč pro clienta: "+ConnectionID);
        return secKey;

    }


    /**
     *  Zašifrování byte[] -> využívá stringEncrypt()
     * @param message
     * @param connectionID
     * @return
     * @throws Exception
     */
    public static String encrypt(String message, String connectionID) throws Exception {

        javax.crypto.Cipher cipherSym = javax.crypto.Cipher.getInstance(SYM_ALGORITHM +"/ECB/PKCS5PADDING");
        cipherSym.init(javax.crypto.Cipher.ENCRYPT_MODE,clientSecretKeys.get(connectionID));
        byte[] encryptedMessage = cipherSym.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return  Base64.getEncoder().encodeToString(encryptedMessage);

    }


    /**
     *  Dešifrovává byte[] -> používá stringDecrypt()
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String message, String ConnectionID) throws Exception {

            javax.crypto.Cipher cipherSym = javax.crypto.Cipher.getInstance(SYM_ALGORITHM +"/ECB/PKCS5PADDING");
            cipherSym.init(javax.crypto.Cipher.DECRYPT_MODE,clientSecretKeys.get(ConnectionID));
            byte[] decryptedMessage = cipherSym.doFinal(Base64.getDecoder().decode(message));

            return  new String(decryptedMessage, StandardCharsets.UTF_8);

    }

    public static void setClientPublicKey(String connectionID, byte[] clientPublicKey) throws Exception{
        clientPublicKeys.put(connectionID, KeyFactory.getInstance(ASYM_ALGORITHM).generatePublic(new X509EncodedKeySpec(clientPublicKey)));
    }


    public static String getEncryptedAESKey(String ConnectionID) throws Exception{
        System.out.println("NEEXISTUJE ConnectionID: "+clientSecretKeys.containsKey(ConnectionID));
        return Base64.getEncoder().encodeToString(encryptAESKey(clientPublicKeys.get(ConnectionID),secKey));

    }

    /**
     *  Zašifrovává AES klíč pomocí klientského veřejného klíče
     * @param clientPubKey
     * @param secKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptAESKey(PublicKey clientPubKey, SecretKey secKey) throws Exception
    {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ASYM_ALGORITHM);
        logger.debug("SYMKEY ----> "+Base64.getEncoder().encodeToString(secKey.getEncoded()));
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE,clientPubKey);
        return  cipher.doFinal(secKey.getEncoded());
    }

    /**
     * Decryptuje AES klíč -> používano pouze v testech
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static byte[] decryptAsymKey(byte[] encrypted) throws Exception{
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ASYM_ALGORITHM);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE,privateKey);
        return  cipher.doFinal(encrypted);
    }


    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static String getAsymAlgorithm() {
        return ASYM_ALGORITHM;
    }

    public static int getAsymKeySize() {
        return ASYM_KEY_SIZE;
    }

    public static String getSymAlgorithm(){return SYM_ALGORITHM;}



    public static int getSymKeySize() {return SYM_KEY_SIZE;}





}
