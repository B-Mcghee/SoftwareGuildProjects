package com.tsg.bullsandcows.controller;

import com.tsg.bullsandcows.dto.Game;
import com.tsg.bullsandcows.dto.Round;
import com.tsg.bullsandcows.service.Referee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bullsandcows")
public class BCcontroller {

    @Autowired
    private Referee referee;

    @PostMapping("/startgame")
    @ResponseStatus(HttpStatus.CREATED)
    public String startGame(){
        return " Your GameID is: " + referee.startGame();

    }


    @PostMapping("/guess/gameid/{id}/userguess/{guess}")
    @ResponseStatus(HttpStatus.CREATED)
    public String guessAttempt(@PathVariable int id, @PathVariable String guess){

        Game gameRequest = referee.getNumber(id);
        String number = gameRequest.getRandomNumber().toString();

        return referee.guessComparison(number, guess, gameRequest);
    }
    @GetMapping("/gameid/{id}")
    public Game getOneGame(@PathVariable int id){
        return referee.displayGame(id);

    }
    @GetMapping("/game")
     public List<Game> allGames() {
        return referee.getAllGames();

    }

    @GetMapping("/rounds/gameid/{id}")
    public List<Round> getallRounds(@PathVariable int id){
        Game game = referee.getNumber(id);
        return referee.getAllRoundsByGameId(game);

    }

}
