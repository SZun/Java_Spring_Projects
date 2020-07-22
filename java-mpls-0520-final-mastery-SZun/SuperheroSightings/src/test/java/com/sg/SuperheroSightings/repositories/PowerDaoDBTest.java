/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.TestAppConfig;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.exceptions.DaoException;
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
public class PowerDaoDBTest {
    
    @Autowired
    PowerDao toTest;
    
    @Autowired
    JdbcTemplate jdbc;
    
    public PowerDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        jdbc.update("DELETE FROM OrganizationsSuperheros");
        jdbc.update("DELETE FROM Organizations");
        jdbc.update("DELETE FROM Superheros");
        jdbc.update("DELETE FROM Powers");
        
        jdbc.update("ALTER TABLE Powers auto_increment = 1");
        
        jdbc.update("INSERT INTO Powers(Name) VALUES('A'),('B'),('C')");
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Power expected = new Power(1,"A");
        Power expected2 = new Power(2,"B");
        Power expected3 = new Power(3,"C");
        
        List<Power> fromDao = toTest.getAllPowers();
        
        assertEquals(3,fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }

    /**
     * Test of createPower method, of class PowerDaoDB.
     */
    @Test
    public void testCreatePower() throws DaoException {
        Power expected = new Power(4,"D");
        
        try {
            toTest.getPowerById(4);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
        
        Power fromDao = toTest.createPower(expected);
        assertEquals(expected, fromDao);
        
        fromDao = toTest.getPowerById(4);
        assertEquals(expected, fromDao);
    }

    /**
     * Test of getPowerById method, of class PowerDaoDB.
     */
    @Test
    public void testGetPowerById() throws DaoException {
        Power expected = new Power(1,"A");
        
        Power fromDao = toTest.getPowerById(1);
        
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testGetPowerByIdInvalidId() {
        try {
            toTest.getPowerById(4);
            fail("should hit DaoException when Name is invalid");
        } catch (DaoException ex) {
        }
    }
    
    /**
     * Test of getPowerByName method, of class PowerDaoDB.
     */
    @Test
    public void testGetPowerByName() throws DaoException {
        Power expected = new Power(1,"A");
        
        Power fromDao = toTest.getPowerByName("A");
        
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testGetPowerByIdInvalidName() {
        try {
            toTest.getPowerByName("Z");
            fail("should hit DaoException when Name is invalid");
        } catch (DaoException ex) {
        }
    }

    /**
     * Test of editPower method, of class PowerDaoDB.
     */
    @Test
    public void testEditPower() throws DaoException {
        Power expected = new Power(1, "Z");
        Power original = new Power(1,"A");
        
        Power fromDao = toTest.getPowerById(1);
        assertEquals(original, fromDao);
        
        assertNotEquals(0, toTest.editPower(expected));
        
        fromDao = toTest.getPowerById(1);
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testEditPowerInvalidId() {
        Power testPower = new Power(4,"A");
        assertEquals(0, toTest.editPower(testPower));
    }

    /**
     * Test of removePower method, of class PowerDaoDB.
     */
    @Test
    public void testRemovePower() throws DaoException {
        Power toRemove = new Power(1,"A");
        
        Power fromDao = toTest.getPowerById(1);
        assertEquals(toRemove, fromDao);
        
        assertNotEquals(0, toTest.removePower(1));
        
        try {
            toTest.getPowerById(1);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
    }
    
    @Test
    public void testRemovePowerInvalidId() {
        assertEquals(0, toTest.removePower(4));
    }
    
}
