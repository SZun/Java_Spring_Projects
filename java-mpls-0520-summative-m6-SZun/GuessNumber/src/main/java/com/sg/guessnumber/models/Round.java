/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author samg.zun
 */
public class Round {
    
    private int roundId;
    private int gameId;
    private String guess;
    private LocalDateTime time;
    private String result;

    public Round() {
    }

    public Round(int gameId, String guess, LocalDateTime time, String result) {
        this.gameId = gameId;
        this.guess = guess;
        this.time = time;
        this.result = result;
    }

    public Round(int roundId, int gameId, String guess, LocalDateTime time, String result) {
        this.roundId = roundId;
        this.gameId = gameId;
        this.guess = guess;
        this.time = time;
        this.result = result;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.roundId;
        hash = 29 * hash + this.gameId;
        hash = 29 * hash + Objects.hashCode(this.guess);
        hash = 29 * hash + Objects.hashCode(this.time);
        hash = 29 * hash + Objects.hashCode(this.result);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }

    
}
