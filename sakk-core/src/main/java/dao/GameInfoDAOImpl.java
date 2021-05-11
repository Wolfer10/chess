package dao;

import config.GameInfoConfig;
import model.GameInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameInfoDAOImpl implements GameInfoDAO {

    private static final String SELECT_ALL_GAMES = "SELECT * FROM GAMES";
    private static final String INSERT_GAME = "INSERT INTO GAMES (BLACKPLAYER, WHITEPLAYER, DATE, WINNER) VALUES (?,?,?,?)";
    private static String connectionURL;

    private static GameInfoDAOImpl instanceGameInfoDAOImpl;
    private GameInfoDAOImpl() {

    }

    public static GameInfoDAOImpl getInstance(){
        if (instanceGameInfoDAOImpl == null){
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connectionURL = GameInfoConfig.getValue("db.url");
            instanceGameInfoDAOImpl = new GameInfoDAOImpl();
        }
        return instanceGameInfoDAOImpl;
    }



    @Override
    public List<GameInfo> findAll() {
        List<GameInfo> result = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionURL);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_GAMES)
        ){
            while(rs.next()){
                GameInfo gameInfo = new GameInfo();
                gameInfo.setId(rs.getInt("ID"));
                gameInfo.setWhitePlayerName(rs.getString("WHITEPLAYER"));
                gameInfo.setBlackPlayerName(rs.getString("BLACKPLAYER"));
                gameInfo.setDate(rs.getString("DATE"));
                gameInfo.setWinner(rs.getString("WINNER"));
                result.add(gameInfo);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public GameInfo save(GameInfo gameInfo) {
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement game = c.prepareStatement(INSERT_GAME, Statement.RETURN_GENERATED_KEYS);
        ){
            System.out.println("SAVE here");
            game.setString(1, gameInfo.getBlackPlayerName());
            game.setString(2, gameInfo.getWhitePlayerName());
            game.setString(3, gameInfo.getDate());
            game.setString(4, gameInfo.getWinner());
            ResultSet genKeys = game.getGeneratedKeys();
            int affectedRows = game.executeUpdate();
            if(affectedRows == 0){
                return null;
            }
            if(genKeys.next()){
                gameInfo.setId(genKeys.getInt(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return gameInfo;
    }



    @Override
    public List<GameInfo> findAll(String param) {
        return null;
    }

    @Override
    public void delete(GameInfo gameInfo) {

    }
}
