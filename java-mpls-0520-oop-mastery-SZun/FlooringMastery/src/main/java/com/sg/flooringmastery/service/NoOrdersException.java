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
public class NoOrdersException extends FlooringServiceException {

    public NoOrdersException(String message) {
        super(message);
    }
    
    public NoOrdersException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
