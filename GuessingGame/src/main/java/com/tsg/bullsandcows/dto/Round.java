package com.tsg.bullsandcows.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Round {
    private int id;
    private int userGuess;
    private LocalDateTime guessTime;
    private int gameId;

}

