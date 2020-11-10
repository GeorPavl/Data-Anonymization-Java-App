package File;

import java.util.List;

public class EncryptedFile extends java.io.File {

    // fields
    private List<String> fileInList;

    public EncryptedFile(String pathname) {
        super(pathname);
    }
}
