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
public class NoRoundsFoundException extends Exception {

    public NoRoundsFoundException(String message) {
        super(message);
    }
    
    public NoRoundsFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
