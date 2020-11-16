package AppUtils;

import java.util.List;

public class Printer {

    public static void printList(List<String> list){
        for (String line : list){
            System.out.println(line);
        }
    }

    public static void printMessageForInput(String typeOf){
        if (typeOf == "dataset"){
            System.out.println("------ Dataset File ------");
            System.out.println("Please input Dataset file's name (file @\\src): ");

        }else if (typeOf == "config"){
            System.out.println("------ Config File ------");
            System.out.println("Please input Config file's name (file @\\src): ");

        }else if (typeOf == "encrypted"){
            System.out.println("\n------ Encrypted File ------");
            System.out.println("Please input new file's name (file @\\src): ");

        }else if (typeOf == "fileType"){
            System.out.println("------ Select file's type ------");
            System.out.println("Please select type of the file you want to encrypt: ");
            System.out.println("1 -> .txt (recommended!)\n2 -> .pdf\n3 -> .csv");
        }else if(typeOf == "iv") {
            System.out.println("------ Select encryption's initial value ------");
            System.out.println("Please select encryption's initial value in bytes: ");
            System.out.println("1 -> 12 bytes\n2 -> 16 bytes");
        }else if(typeOf == "secretKey"){
            System.out.println("------ Select encryption's secret key ------");
            System.out.println("Please select encryption's secret key in bytes: ");
            System.out.println("1 -> 128 bytes\n2 -> 256 bytes");
        }else {
            System.out.println("Error message.");
        }

    }

    public static void printAppMessage(String typeOf){
        if (typeOf == "welcome"){
            System.out.println("\n------ Welcome to EncryptorAES-GCM ------" +
                               "\n-----------------------------------------\n");
        }else if (typeOf == "close"){
            System.out.println("\n-----------------------------------------\n"
                               + "-------- Close EncryptorAES-GCM ---------");
        }
    }
}
