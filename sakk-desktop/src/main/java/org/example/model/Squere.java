package org.example.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Squere  extends Rectangle {

    private Piece piece;

    public boolean hasPiece(){
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Squere(boolean white, int x, int y){
            setWidth(Board.SQUERE_SIZE);
            setHeight(Board.SQUERE_SIZE);
            relocate(x * Board.SQUERE_SIZE, y * Board.SQUERE_SIZE);
            setFill(white ? Color.rgb(232,235, 239) : Color.rgb(125, 135, 150) );

    }

}
