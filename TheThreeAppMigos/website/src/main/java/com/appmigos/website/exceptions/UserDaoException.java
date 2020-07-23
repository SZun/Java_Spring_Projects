/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.exceptions;

/**
 *
 * @author Isaia
 */
public class UserDaoException extends Exception {
    
    public UserDaoException(String message){
        super(message);
    }
    
    public UserDaoException(String message, Throwable cause){
        super(message, cause);
    }
}
