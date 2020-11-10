package File;

import AppUtils.UserInput;

import java.util.List;

public class ConfigurationFile extends java.io.File {

    // fields
    private List<String> fileInList;

    // constructor
    public ConfigurationFile(String pathname) {
        super(pathname);
        System.out.println(">>ConfigFile: New ConfigFile created!");
        this.fileInList = UserInput.convertFileToList(pathname);
    }
    // getters
    public List<String> getFileInList() {
        return fileInList;
    }
}
