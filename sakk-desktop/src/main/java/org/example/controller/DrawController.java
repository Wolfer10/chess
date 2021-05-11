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
import java.util.Optional;

public class DrawController implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        ButtonType menu = new ButtonType("Vissza a főmenübe", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType restart = new ButtonType("Új játék", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType draw = new ButtonType("Elfogad", ButtonBar.ButtonData.CANCEL_CLOSE);
        // TODO Új játék vagy vissza a főmenübe gomb beadagolása
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, null, draw, ButtonType.CANCEL);
        confirm.setTitle("Döntetlen");
        confirm.setHeaderText("Elfogadod a döntetlent?");
        Optional<ButtonType> answers = confirm.showAndWait();
        answers.ifPresent(answer -> {
                    if(answer.getText().equals("Elfogad")){
                        View.gameInfo.setWinner("döntetlen");
                        View.gameInfo.setPlayedMoves(Board.moves);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                        Date date = new Date(System.currentTimeMillis());
                        System.out.println(formatter.format(date));
                        View.gameInfo.setDate(formatter.format(date));
                        GameInfoDAOImpl gameInfoDAO = GameInfoDAOImpl.getInstance();
                        gameInfoDAO.save(View.gameInfo);
                        View.mainWindow.setScene(View.mainMenuScene());
                    }
                }
        );
    }

}