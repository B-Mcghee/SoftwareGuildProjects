package com.tsg.bullsandcows.dao;

import com.tsg.bullsandcows.dto.Game;
import com.tsg.bullsandcows.dto.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameDaoImpl implements GameDao{

    @Autowired
    private JdbcTemplate jdbc;


    public static final class GameMapper implements RowMapper<Game>{
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException{
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setRandomNumber(rs.getInt("randomNumber"));
            game.setFinishedGame(rs.getBoolean("finishedGame"));
            return game;
        }

    }
    @Override
    @Transactional
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO Game(randomNumber, finishedgame) " +
                "VALUES(?,?)";
        jdbc.update(INSERT_GAME, game.getRandomNumber(), game.isFinishedGame());
        int newId;
        try {
            newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        }catch (DataAccessException ex){
            return null;
        }
        game.setId(newId);
        return game;
    }

    @Override
    public Game getOneGame(int id) {
        try {
            final String GET_ONE_GAME_BY_ID = "SELECT * FROM Game WHERE id = ?";
            Game game = jdbc.queryForObject(GET_ONE_GAME_BY_ID,new GameMapper(),id );
            game.setGuesses(getRoundsForGame(game));
            return game;
        }catch (DataAccessException ex){
            return null;
        }

    }

    private List<Round> getRoundsForGame(Game game){
        final String SELECT_ROUNDS_FOR_GAME = "SELECT r.* FROM game g " +
                "JOIN round r ON g.Id = r.gameId WHERE g.id = ?";
            return jdbc.query(SELECT_ROUNDS_FOR_GAME,new RoundDAOImpl.RoundMapper(),
                    game.getId());
    }

    @Override
    public List<Game> getAllGames() {
        final String GET_ALL_GAMES = "SELECT id, randomNumber, finishedgame FROM Game;";
        List<Game> allgames =  jdbc.query(GET_ALL_GAMES, new GameMapper());
        for (Game aGame: allgames){
            aGame.setGuesses(getRoundsForGame(aGame));
        }
        return allgames;
    }

    @Override
    @Transactional
    public void editGame(Game game) {
        final String UPDATE_GAME = "UPDATE Game SET finishedGame = ? WHERE id = ?";
        jdbc.update(UPDATE_GAME, game.isFinishedGame(), game.getId());
    }

    @Override
    @Transactional
    public void deleteGame(int id) {
        final String DELETE_GAME_BY_ID_FROM_ROUNDS = "DELETE FROM rounds r WHERE r.gameId = ? ";
        jdbc.update(DELETE_GAME_BY_ID_FROM_ROUNDS, id);

        final String DELETE_GAME_FROM_GAMEDB = "DELETE FROM Game g WHERE g.Id = ?";
        jdbc.update(DELETE_GAME_FROM_GAMEDB, id);
    }
    private Game getGameForRound(Game game){
        final String SELECT_GAME_FOR_ROUND = "SELECT g.* FROM Game g JOIN Round r ON g.id = r.gameId WHERE g.id = ?;";
        return jdbc.queryForObject(SELECT_GAME_FOR_ROUND, new GameMapper(), game.getId());
    }
    @Override
    public Game deleteAndReturnGame(int id) {
        return null;
    }


}
