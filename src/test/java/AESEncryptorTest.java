import org.junit.Test;

import static org.junit.Assert.*;

public class AESEncryptorTest {

    @Test
    public void whenEncryptAString_TheResultStringIsDifferentOfOriginal(){
        //Setup (Given)
        String originalString = "Una prueba de encriptacion";
        //Exercise (When)
        String encryptedString = AESEncryptor.encryptString(originalString, "key1") ;

        //Test (Then)
        assertNotEquals(originalString, encryptedString);
    }

    @Test
    public void whenDesencryptAString_TheResultStringIsTheOriginalUnencrypted() {
        //Setup (Given)
        String originalString = AESEncryptor.encryptString("Una prueba de encriptacion", "key1");

        //Exercise (When)
        String decryptedString = AESEncryptor.decryptString(originalString, "key1");

        //Test (Then)
        assertEquals("Una prueba de encriptacion",decryptedString);
    }
}