package com.tsg.bullsandcows.service;


import com.tsg.bullsandcows.dao.GameDao;
import com.tsg.bullsandcows.dao.RoundDAO;
import com.tsg.bullsandcows.dto.Game;
import com.tsg.bullsandcows.dto.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class Referee {

    @Autowired
    private RoundDAO roundList;
    @Autowired
    private GameDao gameDao;

    public int randomNumberGenerator(){
        Random rgen = new Random();



        return 0;
    }
    public String guessComparison(String number, String guess,Game game){
        if (guess.length() != 4){
            return "That does not fit the requirements of a 4 digit number ";
        }
        try{
            Integer.parseInt(guess);
        }catch (NumberFormatException ex){
            return "That is not a valid entry";
        }
        int exact = 0;
        int partial = 0;
        Round newRound = new Round();
        newRound.setUserGuess(Integer.parseInt(guess));
        newRound.setGuessTime(LocalDateTime.now().withNano(0));
        newRound.setGameId(game.getId());
        roundList.addRound(newRound, game.getId());
        for (int i = 0; i < number.length(); i++) {
            for (int j = 0; j < guess.length() ; j++) {
                if (guess.charAt(j) == number.charAt(i) && i == j){
                    exact++;
                }else if(guess.charAt(j) == number.charAt(i)){
                    partial++;
                }
            }
        }

        game.setGuesses(getAllRoundsByGameId(game));
        if (exact == 4){
            game.setFinishedGame(true);
            gameDao.editGame(game);
            return "You've gotten all numbers in the correct Position!";
        }
        else {
            return "E: " + exact + " P: " + partial;
        }
    }

    public int startGame() {
        Game game = new Game();
        int number = game.numberGenerator();
        game.setRandomNumber(number);
        Game withId = gameDao.addGame(game);

        return withId.getId();
    }


    public Game getNumber(int id) {
        Game game = gameDao.getOneGame(id);
        return game;
    }
    public Game displayGame(int id) {
        Game game = gameDao.getOneGame(id);
        if (!game.isFinishedGame()){
            game.setRandomNumber(null);
        }
        return game;
    }
    public List<Game> getAllGames(){
        List<Game> allGames = gameDao.getAllGames();

        for(Game aGame: allGames){
            if (!aGame.isFinishedGame()){
                aGame.setRandomNumber(null);
            }
        }
        return allGames;
    }

    public List<Round> getAllRoundsByGameId(Game game) {
       return roundList.getAllRounds()
               .stream()
               .filter(r -> r.getGameId() == game.getId())
               .collect(Collectors.toList());
    }

}
