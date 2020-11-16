package AppUtils;

import File.ConfigurationFile;
import File.DatasetFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInput{

    private static final Scanner scanner = new Scanner(System.in);


    // read user's choice for file type (.txt, .word, .csv...)
    public static int inputFileType() throws Exception {

        // print message
        Printer.printMessageForInput("fileType");

        // read user's input
        int choice = scanner.nextInt();
        scanner.nextLine();

        // method to check user's file-type and find
        checkFileType(choice);

        //

        return 0;
    }

    // read user's fileName and creates a new ConfigurationFile
    public static ConfigurationFile inputConfig(){

        // print message
        Printer.printMessageForInput("config");

        // input file name
        String fileName = scanner.nextLine();

        // find file path
        String filePath = System.getProperty("user.dir") + "\\src\\" + fileName;

        // create config file
        ConfigurationFile configFile = new ConfigurationFile(filePath);
        System.out.println("New configuration file: " + fileName +
                           "\n at path: " + filePath + "\n");

        //AppUtils.Printer.printList(configFile.getFileToListOfStrings());

        return configFile;
    }

    // read user's fileName and creates a new DatasetFile
    public static DatasetFile inputDataset(){
        // print message
        Printer.printMessageForInput("dataset");
        // input file name
        String fileName = scanner.nextLine();

        // find file path
        String filePath = System.getProperty("user.dir") + "\\src\\" + fileName;

        // create config file
        DatasetFile datasetFile = new DatasetFile(filePath);
        System.out.println("New dataset file: " + fileName +
                "\n at path: " + filePath + "\n");

        //AppUtils.Printer.printList(configFile.getFileToListOfStrings());

        return datasetFile;
    }

    // converts given file into a list of strings
    public static List<String> convertFileToList(String filePath) {
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
            System.out.println(">>convertFileToList(): File converted successfully to a list of strings");
            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filePath);
            e.printStackTrace();
            return null;
        }
    }

    // checks for user's input and call methods for each one
    public static void checkFileType(int choice) throws Exception {

        if (choice == 1){
            // read user's input for config file
            ConfigurationFile configurationFile = UserInput.inputConfig();

            // read user's input for dataset file
            DatasetFile datasetFile = UserInput.inputDataset();

            // read user's inputs for encryption
            List<Integer> choices = UserInput.inputEncryptionChoices();
            System.out.println(choices.get(0) + " " + choices.get(1));
            // encrypt data and create new file
            Encryption.encryptFile(configurationFile, datasetFile, choices);

        }else if (choice == 2){
            System.out.println("\nThere is no implementation yet!\n Please select another type of file:\n");
            UserInput.inputFileType();
        }else{
            System.out.println("There is no implementation yet!");
            UserInput.inputFileType();
        }
    }

    // checks for user's inputs for encryption
    public static List<Integer> inputEncryptionChoices(){

        // initialize users choices
        List<Integer> choices = new ArrayList<>();

        // print message
        Printer.printMessageForInput("iv");

        // read user's input
        int iv = scanner.nextInt();
        scanner.nextLine();
        choices.add(0,iv);

        // print message
        Printer.printMessageForInput("secretKey");

        // read user's input
        int secretKey = scanner.nextInt();
        scanner.nextLine();
        choices.add(1,secretKey);

        return choices;
    }
}
