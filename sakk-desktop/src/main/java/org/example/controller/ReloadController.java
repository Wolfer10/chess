package org.example.controller;

import javafx.event.ActionEvent;


import javafx.event.EventHandler;
import org.example.model.Board;
import org.example.view.View;

public class ReloadController implements EventHandler<ActionEvent> {

    private static int step = 0;
    @Override
    public void handle(ActionEvent actionEvent) {
        Board board = new Board();
        try {
            if(step == 0){
                View.playBackBoard(board);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
