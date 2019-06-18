package com.tsg.bullsandcows.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
public class ToDoControllerExceptionHandler {


    @GetMapping("/gameid/{id}")
    public int catchNumberFormat(@PathVariable int id) {
        return id;
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity handleFormatException() {
        return new ResponseEntity("That is not a valid number ", HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity handleNullException() {
        return new ResponseEntity("That item does not exists, Try again", HttpStatus.I_AM_A_TEAPOT);
    }
}

