/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.daos;

import com.sg.guessnumber.models.Round;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface GNRoundDao {

    public Round createRound(Round toAdd) throws GNRoundDaoException;

    public List<Round> getRoundsByGameId(int gameId) throws GNRoundDaoException;
    
}
