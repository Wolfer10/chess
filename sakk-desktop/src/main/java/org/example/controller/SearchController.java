package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.GameInfo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchController implements EventHandler<KeyEvent> {

    TextField whiteP;
    TextField blackP;

    TableView tableView;
    List<GameInfo> playedGames;
    static List<GameInfo> filteredState;

    public SearchController(TextField whiteP, TextField blackP, TableView tableView, List<GameInfo> playedGames) {
        this.whiteP = whiteP;
        this.blackP = blackP;
        this.tableView = tableView;
        this.playedGames = playedGames;
    }

    @Override
    public void handle(KeyEvent actionEvent) {
        filteredState = playedGames;
        filteredState = whitePlayerFilter(filteredState).collect(Collectors.toList());
        filteredState = blackPlayerFilter(filteredState).collect(Collectors.toList());
        tableView.getItems().setAll(filteredState);
    }

    private Stream<GameInfo> blackPlayerFilter(List<GameInfo> playedGames) {
        return playedGames.stream().filter(game-> game.getBlackPlayerName().toLowerCase().startsWith(blackP.getText().toLowerCase()));
    }


    private Stream<GameInfo> whitePlayerFilter(List<GameInfo> playedGames){
        return playedGames.stream().filter(game-> game.getWhitePlayerName().toLowerCase().startsWith(whiteP.getText().toLowerCase()));
    }

}
