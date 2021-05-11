package org.example.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

import java.util.Optional;

import static org.example.model.Board.*;

public class Move {
    /**
     * Megpróbál lépni, ha a pályán belül vagyunk és nem egy saját színű bábúra lépünk,
     * akkor végrehajtjuk a megfelelő lépést. (átváltozás/sáncolás/ütés/menetközbeni ütés)
     * Elmentjük a lépést, ha a lépés helyes.
     *
     * @param piece
     * @param MouseXPos
     * @param MouseYPos
     */
    public void tryMove(Piece piece, double MouseXPos, double MouseYPos) {
        int newXcord;
        int newYcord;
        if (!withinBoard(piece, MouseXPos, MouseYPos)) {
            return;
        }

        if (backPlay) {
            newXcord = (int) (MouseXPos / 100);
            newYcord = (int) (MouseYPos / 100);
        } else {
            newXcord = (int) ((MouseXPos - 200) / SQUERE_SIZE);
            newYcord = (int) ((MouseYPos) / SQUERE_SIZE);
        }


        if (collision(piece, newXcord, newYcord)) {
            return;
        }

        String move = newXcord + "," + newYcord;

        if (piece.getType().equals("king") && piece.isWhite()) {
            if (newXcord == 6 && newYcord == 7 || newXcord == 2 && newYcord == 7) {
                tryToCastle(piece, newXcord, newYcord);
            }
        }
        if (piece.getType().equals("king") && !piece.isWhite()) {
            if (newXcord == 6 && newYcord == 0 || newXcord == 2 && newYcord == 0) {
                tryToCastleBlack(piece, newXcord, newYcord);
            }
        }

        if (piece.getType().equals("pawn")) {
            trypromote(piece, newYcord);
            if (piece.getY() == 3 && newYcord == 2 && piece.isWhite()) {
                tryToPas(piece, newYcord, newXcord);
            }
            if (piece.getY() == 4 && newYcord == 5 && !piece.isWhite()) {
                tryToPasBlack(piece, newYcord, newXcord);
            }
        }

        pieceCORDS[piece.getY()][piece.getX()] = null;

        saveMove(piece, move);

        move(piece, newXcord, newYcord);
        pieceCORDS[piece.getY()][piece.getX()] = piece;

    }

    private boolean withinBoard(Piece piece, double xpos, double ypos) {
        if (xpos < 200 || xpos >= COLUMNS * SQUERE_SIZE + 200 || ypos < 0 || ypos > ROWS * SQUERE_SIZE + 200) {
            move(piece, piece.getX(), piece.getY());
            return false;
        }
        return true;
    }

    private boolean collision(Piece piece, int newXcord, int newYcord) {
        String move = newXcord + "," + newYcord;
        for (Piece other : pieces) {
            if ((newXcord == other.getX() && newYcord == other.getY() && piece.isWhite() != other.isWhite())) {
                killMove(other);
                pieceCORDS[piece.getY()][piece.getX()] = null;
                saveMove(piece, move);
                move(piece, newXcord, newYcord);
                pieceCORDS[piece.getY()][piece.getX()] = piece;
                if (piece.getType().equals("pawn")) {
                    trypromote(piece, newYcord);
                }
                return true;
            } else if ((newXcord == other.getX() && newYcord == other.getY() && piece.isWhite() == other.isWhite())) {
                pieceCORDS[piece.getY()][piece.getX()] = null;
                saveMove(piece, move);
                move(piece, piece.getX(), piece.getY());
                pieceCORDS[piece.getY()][piece.getX()] = piece;
                return true;
            }
        }
        return false;
    }

    private void saveMove(Piece piece, String move) {
        if (!backPlay) {
            moves.add(piece.getX() + "," + piece.getY() + "," + move);
        }
    }

    private void tryToPasBlack(Piece pawn, int newYcord, int newXcord) {
        if ((newXcord == pawn.getX() - 1 || newXcord == pawn.getX() + 1) && newYcord == pawn.getY() + 1) {
            if (pieceCORDS[newYcord - 1][newXcord] != null
                    && pieceCORDS[newYcord - 1][newXcord].getType().equals("pawn")
                    && pieceCORDS[newYcord - 1][newXcord].isWhite()) {
                killMove(pieceCORDS[newYcord - 1][newXcord]);
                System.out.println("EN PASSANT BLACK");
            }

        }
    }

    private void tryToPas(Piece pawn, int newYcord, int newXcord) {
        //System.out.println(String.valueOf(newXcord) + " " + String.valueOf(pawn.getX()-1) + " " + String.valueOf(newXcord) + " " + String.valueOf(pawn.getX()+1) + " " + String.valueOf(newYcord) + " " + String.valueOf(pawn.getY()-1));
        if ((newXcord == pawn.getX() - 1 || newXcord == pawn.getX() + 1) && newYcord == pawn.getY() - 1) {
            if (pieceCORDS[newYcord + 1][newXcord] != null
                    && pieceCORDS[newYcord + 1][newXcord].getType().equals("pawn")
                    && !pieceCORDS[newYcord + 1][newXcord].isWhite()) {
                killMove(pieceCORDS[newYcord + 1][newXcord]);
                System.out.println("EN PASSANT");
            }

        }
    }


    public void move(Piece piece, int xcord, int ycord) {
        piece.setXpos(xcord * SQUERE_SIZE + SQUERE_SIZE / 9.0);
        piece.setYpos(ycord * SQUERE_SIZE + SQUERE_SIZE / 9.0);
        piece.setX(xcord);
        piece.setY(ycord);
        piece.relocate(piece.getXpos(), piece.getYpos());
    }


    private void tryToCastleBlack(Piece king, int newX, int newY) {
        // short castle
        if (king.getY() == 0 && king.getX() == 4
                && pieceCORDS[0][7] != null
                && pieceCORDS[0][7].getType().equals("rook")
                && !pieceCORDS[0][7].isWhite()
                && newX == 6
                && newY == 0
        ) {
            if (pieceCORDS[0][5] == null && pieceCORDS[0][6] == null) {
                pieceCORDS[newY][newX] = king;
                pieceCORDS[0][5] = pieceCORDS[0][7];
                move(pieceCORDS[0][7], 5, 0);
                pieceCORDS[0][7] = null;
                return;
            }
            // long castle
        }
        if (king.getY() == 0 && king.getX() == 4
                && pieceCORDS[0][0] != null
                && pieceCORDS[0][0].getType().equals("rook") && !pieceCORDS[0][0].isWhite()) {
            if (pieceCORDS[0][1] == null && pieceCORDS[0][2] == null && pieceCORDS[0][3] == null) {
                pieceCORDS[newY][newX] = king;
                pieceCORDS[0][3] = pieceCORDS[0][0];
                move(pieceCORDS[0][0], 3, 0);
                pieceCORDS[0][0] = null;
                Board.printBoard();
            }

        }
    }


    public void tryToCastle(Piece king, int newX, int newY) {
        // short castle
        if (king.getY() == 7 && king.getX() == 4
                && pieceCORDS[7][7] != null
                && pieceCORDS[7][7].getType().equals("rook")
                && pieceCORDS[7][7].isWhite()
                && newX == 6
                && newY == 7
        ) {
            if (pieceCORDS[7][5] == null && pieceCORDS[7][6] == null) {
                pieceCORDS[newY][newX] = king;
                pieceCORDS[7][5] = pieceCORDS[7][7];
                move(pieceCORDS[7][7], 5, 7);
                pieceCORDS[7][7] = null;
                return;
            }
        }
        // long castle
        if (king.getY() == 7 && king.getX() == 4
                && pieceCORDS[7][0] != null
                && pieceCORDS[7][0].getType().equals("rook") && pieceCORDS[7][0].isWhite()) {
            if (pieceCORDS[7][1] == null && pieceCORDS[7][2] == null && pieceCORDS[7][3] == null) {
                pieceCORDS[newY][newX] = king;
                pieceCORDS[7][3] = pieceCORDS[7][0];
                move(pieceCORDS[7][0], 3, 7);
                pieceCORDS[7][0] = null;
                Board.printBoard();
            }

        }

    }

    private String promoteAlert() {
        ButtonType queen = new ButtonType("Vezér", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType rook = new ButtonType("Bástya", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType knight = new ButtonType("Huszár", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType bishop = new ButtonType("Futó", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, null, queen, rook, knight, bishop);
        confirm.setTitle("Átváltozás");
        confirm.setHeaderText("Válassz egy bábút!");
        Optional<ButtonType> answer = confirm.showAndWait();
        return answer.get().getText();
    }

    public void trypromote(Piece pawn, int newY) {
        if (newY == 0 && pawn.isWhite()) {
            String PAnswer = promoteAlert();
            switch (PAnswer) {
                case "Vezér":
                    pawn.loadPic("queen", true);
                    pawn.setType("queen");
                    break;
                case "Bástya":
                    pawn.loadPic("rook", true);
                    pawn.setType("rook");
                    break;
                case "Huszár":
                    pawn.loadPic("knight", true);
                    pawn.setType("knight");
                    break;
                case "Futó":
                    pawn.loadPic("bishop", true);
                    pawn.setType("bishop");
                    break;
            }

        }
        if (newY == 7 && !pawn.isWhite()) {
            String PAnswer = promoteAlert();
            switch (PAnswer) {
                case "Vezér":
                    pawn.loadPic("queen", false);
                    pawn.setType("queen");
                    break;
                case "Bástya":
                    pawn.loadPic("rook", false);
                    pawn.setType("rook");
                    break;
                case "Huszár":
                    pawn.loadPic("knight", false);
                    pawn.setType("knight");
                    break;
                case "Futó":
                    pawn.loadPic("bishop", false);
                    pawn.setType("bishop");
                    break;
            }
        }
    }


    public void killMove(Piece piece) {
        ArrayList<Piece> bin = new ArrayList<>();
        bin.add(piece);
        pieceCORDS[piece.getY()][piece.getX()] = null;
        Board.pieces.removeAll(bin);
        Board.pieceGroup.getChildren().remove(piece);
    }
}
