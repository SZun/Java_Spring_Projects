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
public class GameNotFoundException extends Exception {

    public GameNotFoundException(String message) {
        super(message);
    }
    
    public GameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
