package org.example;



import javafx.application.Application;
import javafx.stage.Stage;
import org.example.view.View;



/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage Primarystage) {
        View.initView(Primarystage);
    }

    public static void main(String[] args) {
        launch();
    }









}