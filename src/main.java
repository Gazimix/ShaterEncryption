import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

public class main {
    public static void main(String args[]) throws FileNotFoundException {
//        EncryptJsonFile.encrypt("C:\\Users\\Chend\\IdeaProjects\\DictionaryEncryption\\shater-02052021.json");
//        DecryptJsonFile.decrypt("C:\\Users\\Chend\\IdeaProjects\\DictionaryEncryption\\EncryptedFile");
        File iFile = new File("C:\\Users\\Chend\\IdeaProjects\\DictionaryEncryption\\shater-02052021.json");
        File oFile = new File("C:\\Users\\Chend\\IdeaProjects\\DictionaryEncryption\\EncryptedFile");
        File dFile = new File("C:\\Users\\Chend\\IdeaProjects\\DictionaryEncryption\\DecryptedFile.json");
        String key = "abcdefghijklmnop";
        HashMap<String, String> mp = new HashMap<>();
        CryptoUtils<String ,String > crypt = new CryptoUtils<>();
        crypt.encrypt(key, iFile, oFile, mp);
        crypt.decrypt(key, oFile, dFile, mp);
    }
}
