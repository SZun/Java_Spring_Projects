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
public class OrderValidationException extends FlooringServiceException {

    public OrderValidationException(String message) {
        super(message);
    }
    
    public OrderValidationException(String message, Throwable cause) {
        super(message);
    }
    
}
