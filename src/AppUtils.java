import File.MyFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AppUtils {

    // scanner
    private static final Scanner scanner = new Scanner(System.in);

    // welcome message
    public static void printWelcomeMessage(){
        System.out.println("\n------ Welcome to EncryptorAES-GCM ------" +
                           "\n-----------------------------------------\n");
    }
    // print list
    public static void printList(List<String> myList){
        for (String line : myList){
            System.out.println(line);
        }
        System.out.println();
    }

    // user's input config file
    public static MyFile inputConfigFile(){
        System.out.println("\n------ Config File ------" +
                           "\n-------------------------\n");

        // input file name
        System.out.println("Please input Config file's name: \n" +
                           "*** File must be at src file!");
        String fileName = scanner.nextLine();

        // find file path
        String filePath = System.getProperty("user.dir") + "\\src\\" + fileName;

        // create config file
        MyFile configFile = new MyFile(filePath);
        System.out.println("New config file: " + fileName +
                           "\n at path: " + filePath + "\n");

        AppUtils.printList(configFile.getFileToListOfStrings());

        return configFile;
    }
    // user's input dataset file to encrypt
    public static MyFile inputDatasetFile(){
        System.out.println("\n------ Dataset File ------" +
                           "\n--------------------------\n");

        // input file name
        System.out.println("Please input Dataset file's name: \n" +
                "*** File must be at src file!");
        String fileName = scanner.nextLine();

        // find file path
        String filePath = System.getProperty("user.dir") + "\\src\\" + fileName;

        // create config file
        MyFile datasetFile = new MyFile(filePath);
        System.out.println("New dataset file: " + fileName +
                           "\n at path: " + filePath + "\n");

        AppUtils.printList(datasetFile.getFileToListOfStrings());

        return datasetFile;
    }

    // encrypt columns
    public static List<String> encryptFile(MyFile configFile, MyFile datasetFile) throws Exception {
        List<Integer> colsToEncrypt = datasetFile.findColumns(configFile.getFileToListOfStrings());
        return datasetFile.encryptColumns(colsToEncrypt);
    }
    // create new file to store encrypted data
    public static void createNewFile(List<String> encryptedFileInList){
        System.out.println("\n------ Create New File ------" +
                           "\n-----------------------------\n");

        System.out.println("Please input new file's name:");
        String newName = scanner.nextLine(); // user's input for new file's name

        String newPath = System.getProperty("user.dir") + "\\src\\" + newName;
        File file = new File(newPath); //initialize File object and passing path as argument

        boolean result;
        try{
            result = file.createNewFile();  //creates a new file
            if(result){      // test if successfully created a new file
                System.out.println("\nFile created:\n "+file.getCanonicalPath() + "\n"); //returns the path string
                FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
                for (String lineStr : encryptedFileInList){
                    fileWriter.write(lineStr + System.lineSeparator());
                }
                fileWriter.close();
                System.out.println("Encrypted data saved successfully to file: \n" + file.getPath() + "\n");
            }else{
                System.out.println("File already exist at location: "+file.getCanonicalPath());
            }
        }catch (IOException e){
            e.printStackTrace();    //prints exception if any
        }
    }
}
