package model;

import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {
    int id;
    String blackPlayerName;
    String whitePlayerName;
    String winner;
    String date;
    ArrayList<String> playedMoves;


    @Override
    public String toString() {
        return "GameInfo{" +
                "blackPlayerName='" + blackPlayerName + '\'' +
                ", whitePlayerName='" + whitePlayerName + '\'' +
                ", victorious='" + winner + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public GameInfo() {
    }

    public ArrayList<String> getPlayedMoves() {
        return playedMoves;
    }

    public void setPlayedMoves(ArrayList<String> playedMoves) {
        this.playedMoves = playedMoves;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlackPlayerName() {
        return blackPlayerName;
    }

    public void setBlackPlayerName(String blackPlayerName) {
        this.blackPlayerName = blackPlayerName;
    }

    public String getWhitePlayerName() {
        return whitePlayerName;
    }

    public void setWhitePlayerName(String whitePlayerName) {
        this.whitePlayerName = whitePlayerName;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
