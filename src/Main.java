import EncryptionASE.CryptoUtils;
import EncryptionASE.EncryptorAesGcm;
import EncryptionASE.File;

import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int AES_KEY_BIT = 256;
    private static final int IV_LENGTH_BYTE = 12;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static void main(String[] args) throws Exception {

        // prints welcome message
        printWelcomeMessage();
        // creates file for encryption
        File configFile = createFile();
        configFile.printInList();

        System.out.println("-------");

        File datasetFile = createFile();
        datasetFile.printInList();

        List<String> list = configFile.getFileInList();

        List<Integer> listOfColIndexes = datasetFile.findColumns(list);

        List<String> encryptedList = datasetFile.encryptColumns(listOfColIndexes);

        for (int i=0; i<encryptedList.size(); i++){
            System.out.println(encryptedList.get(i));
        }




        /*// encrypt and decrypt need the same key.
        // get AES 256 bits (32 bytes) key
        SecretKey secretKey = CryptoUtils.getAESKey(AES_KEY_BIT);

        // encrypt and decrypt need the same IV.
        // AES-GCM needs IV 96-bit (12 bytes)
        byte[] iv = CryptoUtils.getRandomBytes(IV_LENGTH_BYTE);
        for (int i=0; i<bytesInList.size(); i++){
            System.out.println(CryptoUtils.hex(EncryptorAesGcm.encryptWithPrefixIV(bytesInList.get(i),secretKey,iv)));

        }
        */
    }
    // scanner
    private static final Scanner scanner = new Scanner(System.in);
    // welcome message
    public static void printWelcomeMessage(){
        System.out.println("\n------ Welcome to EncryptorAES-GCM ------" +
                           "\n-----------------------------------------\n");
    }

    // create file
    private static File createFile(){
        System.out.println("\n------ CREATE FILE ------" +
                           "\n--------------------------\n");
        System.out.println("Please enter the file's name you want to encrypt:");
        String fileName = scanner.nextLine();
        Path filePath = Paths.get(System.getProperty("user.dir") + "\\src\\" + fileName);
        File file = new File(fileName,filePath);

        System.out.println("File name: \n\t" + file.getFileName());
        System.out.println("File path: \n\t" + file.getFilePath());

        return file;
    }

    //
    private static List<byte[]> encryptByteList(List<byte[]> originalByteList) throws Exception {
        // encrypt and decrypt need the same key.
        // get AES 256 bits (32 bytes) key
        SecretKey secretKey = CryptoUtils.getAESKey(AES_KEY_BIT);

        // encrypt and decrypt need the same IV.
        // AES-GCM needs IV 96-bit (12 bytes)
        byte[] iv = CryptoUtils.getRandomBytes(IV_LENGTH_BYTE);

        //
        System.out.println("\n------ ENCRYPT BYTE LIST ------" +
                           "\n--------------------------\n");

        System.out.println("Original string's size in bytes for encryption: " + originalByteList.size());

        List<byte[]> encryptedByteList = new ArrayList<byte[]>();

        for (int i=0; i<originalByteList.size(); i++){
            System.out.println(originalByteList.get(i));
            encryptedByteList.add(i,EncryptorAesGcm.encryptWithPrefixIV(originalByteList.get(i),secretKey,iv));
        }
        return encryptedByteList;
    }
}
