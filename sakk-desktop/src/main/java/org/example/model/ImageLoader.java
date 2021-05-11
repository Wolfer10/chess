package org.example.model;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {

    private static final List<Image> images = new ArrayList<>();

    public static List<Image> loadImagesFromFiles(File[] files){
        for (File file : files) {
            images.add(new Image(file.toURI().toString()));
        }
        return images;
    }
}
