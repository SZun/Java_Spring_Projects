/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary.dao;

/**
 *
 * @author samg.zun
 */
public class DVDLibraryDAOException extends Exception {

    public DVDLibraryDAOException(String message) {
        super(message);
    }

    public DVDLibraryDAOException(String message, Throwable innerException) {
        super(message, innerException);
    }

}
