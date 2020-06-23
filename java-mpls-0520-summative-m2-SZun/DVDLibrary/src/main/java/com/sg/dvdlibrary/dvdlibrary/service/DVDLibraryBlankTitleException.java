/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary.service;

/**
 *
 * @author samg.zun
 */
public class DVDLibraryBlankTitleException extends Exception {
    
    public DVDLibraryBlankTitleException(String message){
        super(message);
    }
    
    public DVDLibraryBlankTitleException(String message, Throwable cause){
        super(message, cause);
    }
    
}
