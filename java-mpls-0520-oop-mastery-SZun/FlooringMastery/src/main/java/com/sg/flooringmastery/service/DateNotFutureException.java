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
public class DateNotFutureException extends FlooringServiceException {

    public DateNotFutureException(String message) {
        super(message);
    }
    
    public DateNotFutureException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
