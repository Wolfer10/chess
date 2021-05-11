package org.example.controller;

import dao.GameInfoDAOImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.example.App;
import org.example.model.Board;
import org.example.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResignController implements EventHandler<ActionEvent> {
    boolean white;

    public ResignController(boolean white) {
        this.white = white;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        View.gameInfo.setWinner(white ? "fekete" : "fehér");
        View.gameInfo.setPlayedMoves(Board.moves);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        View.gameInfo.setDate(formatter.format(date));

        GameInfoDAOImpl gameInfoDAO =  GameInfoDAOImpl.getInstance();
        gameInfoDAO.save(View.gameInfo);



        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.OK);
        confirm.setTitle("Játék vége");
        confirm.setHeaderText(white ? "Fekete nyert" : "Fehér nyert");
        confirm.showAndWait();
        View.mainWindow.setScene(View.mainMenuScene());
    }

}
