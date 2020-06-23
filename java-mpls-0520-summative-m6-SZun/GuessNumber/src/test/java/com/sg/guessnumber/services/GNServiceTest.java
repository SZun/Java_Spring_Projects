/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.services;

import com.sg.guessnumber.TestAppConfig;
import com.sg.guessnumber.daos.GNGameInMemDao;
import com.sg.guessnumber.daos.GNRoundDaoException;
import com.sg.guessnumber.daos.GNRoundInMemDao;
import com.sg.guessnumber.models.Game;
import com.sg.guessnumber.models.Round;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author samg.zun
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
public class GNServiceTest {

    @Autowired
    GNService toTest;

    @Autowired
    GNRoundInMemDao roundDao;

    @Autowired
    GNGameInMemDao gameDao;

    public GNServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of beginGame method, of class GNService.
     */
    @Test
    public void testBeginGame() throws InvalidIdException, GameNotFoundException, NoGamesFoundException {
        int res = toTest.beginGame();
        int four = 4;

        assertEquals(four, res);

        List<Game> allGames = toTest.getAllGames();

        assertEquals(four, allGames.size());

        res = allGames.get(3).getGameId();

        assertEquals(four, res);

        res = toTest.getGameById(four).getGameId();

        assertEquals(four, res);
    }

    /**
     * Test of getAllGames method, of class GNService.
     */
    @Test
    public void testGetAllGames() throws NoGamesFoundException {
        Game expected = new Game(1, null, false);
        Game expected2 = new Game(2, "1234", true);
        Game expected3 = new Game(3, null, false);

        List<Game> res = toTest.getAllGames();

        assertEquals(3, res.size());
        assertTrue(res.contains(expected));
        assertTrue(res.contains(expected2));
        assertTrue(res.contains(expected3));
    }

    @Test
    public void testGetAllEmptyGames() throws NoGamesFoundException {
        gameDao.removeAllGames();
        try {
            toTest.getAllGames();
            fail("should hit NoGamesFoundException when no games are present");
        } catch (NoGamesFoundException ex) {
        }
    }

    /**
     * Test of getGameById method, of class GNService.
     */
    @Test
    public void testGetGameById() throws InvalidIdException, GameNotFoundException {
        Game expected = new Game(1, null, false);

        Game res = toTest.getGameById(1);

        assertEquals(expected, res);
    }

    @Test
    public void testGetGameByIdInvalidId() throws InvalidIdException, GameNotFoundException {
        try {
            toTest.getGameById(0);
            fail("should hit InvalidIdException when ID is invalid");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testGetGameByIdGameNotFound() throws InvalidIdException, GameNotFoundException {
        try {
            toTest.getGameById(10);
            fail("should hit GameNotFoundException when game can not be found");
        } catch (GameNotFoundException ex) {
        }
    }

    /**
     * Test of getRoundsByGameId method, of class GNService.
     */
    @Test
    public void testGetRoundsByGameId() throws InvalidIdException, NoRoundsFoundException {
        LocalDateTime time = Timestamp.valueOf("2017-07-23 00:00:00").toLocalDateTime();
        Round expected = new Round(1, 2, "1235", time, "e:3:p:0");
        Round expected2 = new Round(2, 2, "1235", time, "e:3:p:0");
        Round expected3 = new Round(3, 2, "1234", time, "e:4:p:0");

        List<Round> res = toTest.getRoundsByGameId(2);

        assertEquals(3, res.size());
        assertTrue(res.contains(expected));
        assertTrue(res.contains(expected2));
        assertTrue(res.contains(expected3));
    }

    @Test
    public void testGetRoundsByGameIdInvalidId() throws InvalidIdException, NoRoundsFoundException {
        try {
            toTest.getRoundsByGameId(0);
            fail("should hit InvalidIdException when ID is invalid");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testGetRoundsByGameIdNoRoundsFound() throws InvalidIdException, NoRoundsFoundException {
        try {
            toTest.getRoundsByGameId(10);
            fail("should hit NoRoundsFoundException when no rounds can be found");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of executeRound method, of class GNService.
     */
    // not working
    @Test
    public void testExecuteRound() throws GNRoundDaoException, InvalidIdException, InvalidGuessException, GameNotFoundException, GameFinishedException {
        LocalDateTime original = LocalDateTime.now();
        LocalDateTime max = original.plusSeconds(5);
        
        Round expected = new Round(4, 1, "1235", original, "e:3:p:0");

        Round res = toTest.executeRound(1, "1235");

        assertEquals(expected.getRoundId(), res.getRoundId());
        assertEquals(expected.getGameId(), res.getGameId());
        assertEquals(expected.getGuess(), res.getGuess());
        assertEquals(expected.getResult(), res.getResult());
        assertTrue(res.getTime().isBefore(max));
        assertTrue(res.getTime().isAfter(original));
        
    }

    @Test
    public void testExecuteRoundInvaldId() throws GNRoundDaoException, InvalidIdException, InvalidGuessException, GameNotFoundException, GameFinishedException {
        try {
            toTest.executeRound(0, "1234");
            fail("should hit InvalidIdException when ID is invalid");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testExecuteRoundInvalidGuessNotUnique() throws GNRoundDaoException, InvalidIdException, InvalidGuessException, GameNotFoundException, GameFinishedException {
        try {
            toTest.executeRound(1, "4444");
            fail("should hit InvalidGuessException when digits are not unique");
        } catch (InvalidGuessException ex) {
        }
    }

    @Test
    public void testExecuteRoundInvalidGuessIncorrectLength() throws GNRoundDaoException, InvalidIdException, InvalidGuessException, GameNotFoundException, GameFinishedException {
        try {
            toTest.executeRound(1, "444");
            fail("should hit InvalidGuessException when digits length is not 4");
        } catch (InvalidGuessException ex) {
        }
    }

    @Test
    public void testExecuteRoundGameNotFound() throws GNRoundDaoException, InvalidIdException, InvalidGuessException, GameNotFoundException, GameFinishedException {
        try {
            toTest.executeRound(10, "1234");
            fail("should hit GameNotFoundException when game can\'t be found");
        } catch (GameNotFoundException ex) {
        }
    }

    @Test
    public void testExecuteRoundGameFinished() throws GNRoundDaoException, InvalidIdException, InvalidGuessException, GameNotFoundException, GameFinishedException {
        try {
            toTest.executeRound(2, "1234");
            fail("should hit GameFinishedException when game has finished");
        } catch (GameFinishedException ex) {
        }
    }

}
