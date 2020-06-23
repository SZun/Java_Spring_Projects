/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.daos;

import com.sg.guessnumber.TestAppConfig;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author samg.zun
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("prod")
public class GNRoundDBDaoTest {
    
    LocalDateTime time = Timestamp.valueOf("2017-07-23 00:00:00").toLocalDateTime();
    
    @Autowired
    GNRoundDao toTest;
    
    @Autowired
    JdbcTemplate jdbc;
    
    public GNRoundDBDaoTest() {
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
        jdbc.update("ALTER TABLE Rounds auto_increment = 1");

        jdbc.update("INSERT INTO Games (Answer, Finished) VALUES ('1234', 0), ('1234', 1), ('1234', 0)");
        jdbc.update("insert into Rounds (GameId, Guess, Time, Result) VALUES "
                + "( 2, '1235', '2017-07-23 00:00:00', 'e:3:p:0' ),"
                + "( 2, '1235', '2017-07-23 00:00:00', 'e:3:p:0' ),"
                + "( 2, '1234', '2017-07-23 00:00:00', 'e:4:p:0' )");
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createRound method, of class GNRoundDBDao.
     */
    @Test
    public void testCreateRound() throws GNRoundDaoException {
        Round testRound = new Round(2,"1235", time, "e:3:p:0");
        Round expected = new Round(4,2,"1235", time, "e:3:p:0");
        
        Round res = toTest.createRound(testRound);
        assertEquals(expected, res);
        
        res = toTest.getRoundsByGameId(2).get(3);
        assertEquals(expected, res);
        
    }
    
    public void testCreateRoundNullGuess() throws GNRoundDaoException {
        Round invalidRound = new Round(2,null, time, "e:3:p:0");
        try {
            toTest.createRound(invalidRound);
            fail("should throw GNRoundDaoException when guess is null");
        } catch(GNRoundDaoException ex){}
    }
    
    public void testCreateRoundBlankGuess() throws GNRoundDaoException {
        Round invalidRound = new Round(2," ", time, "e:3:p:0");
        try {
            toTest.createRound(invalidRound);
            fail("should throw GNRoundDaoException when guess is blank");
        } catch(GNRoundDaoException ex){}
    }
    
    public void testCreateRoundEmptyGuess() throws GNRoundDaoException {
        Round invalidRound = new Round(2,"", time, "e:3:p:0");
        try {
            toTest.createRound(invalidRound);
            fail("should throw GNRoundDaoException when guess is empty");
        } catch(GNRoundDaoException ex){}
    }
    
    public void testCreateRoundNullResult() throws GNRoundDaoException {
        Round invalidRound = new Round(2,"1235", time, null);
        try {
            toTest.createRound(invalidRound);
            fail("should throw GNRoundDaoException when result is null");
        } catch(GNRoundDaoException ex){}
    }
    
    public void testCreateRoundBlankResult() throws GNRoundDaoException {
        Round invalidRound = new Round(2,"1235", time, " ");
        try {
            toTest.createRound(invalidRound);
            fail("should throw GNRoundDaoException when result is blank");
        } catch(GNRoundDaoException ex){}
    }
    
    public void testCreateRoundBlankEmptyResult() throws GNRoundDaoException {
        Round invalidRound = new Round(2,"1235", time, "");
        try {
            toTest.createRound(invalidRound);
            fail("should throw GNRoundDaoException when result is empty");
        } catch(GNRoundDaoException ex){}
    }
    
    /**
     * Test of getRoundsByGameId method, of class GNRoundDBDao.
     */
    @Test
    public void testGetRoundsByGameId() throws GNRoundDaoException {
        Round expected = new Round(1,2,"1235", time, "e:3:p:0");
        Round expected2 = new Round(2,2,"1235", time, "e:3:p:0");
        Round expected3 = new Round(3,2,"1234", time, "e:4:p:0");
                
        List<Round> fromDao = toTest.getRoundsByGameId(2);
        
        assertEquals(3,fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }
    
    @Test
    public void testGetRoundsByGameIdInvalidId() throws GNRoundDaoException {
        try {
            toTest.getRoundsByGameId(0);
            fail("should hit GNRoundDaoException when gameId is invalid");
        } catch(GNRoundDaoException ex) {}
    }
    
}
