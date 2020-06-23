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
public class NullDateException extends FlooringServiceException {

    public NullDateException(String message) {
        super(message);
    }
    
    public NullDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
