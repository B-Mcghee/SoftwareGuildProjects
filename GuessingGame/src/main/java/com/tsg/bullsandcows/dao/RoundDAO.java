package com.tsg.bullsandcows.dao;

import com.tsg.bullsandcows.dto.Round;

import java.util.List;

public interface RoundDAO {

    Round addRound(Round round, int gameId);

    Round getOneRound(int id);

    List<Round> getAllRounds();

    void editRound(Round round);

    void deleteRound(int id);
    Round deleteAndReturnRound(int id);


}
