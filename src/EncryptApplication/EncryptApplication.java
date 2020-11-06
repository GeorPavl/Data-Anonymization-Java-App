package EncryptApplication;

import EncryptionASE.CryptoUtils;
import EncryptionASE.EncryptorAesGcm;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EncryptApplication {

    private static final int AES_KEY_BIT = 256;
    private static final int IV_LENGTH_BYTE = 12;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static Scanner scanner = new Scanner(System.in);

    public static void printWelcomeMessage(){
        System.out.println("\n------ Welcome to EncryptorAES-GCM ------");
    }

    public static List<String> readFile(String filePath)
    {
        List<String> records = new ArrayList<String>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.add(line);
            }
            reader.close();
            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filePath);
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> askForConfigFile(){
        System.out.print("Please enter the name of dataset file:");
        String dataSetName = scanner.nextLine();
        String dataSetPath = Paths.get(System.getProperty("user.dir")) + "\\src\\" + dataSetName;

        System.out.println("Dataset file name: \n\t" + dataSetName);
        System.out.println("Dataset file path: \n\t" + dataSetPath);
        System.out.println("Dataset content: " + readFile(dataSetPath));

        List<String> fileAsList = readFile(dataSetPath);
        return fileAsList;
    }

    public static byte[] encryptListItem(String listItem) throws Exception {
        // encrypt and decrypt need the same key.
        // get AES 256 bits (32 bytes) key
        SecretKey secretKey = CryptoUtils.getAESKey(AES_KEY_BIT);

        // encrypt and decrypt need the same IV.
        // AES-GCM needs IV 96-bit (12 bytes)
        byte[] iv = CryptoUtils.getRandomBytes(IV_LENGTH_BYTE);

        byte[] encryptedText = EncryptorAesGcm.encryptWithPrefixIV(listItem.getBytes(UTF_8), secretKey, iv);
        return encryptedText;
    }
}
