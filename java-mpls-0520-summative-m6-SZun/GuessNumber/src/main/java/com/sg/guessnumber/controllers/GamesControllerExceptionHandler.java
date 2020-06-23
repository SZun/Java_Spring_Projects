/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.controllers;

import com.sg.guessnumber.daos.GNGameDaoException;
import com.sg.guessnumber.services.GameFinishedException;
import com.sg.guessnumber.services.GameNotFoundException;
import com.sg.guessnumber.services.NoGamesFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author samg.zun
 */
@ControllerAdvice
@RestController
public class GamesControllerExceptionHandler {
    
    @ExceptionHandler(GameFinishedException.class)
    public final ResponseEntity<Error> handleGameFinishedException(
            GameFinishedException ex,
            WebRequest request) {

        Error err = new Error(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(GameNotFoundException.class)
    public final ResponseEntity<Error> handleGameNotFoundException(
            GameNotFoundException ex,
            WebRequest request) {

        Error err = new Error(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(NoGamesFoundException.class)
    public final ResponseEntity<Error> handleNoGamesFoundException(
            NoGamesFoundException ex,
            WebRequest request) {

        Error err = new Error(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(GNGameDaoException.class)
    public final ResponseEntity<Error> handleGNGameDaoException(
            GNGameDaoException ex,
            WebRequest request) {

        Error err = new Error(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
}
