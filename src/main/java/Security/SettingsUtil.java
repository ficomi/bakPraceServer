package Security;

import org.ini4j.Wini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class SettingsUtil {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String SYM_ALGORITHM;
    private static int SYM_KEY_SIZE;

    private static String ASYM_ALGORITHM;
    private static int ASYM_KEY_SIZE;

    private static String HASH_ALGORITHM;
    private static int HASH_SIZE;
    private static int HASH_ITER;

    private static String DB_KEY_ALGORITHM;
    private static String DB_KEY;

    private static String SALT_ALGORITHM;
    private static int SALT_SIZE;

    private static int PORT;


    private static String DB_PATH;
    private static String DB_NAME;
    private static String DB_PASS;
    private static String DB_USER;

    private final String SETTING_FILE_PATH;



    public SettingsUtil(String settingsPath){

        SETTING_FILE_PATH = settingsPath;

        try {
            Wini ini = new Wini(new File(SETTING_FILE_PATH));
            SYM_ALGORITHM = ini.get("APPLICATION","SYM_ALGORITHM", String.class);
            ASYM_ALGORITHM = ini.get("APPLICATION","ASYM_ALGORITHM", String.class);
            SYM_KEY_SIZE = ini.get("APPLICATION","SYM_KEY_SIZE", int.class);
            ASYM_KEY_SIZE = ini.get("APPLICATION","ASYM_KEY_SIZE", int.class);
            HASH_ALGORITHM = ini.get("APPLICATION","HASH_ALGORITHM", String.class);
            HASH_SIZE = ini.get("APPLICATION","HASH_SIZE", int.class);
            HASH_ITER = ini.get("APPLICATION","HASH_ITER", int.class);
            SALT_ALGORITHM = ini.get("APPLICATION","SALT_ALGORITHM", String.class);
            SALT_SIZE = ini.get("APPLICATION","SALT_SIZE", int.class);
            PORT = ini.get("APPLICATION","PORT", int.class);

            DB_NAME = ini.get("DATABASE","DB_NAME", String.class);
            DB_PASS = ini.get("DATABASE","DB_PASS", String.class);
            DB_PATH = ini.get("DATABASE","DB_PATH", String.class);
            DB_KEY_ALGORITHM = ini.get("DATABASE","DB_KEY_ALGORITHM", String.class);
            DB_KEY = ini.get("DATABASE","DB_KEY", String.class);
            DB_USER = ini.get("DATABASE","DB_USER", String.class);


            logger.info("Import of file settings.ini was successful.");

        }catch (FileNotFoundException e){
            logger.error("We cant find your settings file.\r\n" +
                         "Please create your settings file or check its name");
            e.printStackTrace();
            System.exit(0);
        }catch (NullPointerException e){
            e.printStackTrace();
            System.exit(0);
            logger.error("We cant import settings from file.\r\n" +
                    "Please check your settings file :"+SETTING_FILE_PATH);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(0);
            logger.error("We cant open file.\r\n" +
                    "Please check your settings file :"+SETTING_FILE_PATH);
        }

    }


    public static int getPORT() {
        return PORT;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static int getAsymKeySize() {
        return ASYM_KEY_SIZE;
    }

    public static int getHashSize() {
        return HASH_SIZE;
    }

    public static int getHashIter() {
        return HASH_ITER;
    }

    public static int getSymKeySize() {
        return SYM_KEY_SIZE;
    }

    public static String getAsymAlgorithm() {
        return ASYM_ALGORITHM;
    }

    public static String getDbKey() {
        return DB_KEY;
    }

    public static String getDbKeyAlgorithm() {
        return DB_KEY_ALGORITHM;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static String getDbPass() {
        return DB_PASS;
    }

    public static String getDbPath() {
        return DB_PATH;
    }

    public static String getHashAlgorithm() {
        return HASH_ALGORITHM;
    }

    public static String getSymAlgorithm() {
        return SYM_ALGORITHM;
    }

    public static int getSaltSize() {
        return SALT_SIZE;
    }

    public static String getSaltAlgorithm() {
        return SALT_ALGORITHM;
    }
}
