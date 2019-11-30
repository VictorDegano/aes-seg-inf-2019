import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class AESEncryptor {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8); // Codificacion de los Char
            sha = MessageDigest.getInstance("SHA-1"); // La funcion Hash cryptografica
            key = sha.digest(key); // Hashea la clave
            key = Arrays.copyOf(key, 16); // se queda con los primeros 16 generados del hash
            secretKey = new SecretKeySpec(key, "AES"); // Crea la clave secretad con la clave generada y el algoritom AES
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encryptString(String stringToEncrypt, String secret) {

        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            //Inicia el cipher en modo de encriptacion con la clave secreta AES generada
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return Base64.getEncoder()
                    .encodeToString(
                            cipher.doFinal(
                                    stringToEncrypt.getBytes(StandardCharsets.UTF_8)
                            )
                    );
        } catch (BadPaddingException
                | IllegalBlockSizeException
                | InvalidKeyException
                | NoSuchPaddingException
                | NoSuchAlgorithmException e) {
            System.out.println("Encrypting Error: " + e.toString());
        }

        return "";
    }

    public static String decryptString(String stringToDecrypt, String secret) {

        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            //Inicia el cipher en modo de encriptacion con la clave secreta AES generada
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return new String(cipher.doFinal(Base64.getDecoder().decode(stringToDecrypt)));

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            System.out.println("Decrypting Error: " + e.toString());
        }

        return null;
    }

    public static void encryptFile(String path, String directoryToSave, String key) {
        try {
            List<String> fileContent = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            Optional<String> result = fileContent.stream().reduce(String::concat);

            if (result.isPresent()) {
                String encryptedString = encryptString(result.get(), key);

                writeFile(directoryToSave, encryptedString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decryptFile(String path, String directoryDecrypt, String key) {
        try {
            List<String> fileContent = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            Optional<String> result = fileContent.stream().reduce(String::concat);

            if (result.isPresent()) {
                String decryptedString = decryptString(result.get(), key);

                writeFile(directoryDecrypt, decryptedString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(String directoryToSave, String encryptedString) throws IOException {
        Path pathToSave = Paths.get(directoryToSave);
        Files.deleteIfExists(pathToSave);

        File file = new File(directoryToSave);
        if (pathToSave.toFile().createNewFile()) {
            Files.write(pathToSave, Arrays.asList(encryptedString), StandardCharsets.UTF_8);
        }
    }
}
