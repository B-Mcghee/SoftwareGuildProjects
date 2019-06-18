package com.tsg.bullsandcows.dto;

import lombok.Data;

import java.util.List;
import java.util.Random;


@Data
public class Game {

    private int id;
    private Integer randomNumber;
    private boolean finishedGame = false;
    private List<Round> guesses;




    public Integer numberGenerator(){
        Random rGen = new Random();
        int digit1 = rGen.nextInt(10);
        int digit2 = rGen.nextInt(10);
        int digit3 = rGen.nextInt(10);
        int digit4 = rGen.nextInt(10);
        while(digit2 == digit1){
            digit2 = rGen.nextInt(10);
        }
        while(digit3 == digit1 || digit3 == digit2){
            digit3 = rGen.nextInt(10);
        }
        while(digit4 == digit1 || digit4 == digit2 || digit4 == digit3){
            digit4 = rGen.nextInt(10);
        }
        String number ="" + digit1 + digit2 + digit3 + digit4 +"";
        Integer newNumber = Integer.parseInt(number);
        return newNumber;
    }
}
