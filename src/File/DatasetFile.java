package File;

import AppUtils.UserInput;

import java.util.List;

public class DatasetFile extends java.io.File{

    // fields
    private List<String> fileInList;

    // constructor
    public DatasetFile(String pathname) {
        super(pathname);
        System.out.println(">>DatasetFile: New DatasetFile created!");
        this.fileInList = UserInput.convertFileToList(pathname);
    }

    // getter
    public List<String> getFileInList() {
        return fileInList;
    }
}