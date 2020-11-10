package AppUtils;

import EncryptorAES.CryptoUtils;
import EncryptorAES.EncryptorAesGcm;
import File.ConfigurationFile;
import File.DatasetFile;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Encryption {

    // scanner
    private static final Scanner scanner = new Scanner(System.in);

    // variables for encryption
    private static final int AES_KEY_BIT = 256;
    private static final int IV_LENGTH_BYTE = 12;

    // find columns for encryption in dataset file
    public static List<Integer> findColumns(List<String> configFile, List<String> datasetFile) {
        System.out.println("------ Find Columns ------");

        String firstRow = datasetFile.get(0);
        String[] columns = firstRow.split("\t");
        List<Integer> columnsToEncrypt = new ArrayList<>();
        for (int i = 0; i < columns.length; i++) {
            for (String configLine : configFile) {
                if (columns[i].equals(configLine)) {
                    columnsToEncrypt.add(i);
                }
            }
        }
        System.out.println(">>findColumns(): Columns to be encrypted:" +
                columnsToEncrypt.toString());
        return columnsToEncrypt;
    }

    // encrypt columns of dataset
    public static List<String> encryptColumns(List<Integer> indexes, List<String> datasetFile) throws Exception {

        System.out.println("\n------ Encrypt Columns ------");

        List<String> encryptedList = new ArrayList<>();
        // iterate through rows
        for (int i =1; i<datasetFile.size(); i++){
            String tempRow = datasetFile.get(i);
            String finalRow = null;
            String[] columns = tempRow.split("\t");
            // iterate through row's columns
            for (int j=0; j<columns.length; j++){
                // iterate through column's indexes
                //
                for(int index : indexes){
                    if (j == index){
                        // encrypt and decrypt need the same key.
                        // get AES 256 bits (32 bytes) key
                        SecretKey secretKey = CryptoUtils.getAESKey(AES_KEY_BIT);

                        // encrypt and decrypt need the same IV.
                        // AES-GCM needs IV 96-bit (12 bytes)
                        byte[] iv = CryptoUtils.getRandomBytes(IV_LENGTH_BYTE);
                        byte[] colToBytes = columns[j].getBytes();
                        byte[] encrypted = EncryptorAesGcm.encryptWithPrefixIV(colToBytes,secretKey,iv);
                        String encryptedToHex = CryptoUtils.hex(encrypted);
                        finalRow += encryptedToHex + "\t";
                    }
                }
            }
            encryptedList.add(finalRow);
        }
        System.out.println(">>encryptColumns(): Columns encrypted successfully\n");
        Printer.printList(encryptedList);
        return encryptedList;
    }

    // create new file to store encrypted data
    public static void createNewFile(List<String> encryptedFileInList){
        Printer.printMessageForInput("encrypted");
        String newName = scanner.nextLine(); // user's input for new file's name

        String newPath = System.getProperty("user.dir") + "\\src\\" + newName;
        File file = new File(newPath); //initialize File object and passing path as argument

        boolean result;
        try{
            result = file.createNewFile();  //creates a new file
            if(result){      // test if successfully created a new file
                System.out.println(">>createFile(): File created successfully! Path: "+file.getCanonicalPath()); //returns the path string
                FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
                for (String lineStr : encryptedFileInList){
                    fileWriter.write(lineStr + System.lineSeparator());
                }
                fileWriter.close();
                System.out.println(">>createFile(): Encrypted data saved successfully to file");
            }else{
                System.out.println("File already exist at location: "+file.getCanonicalPath());
            }
        }catch (IOException e){
            e.printStackTrace();    //prints exception if any
        }
    }

    // encrypt file
    public static void encryptFile(ConfigurationFile configFile, DatasetFile datasetFile) throws Exception {
        List<Integer> colsToEncrypt = Encryption.findColumns(configFile.getFileInList(), datasetFile.getFileInList());
        List<String> encryptedColumns = Encryption.encryptColumns(colsToEncrypt, datasetFile.getFileInList());
        Encryption.createNewFile(encryptedColumns);
    }


}