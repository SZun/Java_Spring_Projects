/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.models;

import java.util.Objects;

/**
 *
 * @author samg.zun
 */
public class Game {
    
    private int gameId;
    private String answer;
    private boolean finished;

    public Game() {
    }

    public Game(int gameId, String answer, boolean finished) {
        this.gameId = gameId;
        this.answer = answer;
        this.finished = finished;
    }
    
    public Game(Game that){
        gameId = that.gameId;
        answer = that.answer;
        finished = that.finished;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.gameId;
        hash = 37 * hash + Objects.hashCode(this.answer);
        hash = 37 * hash + (this.finished ? 1 : 0);
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
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.finished != other.finished) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        return true;
    }

    
    
    
    
}
