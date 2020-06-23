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
public class DVDLibraryInvalidIdException extends Exception {
    
    public DVDLibraryInvalidIdException(String message) {
        super(message);
    }

    public DVDLibraryInvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
