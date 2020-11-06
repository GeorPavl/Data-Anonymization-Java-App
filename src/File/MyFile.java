package File;

import EncryptorAES.CryptoUtils;
import EncryptorAES.EncryptorAesGcm;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MyFile extends java.io.File {

    // fields
    private final List<String> fileToListOfStrings;

    // variables for encryption
    private static final int AES_KEY_BIT = 256;
    private static final int IV_LENGTH_BYTE = 12;

    // constructor
    public MyFile(String pathname) {
        super(pathname);
        this.fileToListOfStrings = writeFileToListOfStrings();
        System.out.println("New file read!\n");
    }
    
    // getters
    public List<String> getFileToListOfStrings() {
        return fileToListOfStrings;
    }

    // methods
    private List<String> writeFileToListOfStrings(){
        String filePath = this.getPath();
        List<String> records = new ArrayList<>();
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

    // find columns for encryption in dataset file
    public List<Integer> findColumns(List<String> configFile){
        System.out.println("\n------ FIND COLUMNS ------\n"
                          +  "--------------------------");
        String firstRow = this.fileToListOfStrings.get(0);
        String[] columns = firstRow.split("\t");
        List<Integer> columnsToEncrypt = new ArrayList<>();
        for (int i =0; i<columns.length; i++){
            /*


            */
            for (String configLine : configFile){
                if (columns[i].equals(configLine)){
                    columnsToEncrypt.add(i);
                }
            }
        }
        System.out.println("Columns of dataset file for encryption: " +
                           columnsToEncrypt.toString());
        return columnsToEncrypt;
    }

    // encrypt columns of dataset
    public List<String> encryptColumns(List<Integer> columnsIndexes) throws Exception {

        System.out.println("\n------ ENCRYPT COLUMNS ------\n"
                            +"-----------------------------");

        List<String> encryptedList = new ArrayList<>();
        // iterate through rows
        for (int i =1; i<this.getFileToListOfStrings().size(); i++){
            String tempRow = getFileToListOfStrings().get(i);
            String finalRow = null;
            String[] columns = tempRow.split("\t");
            // iterate through row's columns
            for (int j=0; j<columns.length; j++){
                // iterate through column's indexes
                //
                for(int index : columnsIndexes){
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
        return encryptedList;
    }

}
