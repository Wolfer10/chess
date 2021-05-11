package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import model.GameInfo;

import java.util.List;

public class RemoveDateController implements EventHandler<ActionEvent> {


    DatePicker datePicker;
    TableView tableView;
    List<GameInfo> playedGames;
    static List<GameInfo> filteredState;

    public RemoveDateController(DatePicker datePicker, TableView tableView, List<GameInfo> playedGames) {
        this.tableView = tableView;
        this.playedGames = playedGames;
        this.datePicker = datePicker;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        filteredState = playedGames;
        datePicker.setValue(null);
        if(filteredState !=null){
            tableView.getItems().setAll(filteredState);
        }
    }
}
