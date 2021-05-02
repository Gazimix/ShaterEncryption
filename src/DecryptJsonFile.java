import javax.crypto.*;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import java.io.FileReader;
import java.io.DataInputStream;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class DecryptJsonFile {
    static void decrypt(String path) {
        try {
            // load file to buffered reader

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

            FileInputStream iFile = new FileInputStream(path);
            CipherInputStream input = new CipherInputStream(iFile, c);

            final InputStreamReader r = new InputStreamReader(input);
            final BufferedReader reader = new BufferedReader(r);


            FileWriter w = new FileWriter("DecryptedFile.json");
            BufferedWriter oStream = new BufferedWriter(w);
            String line;
            while ((line = reader.readLine()) != null) {
                oStream.write(line);
            }
            reader.close();
            input.close();
            oStream.close();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
