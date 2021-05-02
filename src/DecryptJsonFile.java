import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.io.FileReader;
import java.io.DataInputStream;
import java.security.spec.InvalidKeySpecException;

public class DecryptJsonFile {
    static void decrypt(String path) {
        try {
            // load file to buffered reader

            // initialize encryption stream objects



            PBEKeySpec pbeKeySpec = new PBEKeySpec(new char[]{'1', '2', '3'});
            SecretKeyFactory secretKeyFactory = SecretKeyFactory
                    .getInstance("PBEWithSHA1AndDESede");
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
            Cipher c = Cipher.getInstance("PBEWithSHA1AndDESede");
            c.init(Cipher.ENCRYPT_MODE, secretKey);

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
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
