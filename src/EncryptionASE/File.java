package EncryptionASE;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class File{

    // fields
    private String fileName;
    private Path filePath;
    private String fileType;
    private List<String> fileInList;

    // constructors
    public File(String fileName, Path filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileInList = readFile(this.filePath.toString());
        //this.fileInList = readFile(this.filePath.toString());
    }

    // getters
    public String getFileName() {
        return fileName;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public List<String> getFileInList(){
        return fileInList;
    }

    // setters
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    // methods
    private static final int AES_KEY_BIT = 256;
    private static final int IV_LENGTH_BYTE = 12;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static List<String> readFile(String filePath) {
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

    public List<Integer> findColumns(List<String> columnsInConfig){
        System.out.println("\n------ FIND COLUMNS ------" +
                "\n--------------------------\n");
        String firstRow = this.fileInList.get(0);
        String[] columns = firstRow.split("\t");
        List<Integer> columnsToEncrypt = new ArrayList<Integer>();
        for (int i =0; i<columns.length; i++){
            for (int j=0; j<columnsInConfig.size(); j++){
                if (columns[i].equals(columnsInConfig.get(j))){
                    columnsToEncrypt.add(i);
                }
            }
        }
        return columnsToEncrypt;
    }

    public List<String> encryptColumns(List<Integer> columnsIndexes) throws Exception {
        System.out.println("\n------ ENCRYPT COLUMNS ------" +
                "\n--------------------------\n");

        List<String> encryptedList = new ArrayList<String>();
        // iterate through rows
        for (int i =1; i<this.getFileInList().size(); i++){
            String row = getFileInList().get(i);
            String tempRow = row;
            String finalRow = null;
            String[] columns = tempRow.split("\t");
            // iterate through row's columns
            for (int j=0; j<columns.length; j++){
                // iterate through column's indexes
                for (int k=0; k<columnsIndexes.size(); k++){
                    if (j == columnsIndexes.get(k)){
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

    public void printInList(){
        System.out.println("\n------ FILE IN LIST ------" +
                           "\n--------------------------\n");

        for (int i=0; i<getFileInList().size(); i++){
            System.out.println(getFileInList().get(i));
        }
    }

    public List<byte[]> toByteList(List<String> list){
        System.out.println("\n------ FILE IN LIST ------" +
                           "\n--------------------------\n");
        List<byte[]> byteList = new ArrayList<byte[]>();
        for (int i=0; i<list.size(); i++){
            byte[] rowInBytes = list.get(i).getBytes(UTF_8);
            byteList.add(i,rowInBytes);
        }

        return byteList;
    }
    public List<String> encryptFile() throws Exception {
        // encrypt and decrypt need the same key.
        // get AES 256 bits (32 bytes) key
        SecretKey secretKey = CryptoUtils.getAESKey(AES_KEY_BIT);

        // encrypt and decrypt need the same IV.
        // AES-GCM needs IV 96-bit (12 bytes)
        byte[] iv = CryptoUtils.getRandomBytes(IV_LENGTH_BYTE);

        List<String> encryptedList = null;
        for (int i=0; i<this.getFileInList().size(); i++){
            String row = this.getFileInList().get(i);
            System.out.println(row);
            byte[] rowInBytes = row.getBytes(UTF_8);
            byte[] encryptedRowInBytes = EncryptorAesGcm.encryptWithPrefixIV(rowInBytes,secretKey, iv);
            String encryptedRow = CryptoUtils.hex(encryptedRowInBytes);
            encryptedList.set(i,encryptedRow);
        }
        return encryptedList;
    }
}
