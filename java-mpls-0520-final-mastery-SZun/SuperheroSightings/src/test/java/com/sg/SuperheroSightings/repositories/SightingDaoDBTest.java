/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.TestAppConfig;
import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.entities.Sighting;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.DaoException;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class SightingDaoDBTest {

    @Autowired
    SightingDao toTest;

    @Autowired
    JdbcTemplate jdbc;

    LocalDate testSightingDate = LocalDate.of(2020, 01, 01);

    Location testLocation = new Location(1,
            "Test Name",
            "Test Description",
            "Test Address",
            new BigDecimal("1.0000000"),
            new BigDecimal("1.0000000"));

    Superhero testSuperhero = new Superhero(1, "A", "A", new Power(1, "A"), null);

    public SightingDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        jdbc.update("DELETE FROM LocationsSuperheros");
        jdbc.update("DELETE FROM OrganizationsSuperheros");
        jdbc.update("DELETE FROM Locations");
        jdbc.update("DELETE FROM Superheros");
        jdbc.update("DELETE FROM Powers");

        jdbc.update("ALTER TABLE LocationsSuperheros auto_increment = 1");
        jdbc.update("ALTER TABLE Superheros auto_increment = 1");
        jdbc.update("ALTER TABLE Locations auto_increment = 1");
        jdbc.update("ALTER TABLE Powers auto_increment = 1");

        jdbc.update("INSERT INTO Powers(Name) VALUES('A')");
        jdbc.update("INSERT INTO Superheros(Name, Description, PowerId) VALUES('A','A', 1)");
        jdbc.update("INSERT INTO Locations(Name, Description, Address, Latitude, Longitude) VALUE("
                + "'Test Name', 'Test Description', 'Test Address', 1.0, 1.0)");
        jdbc.update("INSERT INTO LocationsSuperheros(SuperheroId, LocationId, SightingDate) "
                + "VALUES(1,1,'2020-01-01'),(1,1,'2020-01-01'),(1,1,'2020-01-01')");
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Sighting expected = new Sighting(1, testSightingDate, testLocation, testSuperhero);
        Sighting expected2 = new Sighting(2, testSightingDate, testLocation, testSuperhero);
        Sighting expected3 = new Sighting(3, testSightingDate, testLocation, testSuperhero);

        List<Sighting> fromDao = toTest.getAllSightings();

        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }

    /**
     * Test of createSighting method, of class SightingDaoDB.
     */
    @Test
    public void testCreateSighting() throws DaoException {
        Sighting expected = new Sighting(4, testSightingDate, testLocation, testSuperhero);

        try {
            toTest.getSightingById(4);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }

        Sighting fromDao = toTest.createSighting(expected);
        assertEquals(expected, fromDao);

        fromDao = toTest.getSightingById(4);
        assertEquals(expected, fromDao);
    }

    /**
     * Test of getSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingById() throws DaoException {
        Sighting expected = new Sighting(1, testSightingDate, testLocation, testSuperhero);
        Sighting fromDao = toTest.createSighting(expected);
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testGetSightingByIdInvalidId() throws DaoException {
        try {
            toTest.getSightingById(4);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
    }

    /**
     * Test of editSighting method, of class SightingDaoDB.
     */
    @Test
    public void testEditSighting() throws DaoException {
        Sighting expected = new Sighting(1, LocalDate.of(2020, 02, 01), testLocation, testSuperhero);
        Sighting original = new Sighting(1, testSightingDate, testLocation, testSuperhero);
        
        Sighting fromDao = toTest.getSightingById(1);
        assertEquals(original, fromDao);
        
        assertNotEquals(0, toTest.editSighting(expected));
        
        fromDao = toTest.getSightingById(1);
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testEditSightingInvalidId() {
        Sighting testSighting = new Sighting(4, testSightingDate, testLocation, testSuperhero);
        assertEquals(0, toTest.editSighting(testSighting));
    }

    /**
     * Test of removeSighting method, of class SightingDaoDB.
     */
    @Test
    public void testRemoveSighting() throws DaoException {
        Sighting toRemove = new Sighting(1, testSightingDate, testLocation, testSuperhero);
        
        Sighting fromDao = toTest.getSightingById(1);
        assertEquals(toRemove, fromDao);
        
        assertNotEquals(0, toTest.removeSighting(1));
        
        try {
            toTest.getSightingById(1);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
    }
    
    @Test
    public void testRemoveSightingInvalidId() {
        assertEquals(0, toTest.removeSighting(4));
    }

}
