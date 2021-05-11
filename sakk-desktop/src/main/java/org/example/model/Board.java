package org.example.model;


import javafx.scene.Group;
import javafx.scene.layout.Pane;
import org.example.controller.ReloadController;

import java.util.*;

public class Board {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;

    public static final int SQUERE_SIZE = 100;

    private Group squereGroup = new Group();
    static Group pieceGroup = new Group();
    static ArrayList<Piece> pieces = new ArrayList<Piece>();
    static Piece[][] pieceCORDS = new Piece[ROWS][COLUMNS];
    public static ArrayList<String> moves = new ArrayList<>();
    public static boolean backPlay = false;

    public Pane createBoard() {
        Pane root = new Pane();
        root.getChildren().addAll(squereGroup, pieceGroup);
        root.setPrefSize(ROWS * SQUERE_SIZE, COLUMNS * COLUMNS);
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                //create squers
                Squere squere = new Squere((x + y) % 2 == 0, x, y);
                squereGroup.getChildren().add(squere);
                //create pawns
                Piece pawn = null;

                if (y == 1) {
                    pawn = createPiece(false, "pawn", x, y);
                    pawn.loadPic(pawn.getType(), pawn.isWhite());
                    pieceCORDS[y][x] = pawn;
                    pieces.add(pawn);
                }
                if (y == 6) {
                    pawn = createPiece(true, "pawn", x, y);
                    pawn.loadPic("pawn", true);
                    pieceCORDS[y][x] = pawn;
                    pieces.add(pawn);
                }

                if (pawn != null) {
                    squere.setPiece(pawn);
                    pieceGroup.getChildren().add(pawn);
                }
            }
        }
        //create all pieces
        Piece rook1 = createPiece(false, "rook", 0, 0);
        rook1.loadPic(rook1.getType(), rook1.isWhite());


        Piece knight1 = createPiece(false, "knight", 1, 0);
        knight1.loadPic(knight1.getType(), knight1.isWhite());


        Piece bishop1 = createPiece(false, "bishop", 2, 0);
        bishop1.loadPic(bishop1.getType(), bishop1.isWhite());

        Piece queen = createPiece(false, "queen", 3, 0);
        queen.loadPic(queen.getType(), queen.isWhite());

        Piece king = createPiece(false, "king", 4, 0);
        king.loadPic(king.getType(), king.isWhite());


        Piece bishop2 = createPiece(false, "bishop", 5, 0);
        bishop2.loadPic(bishop2.getType(), bishop2.isWhite());

        Piece knight2 = createPiece(false, "knight", 6, 0);
        knight2.loadPic(knight2.getType(), knight2.isWhite());

        Piece rook2 = createPiece(false, "rook", 7, 0);
        rook2.loadPic(rook2.getType(), rook2.isWhite());

        Piece wrook1 = createPiece(true, "rook", 0, 7);
        wrook1.loadPic(wrook1.getType(), wrook1.isWhite());

        Piece wknight1 = createPiece(true, "knight", 1, 7);
        wknight1.loadPic(wknight1.getType(), wknight1.isWhite());

        Piece wbishop1 = createPiece(true, "bishop", 2, 7);
        wbishop1.loadPic(wbishop1.getType(), wbishop1.isWhite());

        Piece wqueen = createPiece(true, "queen", 3, 7);
        wqueen.loadPic(wqueen.getType(), wqueen.isWhite());

        Piece wking = createPiece(true, "king", 4, 7);
        wking.loadPic(wking.getType(), wking.isWhite());


        Piece wbishop2 = createPiece(true, "bishop", 5, 7);
        wbishop2.loadPic(wbishop2.getType(), wbishop2.isWhite());

        Piece wknight2 = createPiece(true, "knight", 6, 7);
        wknight2.loadPic(wknight2.getType(), wknight2.isWhite());

        Piece wrook2 = createPiece(true, "rook", 7, 7);
        wrook2.loadPic(wrook2.getType(), wrook2.isWhite());

        pieceGroup.getChildren().addAll(rook1, rook2, knight1, knight2, queen, king, bishop1, bishop2);
        pieceGroup.getChildren().addAll(wrook1, wrook2, wknight1, wknight2, wqueen, wking, wbishop1, wbishop2);

        pieces.addAll(new ArrayList<>(Arrays.asList(rook1, rook2, knight1, knight2, queen, king, bishop1, bishop2)));
        pieces.addAll(new ArrayList<>(Arrays.asList(wrook1, wrook2, wknight1, wknight2, wqueen, wking, wbishop1, wbishop2)));

        //cordInitPiece
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                for (Piece piece : pieces) {
                    if (piece.getY() == y && piece.getX() == x) {
                        pieceCORDS[y][x] = piece;
                    }
                }
            }
        }
        return root;
    }

    public Piece createPiece(Boolean white, String type, int x, int y) {
        Piece piece = new Piece(white, x, y, type);
        piece.setOnMouseReleased(e -> {
            Move move = new Move();
            move.tryMove(piece, e.getSceneX(), e.getSceneY());
        });
        return piece;
    }

    public static void printBoard() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                System.out.print(pieceCORDS[y][x] == null ? "   X   " : pieceCORDS[y][x].isWhite() ? "w-" + pieceCORDS[y][x].getType() + " " : "b-" + pieceCORDS[y][x].getType() + " ");
            }
            System.out.println();
        }
    }


    public void playBackPlay(int step) {
        Move move2 = new Move();
        if (step < moves.size()){
            Board.backPlay = true;
            int oldXCord = Integer.parseInt(moves.get(step).split(",")[0]);
            int oldYCord = Integer.parseInt(moves.get(step).split(",")[1]);
            int newXCord = Integer.parseInt(moves.get(step).split(",")[2]);
            int newYCord = Integer.parseInt(moves.get(step).split(",")[3]);
            System.out.println(oldYCord + " " + oldXCord);
            System.out.println(newYCord + " " + newXCord);
            move2.tryMove(pieceCORDS[oldYCord][oldXCord], newXCord *100 , newYCord*100 );
            Board.printBoard();
        }
    }

    public Pane restart() {
        reset();
        return createBoard();
    }

    public void reset() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                pieceCORDS[y][x] = null;
            }
        }
        pieces.clear();
        pieceGroup.getChildren().clear();
        squereGroup.getChildren().clear();
    }
}
