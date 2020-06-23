/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.controllers;

import com.sg.guessnumber.daos.GNRoundDaoException;
import com.sg.guessnumber.services.NoRoundsFoundException;
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
public class RoundsControllerExceptionHandler {
    
    @ExceptionHandler(NoRoundsFoundException.class)
    public final ResponseEntity<Error> handleNoRoundsFoundException(
            NoRoundsFoundException ex,
            WebRequest request) {

        Error err = new Error(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(GNRoundDaoException.class)
    public final ResponseEntity<Error> handleGNRoundDBDaoException(
            GNRoundDaoException ex,
            WebRequest request) {

        Error err = new Error(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
}

