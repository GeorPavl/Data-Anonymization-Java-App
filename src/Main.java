import File.MyFile;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        // prints welcome page
        AppUtils.Printer.printWelcomeMessage();

        // read config file
        MyFile configFile = AppUtils.UserInput.inputConfigFile();

        // read dataset file
        MyFile datasetFile = AppUtils.UserInput.inputDatasetFile();

        // encrypt columns and store in List<String>
        List<String> encryptedFileInList = AppUtils.Encryptor.encryptFile(configFile,datasetFile);
        // print encrypted list
        AppUtils.Printer.printList(encryptedFileInList);

        // create new .txt file with encrypted data
        AppUtils.Encryptor.createNewFile(encryptedFileInList);
    }
}
