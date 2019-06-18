package com.tsg.bullsandcows.dao;

import com.tsg.bullsandcows.dto.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class RoundDAOImpl implements RoundDAO {

    @Autowired
    private JdbcTemplate jdbc;


    public static final class RoundMapper implements RowMapper<Round> {
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("id"));
            round.setUserGuess(rs.getInt("userGuess"));
            round.setGuessTime(rs.getTimestamp("timestamp").toLocalDateTime());
            round.setGameId(rs.getInt("gameId"));
            return round;
        }

    }

    @Override
    @Transactional
    public Round addRound(Round round, int gameId) {
        final String INSERT_ROUND = "INSERT INTO Round(userguess, timestamp, gameId) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_ROUND, round.getUserGuess(), Timestamp.valueOf(round.getGuessTime()), gameId);
        int newId;

            newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            round.setId(newId);
        return round;
    }

    @Override
    public Round getOneRound(int id) {
        final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE id = ?";
        return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), id);

    }

    @Override
    public List<Round> getAllRounds() {
        final String GET_ALL_ROUNDS = "SELECT id, userGuess, timestamp, gameId FROM Round ORDER BY timestamp ASC;";
        return jdbc.query(GET_ALL_ROUNDS, new RoundMapper());
    }
    private Round getGameForRound(Round round){
        final String SELECT_GAME_FOR_ROUND = "SELECT g.* FROM Game g" +
                "JOIN Round r ON g.id = r.gameId WHERE g.id = ?";
        return jdbc.queryForObject(SELECT_GAME_FOR_ROUND, new RoundMapper(), round.getId());
    }



    @Override
    public void editRound(Round round) {

    }

    @Override
    public void deleteRound(int id) {

    }

    @Override
    public Round deleteAndReturnRound(int id) {
        return null;
    }
}
