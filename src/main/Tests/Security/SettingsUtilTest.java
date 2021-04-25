package Security;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.Assert.*;

public class SettingsUtilTest {


    @Test
    public void setSettings() {


        try {
            FileWriter myWriter = new FileWriter("src\\main\\resources\\test_settings.ini");
            myWriter.write("[APPLICATION]\n" +
                    "PORT = 5010\n"+
                    "SYM_ALGORITHM = TESTSYM\n" +
                    "SYM_KEY_SIZE = 128\n" +
                    "ASYM_ALGORITHM = TESTASYM\n" +
                    "ASYM_KEY_SIZE = 3072\n" +
                    "HASH_ALGORITHM = TESTHASH\n" +
                    "HASH_SIZE = 512\n" +
                    "HASH_ITER = 10000\n" +
                    "SALT_ALGORITHM = TESTSALT\n" +
                    "SALT_SIZE = 16\n" +
                    "[DATABASE]\n" +
                    "DB_PATH = jdbc:mysql://localhost:3306/db_test\n" +
                    "DB_NAME = db_test\n" +
                    "DB_PASS = 1234\n" +
                    "DB_KEY_ALGORITHM = TESTALGODB\n" +
                    "DB_KEY = 123456F\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        SettingsUtil su = new SettingsUtil("src\\main\\resources\\test_settings.ini");

        assertEquals(5010, SettingsUtil.getPORT());
        assertEquals("TESTSYM", SettingsUtil.getSymAlgorithm());
        assertEquals(128, SettingsUtil.getSymKeySize());
        assertEquals("TESTASYM", SettingsUtil.getAsymAlgorithm());
        assertEquals(3072, SettingsUtil.getAsymKeySize());
        assertEquals("TESTHASH", SettingsUtil.getHashAlgorithm());
        assertEquals(512, SettingsUtil.getHashSize());
        assertEquals(10000, SettingsUtil.getHashIter());
        assertEquals("TESTSALT", SettingsUtil.getSaltAlgorithm());
        assertEquals(16, SettingsUtil.getSaltSize());
        assertEquals("jdbc:mysql://localhost:3306/db_test", SettingsUtil.getDbPath());
        assertEquals("db_test", SettingsUtil.getDbName());
        assertEquals("1234", SettingsUtil.getDbPass());
        assertEquals("123456F", SettingsUtil.getDbKey());
        assertEquals("TESTALGODB", SettingsUtil.getDbKeyAlgorithm());

        File testFile = new File("src\\main\\resources\\test_settings.ini");
        testFile.delete();


    }

}
