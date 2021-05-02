import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.InputStreamReader;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class EncryptJsonFile {
    static void encrypt(String path) {
        try {
            // load file to buffered writer
            FileInputStream iFile = new FileInputStream(path);
            InputStreamReader iReader = new InputStreamReader(iFile);
            BufferedReader bFile = new BufferedReader(iReader);

            // initialize encryption stream objects

            
            PBEKeySpec pbeKeySpec = new PBEKeySpec(new char[]{'1', '2', '3'});
            SecretKeyFactory secretKeyFactory = SecretKeyFactory
                    .getInstance("PBEWithSHA1AndDESede");
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
            Cipher c = Cipher.getInstance("PBEWithSHA1AndDESede");
            c.init(Cipher.ENCRYPT_MODE, secretKey);

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
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

}
