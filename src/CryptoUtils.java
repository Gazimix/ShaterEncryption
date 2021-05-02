
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class that encrypts or decrypts a file.
 *
 * @author www.codejava.net
 */
public class CryptoUtils<K, V> {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private Stream<Map.Entry<K, V>> mapToStream(Map<K, V> map) {
        return map.entrySet().stream();
    }

    public void encrypt(String key, File inputFile, File outputFile, HashMap<K, V> map) {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile, map);
    }

    public void decrypt(String key, File inputFile, File outputFile, HashMap<K, V> map) {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile, map);
    }

    private void doCrypto(int cipherMode, String key, File file, HashMap<K, V> mp) {
        try {
            if (cipherMode == Cipher.ENCRYPT_MODE) {
                Stream<Map.Entry<K, V>> stream = mapToStream(mp);
                Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(cipherMode, secretKey);
                String res = Arrays.toString(stream.toArray());
                byte[] inputBytes = res.getBytes();
                byte[] outputBytes = cipher.doFinal(inputBytes);

                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(outputBytes);

                outputStream.close();
            } else if (cipherMode == Cipher.DECRYPT_MODE) {
                Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(cipherMode, secretKey);

                FileInputStream inputStream = new FileInputStream(file);
                byte[] inputBytes = new byte[(int) file.length()];
                inputStream.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);
                // todo convert output bytes to hashmap
                ObjectMapper om = new ObjectMapper();
                TypeReference<Map<K,V>> tr = new TypeReference<Map<K,V>>;

                inputStream.close();
            }

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            ex.printStackTrace();
        }
    }
}