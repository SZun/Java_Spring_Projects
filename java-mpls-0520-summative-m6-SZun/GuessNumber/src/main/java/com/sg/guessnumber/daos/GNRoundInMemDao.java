/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.daos;

import com.sg.guessnumber.models.Round;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class GNRoundInMemDao implements GNRoundDao {
    
    List<Round> allRounds = new ArrayList<>();

    public GNRoundInMemDao() {
        LocalDateTime time = Timestamp.valueOf("2017-07-23 00:00:00").toLocalDateTime();
        
        allRounds.add(new Round(1,2,"1235", time, "e:3:p:0"));
        allRounds.add(new Round(2,2,"1235", time, "e:3:p:0"));
        allRounds.add(new Round(3,2,"1234", time, "e:4:p:0"));
    }
    

    @Override
    public Round createRound(Round toAdd) throws GNRoundDaoException {
        validateRound(toAdd);
        
        int nextId = allRounds.stream()
                        .mapToInt(r -> r.getRoundId())
                        .max()
                        .orElse(0) + 1;
        
        toAdd.setRoundId(nextId);
        
        allRounds.add(toAdd);
        
        return toAdd;
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId) throws GNRoundDaoException {
        if(gameId == 0){
            throw new GNRoundDaoException("Invalid ID");
        }
        
        List<Round> toReturn = allRounds.stream()
                            .filter(r -> r.getGameId() == gameId)
                            .collect(Collectors.toList());
                
        if(toReturn.isEmpty()){
            throw new GNRoundDaoException("Invalid ID");
        }
        
        return toReturn;
    }
    
    private void validateRound(Round toValidate) throws GNRoundDaoException {
        if (toValidate.getGuess() == null || toValidate.getGuess().trim().length() != 4 || 
            toValidate.getResult() == null || toValidate.getResult().trim().length() != 7) {
            throw new GNRoundDaoException("The Round is invalid");
        }
    }
    
}
