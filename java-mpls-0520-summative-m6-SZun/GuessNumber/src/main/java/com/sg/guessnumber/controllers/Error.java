/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.controllers;

import java.time.LocalDateTime;
/**
 *
 * @author samg.zun
 */


public class Error {

    private String message;

    Error(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

}
