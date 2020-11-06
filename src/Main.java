import File.MyFile;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        // prints welcome page
        AppUtils.printWelcomeMessage();

        // read config file
        MyFile configFile = AppUtils.inputConfigFile();

        // read dataset file
        MyFile datasetFile = AppUtils.inputDatasetFile();

        // encrypt columns and store in List<String>
        List<String> encryptedFileInList = AppUtils.encryptFile(configFile,datasetFile);
        // print encrypted list
        AppUtils.printList(encryptedFileInList);

        // create new .txt file with encrypted data
        AppUtils.createNewFile(encryptedFileInList);
    }
}
