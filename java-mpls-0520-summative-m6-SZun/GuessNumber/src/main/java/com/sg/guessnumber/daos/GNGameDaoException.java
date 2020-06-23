/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.daos;

/**
 *
 * @author samg.zun
 */
public class GNGameDaoException extends Exception {

    public GNGameDaoException(String message) {
        super(message);
    }
    
    public GNGameDaoException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
