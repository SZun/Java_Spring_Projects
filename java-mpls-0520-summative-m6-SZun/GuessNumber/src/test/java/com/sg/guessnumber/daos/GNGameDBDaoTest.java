/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this jdbc file, choose Tools | Templates
 * and open the jdbc in the editor.
 */
package com.sg.guessnumber.daos;

import com.sg.guessnumber.TestAppConfig;
import com.sg.guessnumber.models.Game;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author samg.zun
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("prod")
public class GNGameDBDaoTest {

    @Autowired
    GNGameDao toTest;

    @Autowired
    JdbcTemplate jdbc;

    public GNGameDBDaoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        jdbc.update("DELETE FROM Rounds");
        jdbc.update("DELETE FROM Games");

        jdbc.update("ALTER TABLE Games auto_increment = 1");

        jdbc.update("INSERT INTO Games (Answer, Finished) VALUES ('1234', 0), ('1234', 1), ('1234', 0)");
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getGameById method, of class GNGameDBDao.
     */
    @Test
    public void testGetGameByIdGP() throws GNGameDaoException {
        Game expected = new Game(1, "1234", false);
        Game expected2 = new Game(2, "1234", true);

        Game fromDao = toTest.getGameById(1);
        assertEquals(expected,fromDao);
        
        fromDao = toTest.getGameById(2);
        assertEquals(expected2,fromDao);
    }

    @Test
    public void testGetGameByIdInvalidId() throws GNGameDaoException {
        try {
            toTest.getGameById(0);
            fail("should hit GNGameDaoException when id is invalid");
        } catch(GNGameDaoException ex){}
    }
    
    @Test
    public void testGetGameByIdNotFound() throws GNGameDaoException {
        try {
            toTest.getGameById(10);
            fail("should hit GNGameDaoException when id is not found");
        } catch(GNGameDaoException ex){}
    }

    /**
     * Test of getAll method, of class GNGameDBDao.
     */
    @Test
    public void testGetAll() {
        Game expected = new Game(1, "1234", false);
        Game expected2 = new Game(2, "1234", true);
        
        List<Game> fromDao = toTest.getAll();
        
        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
    }

    /**
     * Test of updateGameFinished method, of class GNGameDBDao.
     */
    @Test
    public void testUpdateGameFinished() throws GNGameDaoException {
        Game expected = new Game(1, "1234", false);
        
        Game fromDao = toTest.getGameById(1);
        
        assertEquals(expected, fromDao);
        
        expected = new Game(1, "1234", true);
        
        toTest.updateGameFinished(1);
        
        fromDao = toTest.getGameById(1);
        
        assertEquals(expected, fromDao);
    }

    @Test
    public void testUpdateGameFinishedInvalidId() throws GNGameDaoException {
        try {
            toTest.updateGameFinished(0);
            fail("should hit GNGameDaoException when id is invalid");
        } catch(GNGameDaoException ex){}
    }

    /**
     * Test of createGame method, of class GNGameDBDao.
     */
    @Test
    public void testCreateGame() throws GNGameDaoException {
        Game expected = new Game(4, "1234", false);
        
        int res = toTest.createGame("1234");
        Game fromDao = toTest.getGameById(4);
        
        assertEquals(4, res);
        assertEquals(expected, fromDao);
    }

}
