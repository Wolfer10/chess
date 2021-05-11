package dao;

import model.GameInfo;

import java.util.List;

public interface GameInfoDAO {
    List<GameInfo> findAll();
    GameInfo save(GameInfo gameInfo);
    List<GameInfo> findAll(String param);
    void delete (GameInfo gameInfo);

}
