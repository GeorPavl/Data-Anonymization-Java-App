import AppUtils.Printer;
import AppUtils.UserInput;

public class Main {

    public static void main(String[] args) throws Exception {

        // print welcome message
        Printer.printAppMessage("welcome");

        // user's choice for file type
        // if type = txt: execute, if type != txt: message)
        UserInput.inputFileType();

        // print closing message
        Printer.printAppMessage("close");
    }
}
