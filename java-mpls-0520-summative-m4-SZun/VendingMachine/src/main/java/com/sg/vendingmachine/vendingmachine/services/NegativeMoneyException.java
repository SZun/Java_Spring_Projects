/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.services;

/**
 *
 * @author samg.zun
 */
public class NegativeMoneyException extends Exception {
    
    public NegativeMoneyException(String message){
        super(message);
    }
    
    public NegativeMoneyException(String message, Throwable cause){
        super(message, cause);
    }
    
}
