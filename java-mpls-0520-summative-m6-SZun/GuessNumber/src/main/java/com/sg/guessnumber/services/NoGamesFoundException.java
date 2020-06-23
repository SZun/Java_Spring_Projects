/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.services;

/**
 *
 * @author samg.zun
 */
public class NoGamesFoundException extends Exception {

    public NoGamesFoundException(String message) {
        super(message);
    }

    public NoGamesFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
