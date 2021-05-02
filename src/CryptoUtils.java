
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
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

    public HashMap<K, V> encrypt(String key, File file, HashMap<K, V> map) {
        return doCrypto(Cipher.ENCRYPT_MODE, key, file, map);
    }

    public HashMap<K, V> decrypt(String key, File file, HashMap<K, V> map) {
        return doCrypto(Cipher.DECRYPT_MODE, key, file, map);
    }

    /**
     * Either encrypts the hash map given as an argument, or decrypts an encrypted file of a hash map.
     * @param cipherMode determines whether in encryption mode or decryption mode
     * @param key the key with which we encrypt, it's length must be divisible by 16.
     * @param file the file from which we read into the hash map when decrypting, or into which we write the hash
     *             map into when encrypting.
     * @param mp the hash map (needed only if encrypting)
     * @return the hash map (if in encryption mode, return it unaltered, if in decryption mode - return it after
     * being decrypted from the encrypted file.
     */
    private HashMap<K, V> doCrypto(int cipherMode, String key, File file, HashMap<K, V> mp) {
        try {
            if (cipherMode == Cipher.ENCRYPT_MODE) {
                Stream<Map.Entry<K, V>> stream = mapToStream(mp);
                Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(cipherMode, secretKey);
                String res = Arrays.toString(stream.toArray());
                byte[] hasMapBytes = res.getBytes();
                byte[] encryptedBytes = cipher.doFinal(hasMapBytes);

                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(encryptedBytes);

                outputStream.close();
            } else if (cipherMode == Cipher.DECRYPT_MODE) {
                Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(cipherMode, secretKey);

                FileInputStream inputStream = new FileInputStream(file);
                byte[] inputBytes = new byte[(int) file.length()];
                inputStream.read(inputBytes);

                byte[] decryptedBytes = cipher.doFinal(inputBytes);

                ByteArrayInputStream mStream = new ByteArrayInputStream(decryptedBytes);

                ObjectInputStream ois = new ObjectInputStream(mStream);
                mp = (HashMap<K, V>) ois.readObject();
                cipher.doFinal(inputBytes);
                inputStream.close();
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return mp;

    }
}