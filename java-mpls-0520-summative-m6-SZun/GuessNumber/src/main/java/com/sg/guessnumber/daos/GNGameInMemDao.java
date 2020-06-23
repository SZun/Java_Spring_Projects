/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.daos;

import com.sg.guessnumber.models.Game;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author samg.zun
 */
@Repository
@Profile("test")
public class GNGameInMemDao implements  GNGameDao {
    
    List<Game> allGames = new ArrayList<>();

    public GNGameInMemDao() {
        allGames.add(new Game(1, "1234", false));
        allGames.add(new Game(2, "1234", true));
        allGames.add(new Game(3, "1234", false));
    }
    
    @Override
    public Game getGameById(int id) throws GNGameDaoException {
        if(id == 0){
            throw new GNGameDaoException("Invalid game ID");
        }

        Game toCopy = allGames.stream()
                                .filter(g -> g.getGameId() == id)
                                .findFirst()
                                .orElse(null);
        if (toCopy == null){
            throw new GNGameDaoException("Invalid game ID");
        }
        
        Game toReturn = new Game(toCopy);

        return toReturn;
    }

    @Override
    public List<Game> getAll() {
        return allGames.stream()
                .map(g -> new Game(g))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateGameFinished(int gameId) throws GNGameDaoException {
        if(gameId == 0){
            throw new GNGameDaoException("Invalid game ID");
        }
        
        boolean result = false;
        Game toUpdate = getGameById(gameId);
        
        if(toUpdate != null){
            int i = allGames.indexOf(toUpdate);
            allGames.set(i, toUpdate);
            result = true;
        }
        
        return result;
    }

    @Override
    public int createGame(String generatedAnswer) {
        int nextId = allGames.stream()
                        .mapToInt(g -> g.getGameId())
                        .max()
                        .orElse(0) + 1;
        
        allGames.add(new Game(nextId, generatedAnswer, false));
        
        return nextId;
    }
    
    public void removeAllGames(){
        allGames = new ArrayList();
    }
    
}
