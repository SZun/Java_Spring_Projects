/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.models;

/**
 *
 * @author samg.zun
 */
public class GameGuess {
    int gameId;
    int guess;

    public GameGuess() {
    }
    
    public GameGuess(int gameId, int guess) {
        this.gameId = gameId;
        this.guess = guess;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }
    
}
