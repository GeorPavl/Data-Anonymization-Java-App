import AppUtils.Encryption;
import AppUtils.Printer;
import AppUtils.UserInput;
import File.ConfigurationFile;
import File.DatasetFile;

public class Main {

    public static void main(String[] args) throws Exception {

        // print welcome message
        Printer.printAppMessage("welcome");

        // read user's input for config file
        ConfigurationFile configurationFile = UserInput.inputConfig();

        // read user's input for dataset file
        DatasetFile datasetFile = UserInput.inputDataset();

        // encrypt data and create new file
        Encryption.encryptFile(configurationFile, datasetFile);

        // print closing message
        Printer.printAppMessage("close");
    }
}
