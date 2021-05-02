import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.InputStreamReader;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;


public class EncryptJsonFile {
    static void encrypt(String path) {
        try {
            // load file to buffered writer
            FileInputStream iFile = new FileInputStream(path);
            InputStreamReader iReader = new InputStreamReader(iFile);
            BufferedReader bFile = new BufferedReader(iReader);

            // initialize encryption stream objects

            byte[] salt = new byte[8];
            SecureRandom altr = new SecureRandom();
            altr.nextBytes(salt);

            PBEKeySpec pbeKeySpec = new PBEKeySpec(new char[]{'1', '2', '3'});
            SecretKeyFactory secretKeyFactory = SecretKeyFactory
                    .getInstance("PBEWithSHA1AndDESede");
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
            Cipher c = Cipher.getInstance("PBEWithSHA1AndDESede");

            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 99999);

            c.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);

            FileOutputStream fReader = new FileOutputStream("EncryptedFile");
            CipherOutputStream efile = new CipherOutputStream(fReader, c);
            final OutputStreamWriter r = new OutputStreamWriter(efile);
            final BufferedWriter writer = new BufferedWriter(r);
            String buffer;

            while ((buffer = bFile.readLine()) != null)
            {
                writer.write(buffer);
            }
            bFile.close();
            writer.close();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

}
