/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.services;

import com.sg.guessnumber.daos.GNGameDao;
import com.sg.guessnumber.daos.GNGameDaoException;
import com.sg.guessnumber.daos.GNRoundDaoException;
import com.sg.guessnumber.daos.GNRoundDao;
import com.sg.guessnumber.models.Game;
import com.sg.guessnumber.models.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samg.zun
 */
@Service
public class GNService {

    @Autowired
    GNGameDao gameDao;

    @Autowired
    GNRoundDao roundDao;

    public int beginGame() {
        return gameDao.createGame(generateAnswer());
    }

    public List<Game> getAllGames() throws NoGamesFoundException {
        List<Game> allGames = gameDao.getAll();

        if (allGames.isEmpty()) {
            throw new NoGamesFoundException("No games found");
        }

        allGames.forEach(g -> {
            if (!g.isFinished()) {
                g.setAnswer(null);
            }
        });

        return allGames;
    }

    public Game getGameById(int id) throws InvalidIdException, GameNotFoundException {
        validateId(id);

        Game toReturn = null;

        try {
            toReturn = gameDao.getGameById(id);
        } catch (GNGameDaoException ex) {
            throw new GameNotFoundException("The game you are looking for can not be found");
        }

        if (!toReturn.isFinished()) {
            toReturn.setAnswer(null);
        }

        return toReturn;
    }

    public List<Round> getRoundsByGameId(int gameId) throws InvalidIdException, NoRoundsFoundException {
        validateId(gameId);
        List<Round> allRounds = null;

        try {
            allRounds = roundDao.getRoundsByGameId(gameId);
        } catch (GNRoundDaoException ex) {
            throw new InvalidIdException("The ID entered is invalid");
        }

        if (allRounds.isEmpty()) {
            throw new NoRoundsFoundException("No rounds found for this game ID");
        }

        return allRounds;
    }

    public Round executeRound(int gameId, String guess) throws GNRoundDaoException, InvalidIdException, InvalidGuessException, GameNotFoundException, GameFinishedException {
        validateId(gameId);
        validateGuess(guess);

        Game currentGame = null;

        //get game
        try {
            currentGame = gameDao.getGameById(gameId);
        } catch (GNGameDaoException ex) {
            throw new GameNotFoundException("The game you are looking for can not be found");
        }
        //check if game is finished
        if (currentGame.isFinished()) {
            throw new GameFinishedException("The game you are trying to play has finished");
        }
        // get game answer
        String gameAnswer = currentGame.getAnswer();
        //calulate result
        String result = calulateResult(guess, gameAnswer);
        // create round object
        Round toAdd = new Round(gameId, guess, LocalDateTime.now(), result);
        // if result is correct, update game
        if (gameAnswer.equals(guess)) {
            try {
                gameDao.updateGameFinished(gameId);
            } catch (GNGameDaoException ex) {
                throw new GameNotFoundException("The game you are looking for can not be found");
            }
        }
        // return dao call
        return roundDao.createRound(toAdd);
    }

    private void validateId(int id) throws InvalidIdException {
        if (id == 0) {
            throw new InvalidIdException("The ID you have entered is invalid");
        }
    }

    private void validateGuess(String guess) throws InvalidGuessException {
        String[] arr = guess.split("");
        boolean hasFourUniqueCharacters = new HashSet<>(Arrays.asList(arr)).size() == 4;
        if (arr.length != 4 || !hasFourUniqueCharacters) {
            throw new InvalidGuessException("The guess you have entered is invalid");
        }

    }

    private String calulateResult(String guess, String gameAnswer) {
        int exactMatches = 0;
        int partialMatches = 0;
        String[] answerArr = gameAnswer.split("");
        String[] guessArr = guess.split("");

        for (int i = 0; i < answerArr.length; i++) {
            for (int j = 0; j < guessArr.length; j++) {

                if (answerArr[i].equals(guessArr[j])) {

                    if (i == j) {
                        exactMatches++;
                    } else {
                        partialMatches++;
                    }

                }

            }
        }

        return "e:" + exactMatches + ":p:" + partialMatches;
    }

    private String generateAnswer() {
        List<String> allNums = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            allNums.add(Integer.toString(i));
        }

        Collections.shuffle(allNums);

        String firstNum = allNums.get(0).equals("0") ? allNums.get(4) : allNums.get(0);

        return firstNum + allNums.get(1) + allNums.get(2) + allNums.get(3);

    }

}
