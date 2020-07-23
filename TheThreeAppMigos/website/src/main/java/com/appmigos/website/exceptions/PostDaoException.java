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
public class PostDaoException extends Exception {
    
    public PostDaoException(String message){
        super(message);
    }
    
    public PostDaoException(String message, Throwable cause){
        super(message, cause);
    }
}
