/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.TestAppConfig;
import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.exceptions.DaoException;
import java.math.BigDecimal;
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
public class LocationDaoDBTest {

    @Autowired
    LocationDao toTest;

    @Autowired
    JdbcTemplate jdbc;

    public LocationDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        jdbc.update("DELETE FROM Locations");

        jdbc.update("ALTER TABLE Locations auto_increment = 1");

        jdbc.update("INSERT INTO Locations(Name, Description, Address, Latitude, Longitude) VALUE("
                + "'Test Name', 'Test Description', 'Test Address', 1.0, 1.0"
                + "),("
                + "'Test Name 2', 'Test Description 2', 'Test Address 2', 1.0, 1.0"
                + "),("
                + "'Test Name 3', 'Test Description 3', 'Test Address 3', 1.0, 1.0"
                + ")");
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location expected = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        Location expected2 = new Location(2,
                "Test Name 2",
                "Test Description 2",
                "Test Address 2",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        Location expected3 = new Location(3,
                "Test Name 3",
                "Test Description 3",
                "Test Address 3",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));

        List<Location> fromDao = toTest.getAllLocations();

        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }

    /**
     * Test of createLocation method, of class LocationDaoDB.
     */
    @Test
    public void testCreateLocation() throws DaoException {
        Location expected = new Location(4,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));

        try {
            toTest.getLocationById(4);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }

        Location added = toTest.createLocation(expected);

        assertEquals(expected, added);

        added = toTest.getLocationById(4);

        assertEquals(expected, added);
    }

    /**
     * Test of getLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testGetLocationById() throws DaoException {
        Location expected = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));

        Location fromDao = toTest.getLocationById(1);

        assertEquals(expected, fromDao);
    }

    @Test
    public void testGetLocationByIdNotFound() {
        try {
            toTest.getLocationById(4);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
    }

    /**
     * Test of editLocation method, of class LocationDaoDB.
     */
    @Test
    public void testEditLocation() throws DaoException {
        Location expected = new Location(1,
                "Edited Test Name",
                "Edited Test Description",
                "Edited Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        Location original = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));

        Location fromDao = toTest.getLocationById(1);

        assertEquals(original, fromDao);

        assertNotEquals(0, toTest.editLocation(expected));

        fromDao = toTest.getLocationById(1);

        assertEquals(expected, fromDao);
    }

    @Test
    public void testEditLocationIdNotFound() {
        int result = toTest.editLocation(new Location(4,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000")));
        assertEquals(0, result);
    }

    /**
     * Test of removeLocation method, of class LocationDaoDB.
     */
    @Test
    public void testRemoveLocation() throws DaoException {
        Location toRemove = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));

        Location fromDao = toTest.getLocationById(1);

        assertEquals(toRemove, fromDao);

        assertNotEquals(0, toTest.removeLocation(1));

        try {
            toTest.getLocationById(1);
            fail("should hit DaoException when Location Id is invalid");
        } catch (DaoException ex) {
        }
    }

    @Test
    public void testRemoveLocationIdNotFound() {
        assertEquals(0, toTest.removeLocation(4));
    }
    
    /**
     * Test of getPowerByName method, of class PowerDaoDB.
     */
    @Test
    public void testGetLocationByName() throws DaoException {
        Location expected = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        Location fromDao = toTest.getLocationByName("Test Name");
        
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testGetLocationByIdInvalidName() {
        try {
            toTest.getLocationByName("Z");
            fail("should hit DaoException when Name is invalid");
        } catch (DaoException ex) {
        }
    }

}
