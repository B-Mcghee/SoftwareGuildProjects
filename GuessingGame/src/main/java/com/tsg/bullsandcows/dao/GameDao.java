package com.tsg.bullsandcows.dao;

import com.tsg.bullsandcows.dto.Game;

import java.util.List;

public interface GameDao {
    Game addGame(Game game);

    Game getOneGame(int id);

    List<Game> getAllGames();

    void editGame(Game Game);

    void deleteGame(int id);
    Game deleteAndReturnGame(int id);



}
