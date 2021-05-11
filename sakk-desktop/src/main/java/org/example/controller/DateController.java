package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import model.GameInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DateController implements EventHandler<ActionEvent> {

    DatePicker datePicker;
    TableView tableView;
    List<GameInfo> playedGames;
    static List<GameInfo> filteredState;

    public DateController(DatePicker datePicker, TableView tableView, List<GameInfo> playedGames) {
        this.datePicker = datePicker;
        this.tableView = tableView;
        this.playedGames = playedGames;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        filteredState = playedGames;
        if(datePicker.getValue() != null){
            LocalDate date = datePicker.getValue();
            List<GameInfo> filtered = playedGames.stream().filter(game-> game.getDate().startsWith(date.toString())).collect(Collectors.toList());
            tableView.getItems().setAll(filtered);
            filteredState = filtered;
        }
    }
}
