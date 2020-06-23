/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.controllers;

import com.sg.guessnumber.daos.GNGameDaoException;
import com.sg.guessnumber.daos.GNRoundDaoException;
import com.sg.guessnumber.models.Game;
import com.sg.guessnumber.models.GameGuess;
import com.sg.guessnumber.models.Round;
import com.sg.guessnumber.services.GNService;
import com.sg.guessnumber.services.GameFinishedException;
import com.sg.guessnumber.services.GameNotFoundException;
import com.sg.guessnumber.services.InvalidGuessException;
import com.sg.guessnumber.services.InvalidIdException;
import com.sg.guessnumber.services.NoGamesFoundException;
import com.sg.guessnumber.services.NoRoundsFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samg.zun
 */
@RestController
@RequestMapping("/api")
public class GNController {

    @Autowired
    GNService service;
    
    @PostMapping("/begin")
    public ResponseEntity<Integer> begin() {
        int gameId = service.beginGame();
        return new ResponseEntity<Integer>(gameId, HttpStatus.CREATED);
    }

    @PostMapping("/guess")
    public ResponseEntity<Round> guess(@RequestBody GameGuess gameGuess) throws GNRoundDaoException, InvalidIdException, InvalidGuessException, GameNotFoundException, GameFinishedException {
        int gameId = gameGuess.getGameId();
        int guess = gameGuess.getGuess();

        Round latestRound = service.executeRound(gameId, Integer.toString(guess));

        return new ResponseEntity<Round>(latestRound, HttpStatus.CREATED);
    }

    @GetMapping("/game")
    public ResponseEntity<List<Game>> all() throws NoGamesFoundException {
        List<Game> allGames = service.getAllGames();
        return ResponseEntity.ok(allGames);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Game> getGame(@PathVariable int id) throws InvalidIdException, GameNotFoundException {
        Game toReturn = service.getGameById(id);
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("/rounds/{gameId}")
    public ResponseEntity<List<Round>> getRounds(@PathVariable int gameId) throws InvalidIdException, NoRoundsFoundException {
        List<Round> gameRounds = service.getRoundsByGameId(gameId);
        return ResponseEntity.ok(gameRounds);
    }

}
