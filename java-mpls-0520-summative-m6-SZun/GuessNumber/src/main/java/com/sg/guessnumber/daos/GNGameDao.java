/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.daos;

import com.sg.guessnumber.models.Game;
import com.sg.guessnumber.models.Round;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface GNGameDao {

    public Game getGameById(int id) throws GNGameDaoException;

    public List<Game> getAll();

    public boolean updateGameFinished(int gameId) throws GNGameDaoException;

    public int createGame(String generatedAnswer);
    
}
