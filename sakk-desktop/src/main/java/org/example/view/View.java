package org.example.view;

import dao.GameInfoDAOImpl;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameInfo;
import org.example.App;
import org.example.controller.*;
import org.example.model.Board;

import java.util.List;

public class View {
    public static GameInfo gameInfo = new GameInfo();
    public static Stage mainWindow;
    public static List<GameInfo> filteredState;


    public static Scene initView(Stage Primarystage){
        Scene scene = mainMenuScene();
        mainWindow = Primarystage;
        mainWindow.setResizable(false);
        mainWindow.setScene(scene);
        mainWindow.show();
        return scene;
    }

    public static Scene setNamesScene(){
        BorderPane borderPane = new BorderPane();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField blackP = new TextField();
        blackP.setPromptText("Fekete játékos input");
        TextField whiteP = new TextField();
        whiteP.setPromptText("Fehér Játékos input");

        grid.add(new Label("Fekete játékos:"), 0, 0);
        grid.add(blackP, 1, 0);
        grid.add(new Label("Fehér Játékos:"), 0, 1);
        grid.add(whiteP, 1, 1);

        Button next = new Button("Tovább");
        Button back = new Button("Vissza");

        grid.add(next,1,2);
        grid.add(back, 2,2);

        next.disableProperty().bind(
                Bindings.createBooleanBinding( () ->
                        blackP.getText().trim().isEmpty() || whiteP.getText().trim().isEmpty() , blackP.textProperty(), whiteP.textProperty())
        );
        next.setOnAction(e -> {
            gameInfo.setBlackPlayerName(blackP.getText());
            gameInfo.setWhitePlayerName(whiteP.getText());
            mainWindow.setScene(boardScene());
        });

        back.setOnAction(e -> mainWindow.setScene(mainMenuScene()));
        borderPane.setCenter(grid);
        grid.setAlignment(Pos.CENTER_RIGHT);

        return new Scene(borderPane, 500, 200);
    }

    public static Scene mainMenuScene(){
        BorderPane borderPane = new BorderPane();
        VBox menu = new VBox();
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> mainWindow.setScene(setNamesScene()));

        Button historyButton = new Button("Eddigi lejatszott meccsek");
        historyButton.setOnAction(e -> mainWindow.setScene(setHistoryScene()));

        menu.getChildren().addAll(startButton,historyButton);
        menu.setSpacing(50);
        borderPane.setCenter(menu);
        menu.setAlignment(Pos.CENTER);

        return new Scene(borderPane, 200, 200);
    }

    public static Scene boardScene(){

        Board board = new Board();
        board.reset();
        Pane table = board.createBoard();
        Button whiteResign = new Button("Feladás");
        Text whitePlayer = new Text(gameInfo.getWhitePlayerName());
        whiteResign.setOnAction(new ResignController(true));
        VBox vbox = new VBox();
        vbox.getChildren().addAll(whitePlayer, whiteResign);
        vbox.setSpacing(10);


        Button blackResign = new Button("Feladás");
        blackResign.setOnAction(new ResignController(false));
        Text blackPlayer = new Text(gameInfo.getBlackPlayerName());

        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(blackPlayer, blackResign);
        vbox2.setSpacing(10);

        AnchorPane anchorPaneButtons = new AnchorPane();
        Button draw = new Button("Döntetlen");

        draw.setOnAction(new DrawController());
        anchorPaneButtons.getChildren().addAll(vbox,vbox2, draw);
        AnchorPane.setTopAnchor(vbox,700.0);
        AnchorPane.setBottomAnchor(vbox2,630.0);
        AnchorPane.setTopAnchor(draw,400.0);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(anchorPaneButtons,table);
        AnchorPane.setRightAnchor(table, 5.0);
        AnchorPane.setLeftAnchor(anchorPaneButtons,70.0);
        return new Scene(anchorPane, 1000, 800);
    }
    private static Scene setHistoryScene() {
        TableView tableView = new TableView();

        TableColumn<GameInfo, String> column1 = new TableColumn<>("Fekete játékos");
        column1.setCellValueFactory(new PropertyValueFactory<>("blackPlayerName"));
        TableColumn<GameInfo, String> column2 = new TableColumn<>("Fehér játékos");
        column2.setCellValueFactory(new PropertyValueFactory<>("whitePlayerName"));
        TableColumn<GameInfo, String> column3 = new TableColumn<>("Kimenetel:");
        column3.setCellValueFactory(new PropertyValueFactory<>("winner"));
        TableColumn<GameInfo, String> column4 = new TableColumn<>("Dátum");
        column4.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        GameInfoDAOImpl gameInfoDAO =  GameInfoDAOImpl.getInstance();
        List<GameInfo> playedGames = gameInfoDAO.findAll();
        tableView.getItems().addAll(playedGames);

        filteredState = playedGames;

        TextField whiteNameSearch = new TextField();
        Text whiteNameSearchText = new Text("Fehér játékos:");
        HBox whiteWrapper = new HBox(whiteNameSearchText, whiteNameSearch);
        whiteWrapper.setSpacing(10.0);

        TextField blackNameSearch = new TextField();
        Text blackNameSearchText = new Text("Fekete játékos:");
        HBox blackWrapper = new HBox(blackNameSearchText, blackNameSearch);
        blackWrapper.setSpacing(10.0);

        DatePicker datePicker = new DatePicker();
        Text dateSearchText = new Text("Dátum:");
        Button dateDelete = new Button("Dátum törlése");
        HBox dateWrapper = new HBox(dateSearchText, datePicker, dateDelete);
        dateWrapper.setSpacing(10.0);

        CheckBox blackBox = new CheckBox();
        Text winnerText = new Text("Kimenetele:");
        blackBox.setText("fekete");
        CheckBox whiteBox = new CheckBox();
        whiteBox.setText("fehér");
        CheckBox drawBox = new CheckBox();
        drawBox.setText("döntetlen");

        HBox winnerWrapper = new HBox(winnerText, blackBox, whiteBox, drawBox);
        winnerWrapper.setSpacing(10.0);


        SearchController searchController = new SearchController(whiteNameSearch,blackNameSearch,tableView,playedGames);
        whiteNameSearch.setOnKeyReleased(searchController);
        blackNameSearch.setOnKeyReleased(searchController);

        BoxClickController boxClickController = new BoxClickController(whiteBox, blackBox, drawBox, tableView, playedGames);
        drawBox.setOnMouseReleased(boxClickController);
        blackBox.setOnMouseReleased(boxClickController);
        whiteBox.setOnMouseReleased(boxClickController);
        datePicker.setOnAction(new DateController(datePicker, tableView, playedGames));
        dateDelete.setOnAction(new RemoveDateController(datePicker,tableView, playedGames));

        Button backButton = new Button("vissza");
        backButton.setOnAction(e -> mainWindow.setScene(mainMenuScene()));

        VBox vbox = new VBox(blackWrapper, whiteWrapper, winnerWrapper, dateWrapper,backButton, tableView);

        return new Scene(vbox, 425,500);
    }



    public static void playBackBoard(Board board) {
        Pane table = board.restart();
        VBox vbox = new VBox();
        Button button = new Button("Visszajátszik");
        button.setOnAction(new ReloadController());
        vbox.getChildren().add(button);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(button,table);
        AnchorPane.setRightAnchor(table, 5.0);
        var scene = new Scene(anchorPane, 1000, 800);
        View.mainWindow.setScene(scene);
    }
}
