package org.example.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.model.Board.SQUERE_SIZE;

public class Piece extends StackPane {

    private boolean white;

    private int x;
    private int y;

    private double xpos;
    private double ypos;

    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWhite() {
        return white;
    }

    public double getXpos() {
        return xpos;
    }

    public void setXpos(double xpos) {
        this.xpos = xpos;
    }

    public double getYpos() {
        return ypos;
    }

    public void setYpos(double ypos) {
        this.ypos = ypos;
    }


    public Piece(boolean white, int x, int y, String type) {
        this.white = white;
        this.x = x;
        this.y = y;
        this.type = type;
        this.xpos = x * SQUERE_SIZE + SQUERE_SIZE / 10.0;
        this.ypos = y * SQUERE_SIZE + SQUERE_SIZE / 10.0;
        relocate(xpos, ypos);

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - 230.5, e.getSceneY() - 30.5);
        });
    }

    public ImageView loadPic(String type, boolean white) throws IllegalArgumentException {
        getChildren().clear();
        String fileName = white ? "white" + "-" + type + ".png" : "black" + "-" + type + ".png";
        DirectoryReader fileInput = DirectoryReader.getInstance();
        try {
            List<Image> rawImages = ImageLoader.loadImagesFromFiles(fileInput.listFiles("img"));
            for (Image rawImage : rawImages) {
                if(rawImage.getUrl().endsWith(fileName)){
                    ImageView imageView = new ImageView(rawImage);
                    imageView.setFitHeight(SQUERE_SIZE / 1.25);
                    imageView.setFitWidth(SQUERE_SIZE / 1.25);
                    getChildren().addAll(imageView);
                    return imageView;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Nem található ilyen bábú!");
    }

}
