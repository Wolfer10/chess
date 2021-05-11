package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.GameInfo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoxClickController implements EventHandler<MouseEvent> {
    CheckBox whiteBox;
    CheckBox blackBox;
    CheckBox drawBox;
    TableView tableView;
    List<GameInfo> playedGames;
    static List<GameInfo> filteredState;

    public BoxClickController(CheckBox whiteBox, CheckBox blackBox, CheckBox drawBox, TableView tableView, List<GameInfo> playedGames) {
        this.whiteBox = whiteBox;
        this.blackBox = blackBox;
        this.drawBox = drawBox;
        this.tableView = tableView;
        this.playedGames = playedGames;
    }


    @Override
    public void handle(MouseEvent actionEvent) {
        filteredState = playedGames;
        filteredState = drawFilter(filteredState).collect(Collectors.toList());
        filteredState = whiteFilter(filteredState).collect(Collectors.toList());
        filteredState = blackFilter(filteredState).collect(Collectors.toList());
        tableView.getItems().setAll(filteredState);

    }

    private Stream<GameInfo> drawFilter(List<GameInfo> playedGames){
        if (drawBox.isSelected()){
            return playedGames.stream().filter(game-> game.getWinner().equals("döntetlen"));
        } else {
            return playedGames.stream();
        }
    }

    private Stream<GameInfo> whiteFilter(List<GameInfo> playedGames){
        if (whiteBox.isSelected()){
            return playedGames.stream().filter(game-> game.getWinner().equals("fehér"));
        } else {
            return playedGames.stream();
        }
    }
    private Stream<GameInfo> blackFilter(List<GameInfo> playedGames){
        if (blackBox.isSelected()){
            return playedGames.stream().filter(game-> game.getWinner().equals("fekete"));
        } else {
            return playedGames.stream();
        }
    }
}
