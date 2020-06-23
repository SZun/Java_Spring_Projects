/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author samg.zun
 */
public class InvalidStateException extends FlooringServiceException {

    public InvalidStateException(String message) {
        super(message);
    }
    
    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
