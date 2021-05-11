package org.example.model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DirectoryReader {
    private static DirectoryReader fileInput;

    public static DirectoryReader getInstance(){
        if (fileInput == null ){
            fileInput = new DirectoryReader();
        }
        return fileInput;
    }

    private DirectoryReader(){

    }

    private File getFileFromURL(String dir) {
        URL url = this.getClass().getClassLoader().getResource(dir);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
    }

    public File[] listFiles(String dir) throws IOException {
        File folder = fileInput.getFileFromURL("img");
        return folder.listFiles();
    }

}
