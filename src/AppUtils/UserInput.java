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

}
