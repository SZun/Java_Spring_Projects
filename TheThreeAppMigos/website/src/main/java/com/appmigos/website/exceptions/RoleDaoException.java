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
public class RoleDaoException extends Exception{
    
    public RoleDaoException(String message){
        super(message);
    }
    
    public RoleDaoException(String message, Throwable cause){
        super(message, cause);
    }
}
