import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class main {

    public static void main(String[] args)
    {
        String originalString = "Una prueba de encriptacion";

        String encryptedString = AESEncryptor.encryptString(originalString, "key1") ;
        String decryptedString = AESEncryptor.decryptString(encryptedString, "key1") ;

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);

        String path = new File("").getAbsolutePath();
        String pathToSearch = path+"\\src\\main\\resources\\PruebaEncrypt.txt";
        String directoryToSave = path+"\\src\\main\\resources\\EncryptedFile.txt";
        AESEncryptor.encryptFile(pathToSearch, directoryToSave, "key1");

        String directoryToDecrypt = path+"\\src\\main\\resources\\DecryptedFile.txt";
        AESEncryptor.decryptFile(directoryToSave, directoryToDecrypt, "key1");
    }
}
